package com.example.greatbook.diary.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.databinding.ActivityDiarySelfBinding;
import com.example.greatbook.diary.adapter.DiarySelfActivityAdapter;
import com.example.greatbook.diary.presenter.DiarySelfActivityPresenter;
import com.example.greatbook.diary.presenter.contract.DiarySelfActivityContract;
import com.example.greatbook.greendao.entity.DiarySelf;
import com.example.greatbook.utils.DateUtils;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.DefaultNavigationBar;
import com.example.greatbook.widght.itemdecoration.PowerfulStickyDecoration;
import com.example.greatbook.widght.itemdecoration.listener.PowerGroupListener;
import com.example.greatbook.widght.itemswip.OnSwipeListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MDove on 2017/10/3.
 */

public class DiarySelfActivity extends AppCompatActivity implements DiarySelfActivityContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    private ActivityDiarySelfBinding mBinding;
    private List<DiarySelf> mData;
    private DiarySelfActivityAdapter mAdapter;
    private DiarySelfActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DefaultNavigationBar.Builder(this,null)
                .setLeftResId(R.drawable.btn_back_)
                .setOnLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setTitleText("我的自言自语")
                .builder();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_diary_self);
        mBinding.srlChatSelf.setOnRefreshListener(this);

        mPresenter = new DiarySelfActivityPresenter();
        mPresenter.attachView(this);

        mData = new ArrayList<>();
        mAdapter = new DiarySelfActivityAdapter(this, R.layout.item_activity_diary_self, mData);
        mBinding.rlvDiarySelf.setLayoutManager(new LinearLayoutManager(this));
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (mData.size() > position) {
                            return DateUtils.getDateChineseYMD(mData.get(position).time);
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (mData.size() > position) {
                            Date time=mData.get(position).time;
                            View view = getLayoutInflater().inflate(R.layout.item_diary_self_decoration, mBinding.rlvDiarySelf, false);
                            TextView tvDay=((TextView) view.findViewById(R.id.tv_day));
                            tvDay.setText(DateUtils.getDay(time.getTime())+"");
                            tvDay.setBackground(SelectorFactory.newShapeSelector()
                                    .setDefaultStrokeColor(ContextCompat.getColor(DiarySelfActivity.this,R.color.black))
                                    .setCornerRadius(DpUtils.dp2px(4))
                                    .setStrokeWidth(DpUtils.dp2px(1))
                                    .create());
                            ((TextView) view.findViewById(R.id.tv_year_month)).setText(DateUtils.getYearMonth(time.getTime())+"");
                            ((TextView) view.findViewById(R.id.tv_week)).setText(DateUtils.getDayOfWeek(time.getTime()));
                            return view;
                        } else {
                            return null;
                        }
                    }
                })
                .setGroupHeight(DpUtils.dp2px(50))
                .build();

        mBinding.rlvDiarySelf.addItemDecoration(decoration);

        mBinding.rlvDiarySelf.setAdapter(mAdapter);
        mAdapter.setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onDelete(int pos) {
                mPresenter.deleteDiarySelf(mData.get(pos));

                mAdapter.getSwipeItem().quickClose();
                mAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void onTop(int pos) {

            }

            @Override
            public void onAlter(int pos) {

            }
        });

        mBinding.srlChatSelf.setRefreshing(true);
        mPresenter.showAllDiartSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showAllDiarySelf(List<DiarySelf> data) {
        mBinding.srlChatSelf.setRefreshing(false);

        mBinding.includeEmptyLoading.layoutEmptyView.setVisibility(View.GONE);

        mData = data;
        mAdapter.setData(data);
    }

    @Override
    public void diarySelfEmpty() {
        mBinding.srlChatSelf.setRefreshing(false);

        mBinding.includeEmptyLoading.layoutEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteDiarySelfSuc() {
        mBinding.srlChatSelf.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.showAllDiartSelf();
        }
    }
}
