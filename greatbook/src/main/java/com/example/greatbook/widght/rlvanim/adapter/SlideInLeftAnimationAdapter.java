package com.example.greatbook.widght.rlvanim.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by admin on 17/9/21.
 */

public class SlideInLeftAnimationAdapter extends AnimationAdapter {

    public SlideInLeftAnimationAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override protected Animator[] getAnimators(View view) {
        return new Animator[] {
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        };
    }
}
