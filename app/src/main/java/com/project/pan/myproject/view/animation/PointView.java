package com.project.pan.myproject.view.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.project.pan.myproject.R;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * author: panrongfu
 * date:2018/7/9 12:48
 * describe: 自定义指示器小圆点，以控件的height为圆的直径，以控件的宽为拉伸的宽度
 */

public class PointView extends View {

    private static final int DEFAULT_COLOR = Color.WHITE;
    private static final int DEFAULT_STRETCH_COLOR = Color.YELLOW;
    private int pColor;
    private int pStretchColor;//拉伸条的颜色
    private int pWidth;
    private int pHeight;
    private Paint mRectFPaint;
    private Paint mPointPaint;//圆点的画笔
    private boolean animationStart = false;
    private Disposable mDisposable;

    private RectF mRectF;
    private int mRectFLeft;
    private int mRectFRight;
    private int tempWidth=0;

    private Paint testPaint;
    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context mContext, AttributeSet mAttrs) {

        TypedArray ta = mContext.obtainStyledAttributes(mAttrs,R.styleable.pointerView);
        pColor = ta.getColor(R.styleable.pointerView_pColor,DEFAULT_COLOR);
        pStretchColor = ta.getColor(R.styleable.pointerView_pStretchColor,DEFAULT_STRETCH_COLOR);
        ta.recycle();//使用完之后回收

        mRectFPaint = new Paint();
        mPointPaint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        pWidth = getWidth();//获取控件的宽度
        pHeight =  getHeight();//获取控件的高度

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectFPaint.setStyle(Paint.Style.FILL);//充满
        mRectFPaint.setColor(pStretchColor);
        mRectFPaint.setAntiAlias(true);// 设置画笔的抗锯齿效果

        mPointPaint.setStyle(Paint.Style.FILL);//充满
        mPointPaint.setColor(pColor);
        mPointPaint.setAntiAlias(true);// 设置画笔的抗锯齿效果

        if(animationStart){//动画开始才绘制
            Log.e("onDraw-my","pHeight:"+ pHeight+"   pWidth:"+pWidth+"  mRectFLeft:"+mRectFLeft+"   mRectFRight:"+mRectFRight);
            mRectF = new RectF(mRectFLeft,0,mRectFRight,pHeight);//绘制矩形
            canvas.drawRoundRect(mRectF,0,0,mRectFPaint);
            canvas.drawCircle(mRectFLeft,pHeight/2,pHeight/2,mRectFPaint);//在左边矩形画个圆
            canvas.drawCircle(mRectFRight,pHeight/2,pHeight/2,mRectFPaint);//在右矩形边上画个圆
        }else {//若没有开启动画，则默认显示一个原点
            canvas.drawCircle(pWidth/2,pHeight/2,pHeight/2,mPointPaint);//在左边矩形画个圆
        }
    }

    /**
     * 开始拉伸
     * @param milliseconds
     */
    public void startStretch(int milliseconds){
        animationStart = true;
        mRectFLeft = mRectFRight = pWidth/2;
        mDisposable = Flowable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                       // Log.e("Switcher", "accept: accept : "+aLong );
                    }
                })
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        invalidate();
                        tempWidth ++;//这里循环+1是为了取余，从而达到mRectFLeft 和 mRectFRight 都有数值变化的目的
                        if(tempWidth%2==0){
                            //当mRectFLeft 小于圆的半径 或者刚好等于圆的半径则停止拉伸
                            if(mRectFLeft < pHeight/2 || mRectFLeft == pHeight/2){
                                mRectFLeft = pHeight/2;
                                stretchStop();
                            }
                            mRectFLeft--;
                        }else {
                            //当mRectFRight的位置超过了pWidth - pHeight/2的值（即右边留出的部分已经不够绘制半圆了）
                            //或是刚好等于pWidth - pHeight/2 则停止拉伸
                            if(mRectFRight > pWidth - pHeight/2 || mRectFRight == pWidth - pHeight/2){
                                mRectFRight = pWidth - pHeight/2;
                                stretchStop();
                            }
                            mRectFRight++;
                        }
                    }
                });
    }

    /**
     * 开始收缩
     * @param milliseconds
     */
    public void startCompress(int milliseconds){
        mDisposable = Flowable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                })
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        invalidate();
                        mRectFLeft++;//每隔 milliseconds 左边矩形距离left加1
                        if(pWidth/2-mRectFLeft>pHeight/2
                                ||pWidth/2 -mRectFLeft == pHeight/2){
                            mRectFLeft = pWidth/2-pHeight/2;
                            compressStop();
                        }

                        mRectFRight--;//每隔 milliseconds 右边矩形right加1
                        if(mRectFRight < pWidth/2+pHeight/2 || mRectFRight == pWidth/2+pHeight/2){
                            mRectFRight = pWidth/2+pHeight/2;
                            compressStop();
                        }
                    }
                });
    }

    /**
     * 回缩停止
     */
    private void compressStop() {
        animationStart = false;
        invalidate();
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

    /**
     * 拉伸停止
     */
    public void stretchStop(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

    public  int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * pix转dip
     */
    public  int pix2dip(float pix) {
        final float densityDpi = getResources().getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }
}
