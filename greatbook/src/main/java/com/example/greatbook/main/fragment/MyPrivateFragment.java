package com.example.greatbook.main.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.databinding.FragMyprivateBinding;
import com.example.greatbook.greendao.entity.LocalGroup;
import com.example.greatbook.greendao.entity.LocalRecord;
import com.example.greatbook.main.activity.FeedBackActivity;
import com.example.greatbook.main.activity.LoginActivity;
import com.example.greatbook.main.activity.MyPrivateAdjustActivity;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.DpUtils;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.SelectorFactory;
import com.example.greatbook.utils.StringUtils;

/**
 * Created by MDove on 2016/11/1.
 */

public class MyPrivateFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private FragMyprivateBinding mBinding;
    private int mCurLevel;
    private int mUpLevelNeed;
    private int mCurWords;
    private User user;
    private boolean isFirstShow = true;

    public static MyPrivateFragment newInstance() {
        Bundle args = new Bundle();
        MyPrivateFragment fragment = new MyPrivateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.frag_myprivate, container, false);
        isFirstShow = false;
        initViewsAndEvents();
        return mBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isFirstShow) {
            updateShowLevel();
        }
    }

    private void initViewsAndEvents() {
        mUpLevelNeed = getContext().getResources().getInteger(R.integer.up_level_need);

        mBinding.srfMyprivate.setOnRefreshListener(this);
        mBinding.btnAdjust.setOnClickListener(this);
        mBinding.btnFeedback.setOnClickListener(this);
        mBinding.btnExit.setOnClickListener(this);

        mBinding.tvLevel.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(DpUtils.dp2px(4))
                .setStrokeWidth(DpUtils.dp2px(1))
                .setDefaultStrokeColor(ContextCompat.getColor(getContext(), R.color.red))
                .create());

        updateShowLevel();

        initAvatar();
        onRefresh();
    }

    private void updateShowLevel() {
        mCurLevel = MySharedPreferences.getCurLevel();
        mCurWords = MySharedPreferences.getCurWords();

        String strLevel = (String) App.getInstance().getContext()
                .getResources()
                .getText(R.string.string_level);

        mBinding.tvLevel.setText(String.format(strLevel, mCurLevel));
        String strCurWords = (String) App.getInstance().getContext()
                .getResources()
                .getText(R.string.string_level_cur_words);
        int curWords = mCurWords - mCurLevel * mUpLevelNeed;
        int curNeedWords = (mCurLevel + 1) * mUpLevelNeed;
        mBinding.progressLevel.setProgress((curWords * 100 / curNeedWords));
        //计算升级所需字数，以及当前字数
        mBinding.tvNeedWordsLevel.setText(String.format(strCurWords,
                curWords, curNeedWords, mCurWords));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mBinding.tvName.setText(user.getName());
        GlideUtils.load(user.getAvatar().getUrl(), mBinding.ivAvatar);
        mBinding.srfMyprivate.setRefreshing(false);
    }

    private void initAvatar() {
        if (AVUser.getCurrentUser(User.class) != null) {
            user = AVUser.getCurrentUser(User.class);
            if (!StringUtils.isEmpty(user.getName())) {
                mBinding.tvName.setText(user.getName());
                GlideUtils.load(user.getAvatar().getUrl(), mBinding.ivAvatar);
            } else {
                mBinding.tvName.setText("书心用户");
                GlideUtils.load(user.getAvatar().getUrl(), mBinding.ivAvatar);
            }
        }
    }

    private void adjust() {
        Intent toAdjust = new Intent(App.getInstance().getContext(), MyPrivateAdjustActivity.class);
        startActivity(toAdjust);
    }

    private void exitAccount() {
        //退出账号时清空本地数据
        clearDB();
        MySharedPreferences.setLogin(false);
        //登出账号
        AVUser.getCurrentUser(User.class).logOut();
        Intent intent = new Intent(App.getInstance().getContext(), LoginActivity.class);
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_out);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    //退出账号时清空本地数据
    private void clearDB() {
        App.getDaoSession().deleteAll(LocalRecord.class);
        App.getDaoSession().deleteAll(LocalGroup.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_adjust:
                adjust();
                break;
            case R.id.btn_feedback:
                Intent toFeedBack = new Intent(App.getInstance().getContext(), FeedBackActivity.class);
                startActivity(toFeedBack);
                break;
            case R.id.btn_exit:
                exitAccount();
                break;
        }
    }
}

