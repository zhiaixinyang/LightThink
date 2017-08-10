package com.example.greatbook.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;

/**
 * Created by MBENBEN on 2017/8/10.
 */

public class MiddleMainFragment extends BaseLazyFragment {
    public static MiddleMainFragment newInstance() {

        Bundle args = new Bundle();

        MiddleMainFragment fragment = new MiddleMainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_main;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected void onFirstUserVisible() {

    }


    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void showError(String msg) {

    }
}
