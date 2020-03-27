package com.project.pan.myproject.view.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @Author: panrongfu
 * @CreateDate: 2019/5/19 19:56
 * @Description: java类作用描述
 */
public class HoleLayout extends FrameLayout {

    HoleView holeView;

    public HoleLayout(@NonNull Context context) {
        super(context);
    }

    public HoleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HoleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
            holeView = new HoleView(context,attrs);
            holeView.invalidate();
            addView(holeView);

    }
}
