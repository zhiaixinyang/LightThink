package com.example.greatbook.nethot.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.nethot.model.HotTopMenuBean;
import com.example.greatbook.nethot.presenter.contract.HotContract;
import com.example.greatbook.nethot.presenter.HotPresenter;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MDove on 2017/8/10.
 */

public class HotFragment extends BaseLazyFragment<HotPresenter> implements HotContract.View {
    @BindView(R.id.rlv_top_menu)
    RecyclerView rlvTopMenu;
    @BindView(R.id.rlv_hot_content)
    RecyclerView rlvHotContent;
    private List<HotTopMenuBean> topMenus;
    private CommonAdapter<HotTopMenuBean> topMenuAdapter;
    private GridLayoutManager gln;

    @Override
    public void showError(String msg) {
        ToastUtil.toastLong(msg);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_hot;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        initTopMenu();
        gln=new GridLayoutManager(App.getInstance().getContext(),5);
    }

    @Override
    protected void onFirstUserVisible() {
    }


    @Override
    protected void onUserVisible() {
        rlvTopMenu.setLayoutManager(gln);
        rlvTopMenu.setAdapter(topMenuAdapter);
    }

    @Override
    protected void onUserInvisible() {

    }

    private void initTopMenu() {
        topMenus=new ArrayList<>();

        HotTopMenuBean encourageMenu=new HotTopMenuBean();
        encourageMenu.menuIconPath= R.drawable.icon_encourage;
        encourageMenu.menuName="热门鸡汤";
        topMenus.add(encourageMenu);

        HotTopMenuBean shortEssayMenu=new HotTopMenuBean();
        shortEssayMenu.menuIconPath=R.drawable.icon_short_essay;
        shortEssayMenu.menuName="热门短句";
        topMenus.add(shortEssayMenu);

        HotTopMenuBean jokMenu=new HotTopMenuBean();
        jokMenu.menuIconPath=R.drawable.icon_jok;
        jokMenu.menuName="热门段子";
        topMenus.add(jokMenu);

        topMenuAdapter=new CommonAdapter<HotTopMenuBean>(App.getInstance().getContext(),R.layout.item_hot_top_menu,topMenus) {
            @Override
            public void convert(ViewHolder holder, HotTopMenuBean hotTopMenuBean) {
                holder.setImageResource(R.id.iv_hot_top_menu,hotTopMenuBean.menuIconPath);
                holder.setText(R.id.tv_hot_top_menu,hotTopMenuBean.menuName);
                holder.setOnClickListener(R.id.main_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                    }
                });
            }
        };
    }
}
