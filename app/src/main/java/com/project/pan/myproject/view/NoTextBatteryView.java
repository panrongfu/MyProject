package com.project.pan.myproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.project.pan.myproject.R;


/**
 * @Author: panrongfu
 * @CreateDate: 2019-11-07 12:16
 * @Description: java类作用描述
 */
public class NoTextBatteryView extends View {

    Paint mPaint;
    Bitmap mBitmap;
    RectF imgRectF;
    RectF batteryRectF;
    Paint mBatteryPaint;
    public NoTextBatteryView(Context context) {
        this(context,null);

    }

    public NoTextBatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoTextBatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
         mPaint = new Paint();
         mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_battery);
         imgRectF = new RectF(0,0,mBitmap.getWidth(),mBitmap.getHeight());
         batteryRectF = new RectF();
         batteryRectF.right = mBitmap.getWidth() / 2;

         mBatteryPaint = new Paint();
         mBatteryPaint.setAntiAlias(true);
         mBatteryPaint.setColor(Color.WHITE);
         mBatteryPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, null, imgRectF, mPaint);
        batteryRectF.left = 5;
        batteryRectF.top = 5;
        batteryRectF.bottom = mBitmap.getHeight() -5 ;
        canvas.drawRoundRect(batteryRectF,1,1,mBatteryPaint);
    }

    /**
     * 设置电池电量
     * @param power
     */
    public void setBatteryPower(float power) {
        if (power < 0) power = 0;
        double percent = power / 100;
        if (percent < 0.2) {
            mBatteryPaint.setColor(Color.RED);
        }else {
            mBatteryPaint.setColor(Color.WHITE);
        }
        float width = (float) (mBitmap.getWidth() * percent);
        if (width < 8)  width = 8;
        Log.d("width: ",width+""+"mBitmap.getWidth(): "+ mBitmap.getWidth());
        batteryRectF.right = width > mBitmap.getWidth() ? mBitmap.getWidth() - 5 : width;
        invalidate();
    }
}
