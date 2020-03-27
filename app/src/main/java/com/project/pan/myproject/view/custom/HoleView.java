package com.project.pan.myproject.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.project.pan.myproject.R;

/**
 * @Author: panrongfu
 * @CreateDate: 2019/5/18 16:00
 * @Description: java类作用描述
 */
public class HoleView extends View {

    private float border;
    private float holeSize;
    private float height;
    private float width;
    private Paint paint;
    private Paint bgPaint;
    private int bgColor;
    private int borderColor;
    private Paint borderPaint;
    private boolean roundRect;
    private float roundRadius;

    public HoleView(Context context) {
        super(context);
        initView(context,null);
    }

    public HoleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public HoleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public void initView(Context context, AttributeSet attrs){

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.HoleView);
        holeSize = ta.getDimension(R.styleable.HoleView_holeSize,50);
        border = ta.getDimension(R.styleable.HoleView_border,0);
        bgColor = ta.getColor(R.styleable.HoleView_bgColor,Color.BLACK);
        borderColor = ta.getColor(R.styleable.HoleView_borderColor,Color.BLUE);
        roundRect = ta.getBoolean(R.styleable.HoleView_roundRect,false);
        roundRadius = ta.getDimension(R.styleable.HoleView_roundRadius,2);
        ta.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getHeight();
        width = getWidth();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        float radius = holeSize / 2;

        if (roundRect) {
            float left = 0 + border;
            float top = 0 + border;
            float right = width - border;
            float bottom = height - border;

            canvas.drawRoundRect(0,0,width,height,roundRadius,roundRadius,bgPaint);
            canvas.drawRoundRect(0, 0, width, height, roundRadius,roundRadius,border == 0 ? bgPaint : borderPaint);
            if (border > 0) {
                //border 如果大于0 则说明需要绘制边框
                //原理很简单，先绘制一个 半径为宽高 - border的边框再挖掉，留下的就是边框了
                canvas.drawRoundRect(left, top, right, bottom, roundRadius,roundRadius,paint);
            }

            //使用CLEAR作为PorterDuffXfermode绘制
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint.setColor(0xFFFFFFFF);
            canvas.drawRoundRect(left,top,right,bottom,roundRadius,roundRadius, paint);

        }else {
            //圆形
            //绘制背景
            canvas.drawCircle(width / 2, height / 2, width,bgPaint);
            //绘制底层圆
            canvas.drawCircle(width / 2, height / 2, radius, border == 0 ? bgPaint : borderPaint);
            if (border > 0) {
                //border 如果大于0 则说明需要绘制边框
                //原理很简单，先绘制一个 半径为radius - border的圆再挖掉，留下的就是边框了
                canvas.drawCircle(width / 2, height / 2, radius - border, paint);
            }

            //使用CLEAR作为PorterDuffXfermode绘制
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint.setColor(0xFFFFFFFF);
            canvas.drawCircle(width / 2, height / 2, radius - border, paint);
        }

        //最后将画笔去除Xfermode
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }
}
