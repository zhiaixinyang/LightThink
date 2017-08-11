package com.example.greatbook.utils.anim;

import android.view.animation.Interpolator;

/**
 * Created by MDove on 2017/8/11.
 */

public class SpringAnimationInterpolar implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        float factor = 0.6f;
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}
