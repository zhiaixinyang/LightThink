package com.example.ywb.recyclerviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private RecyclerViewAdapter mAdapter;
    private int lastVisibleItem = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private Button listViewButton;
    private Button gridViewButton;
    private Button horizontalListButton;
    private Button horizontalGridButton;
    private Button waterfallButton;
    private Button addButton;
    private Button removeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initView();

        pullToRefresh();
        loadMoreData();
        onRecyclerItemClickListener();
    }

    /**
     * 判断是否可以加载更多
     *
     * @return
     */
    private boolean isCouldLoadMore() {
        int visibleItemCount = mRecyclerView.getChildCount();
        if (visibleItemCount == mAdapter.mDatas.size()) {
            return false;
        }
        return true;
    }

    private void initDatas() {
        mDatas = new ArrayList<>();

        for (int i = 'A'; i <= 'H'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_refesh_layout);
        listViewButton = (Button) findViewById(R.id.list_view);
        gridViewButton = (Button) findViewById(R.id.grid_view);
        horizontalListButton = (Button) findViewById(R.id.horizontal_list_view);
        horizontalGridButton = (Button) findViewById(R.id.horizontal_grid_view);
        waterfallButton = (Button) findViewById(R.id.waterfall);
        addButton = (Button) findViewById(R.id.add_button);
        removeButton = (Button) findViewById(R.id.remove_button);

        waterfallButton.setOnClickListener(this);
        listViewButton.setOnClickListener(this);
        gridViewButton.setOnClickListener(this);
        horizontalGridButton.setOnClickListener(this);
        horizontalListButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.color_a));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color_c, R.color.color_b, R.color.color_d);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources()
                        .getDisplayMetrics()));

        mAdapter = new RecyclerViewAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void pullToRefresh(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newDatas = new ArrayList<String>();
                        for (int i = 0; i < 3; i++) {
                            newDatas.add("new" + (i + 1));
                        }

                        //添加更新的数据，更新UI
                        mAdapter.addItems(newDatas);

                        //取消刷新动画
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
    }

    private void loadMoreData(){
        //监听滚动事件，加载更多数据
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (isCouldLoadMore()) {
                    mAdapter.changeIsCompleteFill(true);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                        mAdapter.changeLoadMoreStatus(mAdapter.PULLINT_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<String> moreDatas = new ArrayList<String>();
                                for (int i = 0; i < 3; i++) {
                                    moreDatas.add("more data" + (i + 1));
                                }
                                mAdapter.addMoreDatas(moreDatas);
                                Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_LONG).show();
                            }
                        }, 5000);
                    }
                } else {
                    mAdapter.changeIsCompleteFill(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void onRecyclerItemClickListener(){
        mAdapter.setOnRecyclerViewItemListener(new RecyclerViewAdapter.OnRecyclerViewItemListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(MainActivity.this,"onClick:"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                Toast.makeText(MainActivity.this,"onLongClick:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.list_view:
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                break;
            case R.id.grid_view:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            case R.id.horizontal_grid_view:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.horizontal_list_view:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
                break;
            case R.id.waterfall:
                Intent intent = new Intent(MainActivity.this,WaterFallActivity.class);
                startActivity(intent);
                break;
            case R.id.add_button:
                mAdapter.insertData("增加的数据",1);
                break;
            case R.id.remove_button:
                mAdapter.removeData(1);
                break;
            default:
                break;
        }
    }
}
