package com.project.pan.myproject.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author: panrongfu
 * @CreateDate: 2019/4/24 11:25
 * @Description: java类作用描述
 */
public class BezierView extends View {

    Path mPath;
    Paint mPaint;

    PointF start;
    PointF end;
    PointF control;

    int centerX;
    int centerY;
    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
       // mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        start = new PointF(0,0);
        end = new PointF(0,0);
        control = new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        start.x = centerX-200;
        start.y = centerY;

        end.x = centerX+200;
        end.y = centerY;

        control.x = centerX;
        control.y = centerY-400;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(start.x,start.y);
        mPath.quadTo(control.x,control.y,end.x,end.y);
        canvas.drawPath(mPath,mPaint);
    }
}
