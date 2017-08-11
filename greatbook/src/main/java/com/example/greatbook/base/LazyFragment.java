package com.example.greatbook.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MDove on 2016/12/6.
 */

public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;
    private Unbinder unbinder;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            View view =inflater.inflate(getContentViewLayoutID(), null);
            unbinder = ButterKnife.bind(this, view);
            initViewsAndEvents(view);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }


    protected abstract void onVisible();
    protected abstract int getContentViewLayoutID();
    protected abstract void initViewsAndEvents(View view);

    protected void onInvisible(){}
}