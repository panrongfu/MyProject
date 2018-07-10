package com.project.pan.myproject.view.animation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * author: panrongfu
 * date:2018/7/10 20:36
 * describe: 自定义指示器布局
 */

public class IndicatorLayout extends LinearLayout {

    private PointView pointView;
    private int itemSize;//item的个数
    private int currentItem;//当前item的位置

    public IndicatorLayout(Context context) {
        super(context);
    }

    public IndicatorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void initView(){

    }

}
