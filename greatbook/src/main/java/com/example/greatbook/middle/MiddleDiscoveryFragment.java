package com.example.greatbook.middle;

import android.os.Bundle;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.LazyFragment;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleDiscoveryFragment extends LazyFragment {
    public static MiddleDiscoveryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MiddleDiscoveryFragment fragment = new MiddleDiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void onVisible() {
        
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_discovery;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }
}
