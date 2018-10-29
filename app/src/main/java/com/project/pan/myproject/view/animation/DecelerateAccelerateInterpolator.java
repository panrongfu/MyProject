package com.project.pan.myproject.view.animation;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.graphics.Interpolator;
import android.view.animation.BaseInterpolator;

/**
 * @author: panrongfu
 * @date: 2018/10/18 18:46
 * @describe:
 */

@SuppressLint("NewApi")
public class DecelerateAccelerateInterpolator implements TimeInterpolator {

    public DecelerateAccelerateInterpolator() {
    }

    @Override
    public float getInterpolation(float input) {
        return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }
    private static float bounce(float t) {
        return t * t * 8.0f;
    }
}
