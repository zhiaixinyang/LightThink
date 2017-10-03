package com.example.greatbook.local.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.databinding.FragMiddleMainBinding;
import com.example.greatbook.diary.activity.DiarySelfActivity;
import com.example.greatbook.diary.fragment.DiarySelfFragment;
import com.example.greatbook.local.activity.AllLocalRecordActivity;
import com.example.greatbook.local.activity.EssayListActivity;
import com.example.greatbook.local.activity.MyPlanActivity;
import com.example.greatbook.local.activity.SetGroupsActivity;
import com.example.greatbook.local.adapter.MainMenuAdapter;
import com.example.greatbook.local.model.MainMenuItemBean;
import com.example.greatbook.local.presenter.MiddleLocalAddPresenter;
import com.example.greatbook.local.presenter.contract.MiddleLocalAddContract;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.refresh.RefreshRecyclerView;

import java.util.List;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleMainFragment extends Fragment implements MiddleLocalAddContract.View,
        RefreshRecyclerView.OnRefreshListener {
    private MainMenuAdapter menuAdapter;
    private List<MainMenuItemBean> menuData;
    private DiarySelfFragment mDiarySelfFragment;
    private FragMiddleMainBinding mBinding;
    private MiddleLocalAddPresenter mPresenter;

    public static final int MY_ALL_CONTENT = 2;
    public static final int MY_ALL_GROUP = 3;
    public static final int MY_COOPATER_TOPIC = 4;
    public static final int MY_PLAN = 5;
    public static final int CHAT_SELF = 1;

    public static MiddleMainFragment newInstance() {
        Bundle args = new Bundle();

        MiddleMainFragment fragment = new MiddleMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.frag_middle_main, container, false);

        initEvents();
        return mBinding.getRoot();
    }

    protected void initEvents() {
        mDiarySelfFragment = DiarySelfFragment.newInstance();

        mPresenter = new MiddleLocalAddPresenter();
        mPresenter.attachView(this);
        mPresenter.initMenu(getContext());
        menuAdapter = new MainMenuAdapter(getContext(), menuData);
        mBinding.rlvMainMenu.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mBinding.rlvMainMenu.setAdapter(menuAdapter);

        menuAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                MainMenuItemBean menuItemBean = (MainMenuItemBean) o;
                switch (menuItemBean.menuType) {
                    case MY_ALL_CONTENT:
                        Intent toMyAllContent = new Intent(getContext(), AllLocalRecordActivity.class);
                        startActivity(toMyAllContent);
                        break;
                    case MY_ALL_GROUP:
                        Intent toMyAllGroup = new Intent(getContext(), SetGroupsActivity.class);
                        toMyAllGroup.putExtra(SetGroupsActivity.IS_ALL_GROUPS_SHOW_TAG, SetGroupsActivity.IS_ALL_GROUPS_SHOW);
                        startActivity(toMyAllGroup);
                        break;
                    case MY_PLAN:
                        Intent toMyPlan = new Intent(getContext(), MyPlanActivity.class);
                        startActivity(toMyPlan);
                        break;
                    case MY_COOPATER_TOPIC:
                        Intent to = new Intent(getContext(), EssayListActivity.class);
                        startActivity(to);
                        break;
                    case CHAT_SELF:
                        Intent toDiarySelf = new Intent(getContext(), DiarySelfActivity.class);
                        startActivity(toDiarySelf);
                        break;
                    default:
                        break;
                }
            }
        });

        if (getActivity() != null) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, mDiarySelfFragment)
                    .commit();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void onLoadRlvRefresh() {

    }

    @Override
    public void showMenu(List<MainMenuItemBean> menuData) {
        this.menuData = menuData;
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

}
