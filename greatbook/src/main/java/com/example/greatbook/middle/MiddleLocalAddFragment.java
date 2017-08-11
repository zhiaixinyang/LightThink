package com.example.greatbook.middle;

import android.os.Bundle;
import android.view.View;

import com.example.greatbook.R;
import com.example.greatbook.base.LazyFragment;
import com.example.greatbook.ui.main.fragment.TalkAboutFragment;

/**
 * Created by MDove on 2017/8/11.
 */

public class MiddleLocalAddFragment extends LazyFragment {

    public static MiddleLocalAddFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MiddleLocalAddFragment fragment = new MiddleLocalAddFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void onVisible() {
        
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_middle_add_local;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }
}
