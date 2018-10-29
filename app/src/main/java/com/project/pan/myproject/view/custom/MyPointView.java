package com.project.pan.myproject.view.custom;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.pan.myproject.view.animation.MyPointEvaluator;

/**
 * @author: panrongfu
 * @date: 2018/10/18 19:14
 * @describe:
 */

public class MyPointView extends View {
    // 设置需要用到的变量
    public static final float RADIUS = 70f;// 圆的半径 = 70
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔

    public MyPointView(Context context) {
        super(context);
        initView(context);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
            Point startPoint = new Point(RADIUS, RADIUS);// 初始点为圆心(70,70)
            Point endPoint = new Point(700, 1000);// 结束点为(700,1000)
            ValueAnimator animator = ValueAnimator.ofObject(new MyPointEvaluator(),startPoint,endPoint);
            animator.setDuration(5000);
            animator.addUpdateListener(animation -> {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            });
            animator.start();
        }else {
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
        }
    }
}
