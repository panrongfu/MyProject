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
 * @author: panrongfu
 * @date: 2018/7/9 12:48
 * @describe: 自定义指示器小圆点，以控件的height为圆的直径
 */

public class PointView extends View {
    /**默认圆点的默认颜色*/
    private static final int DEFAULT_COLOR = Color.WHITE;
    /**拉伸圆点的默认颜色*/
    private static final int DEFAULT_STRETCH_COLOR = Color.YELLOW;
    /**圆点颜色*/
    private int pColor;
    /**拉伸条的颜色*/
    private int pStretchColor;
    /**圆点宽度*/
    private int pWidth;
    /**圆点高度*/
    private int pHeight;
    /**矩形画笔*/
    private Paint mRectFPaint;
    /**圆点的画笔*/
    private Paint mPointPaint;
    /**矩形*/
    private RectF mRectF;
    /**矩形的左边距*/
    private int mRectFLeft;
    /**矩形的右边距*/
    private int mRectFRight;
    /**用来缓存宽度*/
    private int tempWidth=0;
    /**布局大小位置发生改变*/
    private boolean layoutChange;
    /**是否开始拉伸*/
    private boolean layoutStartStretch = false;
    /**是否开始收缩*/
    private boolean layoutStartCompress = false;
    private Disposable mDisposable;

    public PointView(Context context) {
        super(context);
        initView(context,null);
    }

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
        /*使用完之后回收*/
        ta.recycle();
        mRectFPaint = new Paint();
        mPointPaint = new Paint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取控件的宽度
        pWidth = getWidth();
        //获取控件的高度
        pHeight =  getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取控件的宽度
        pWidth = getWidth();
        //获取控件的高度
        pHeight =  getHeight();
        //充满
        mRectFPaint.setStyle(Paint.Style.FILL);
        mRectFPaint.setColor(pStretchColor);
        //设置画笔的抗锯齿效果
        mRectFPaint.setAntiAlias(true);
        //充满
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(pColor);
        //设置画笔的抗锯齿效果
        mPointPaint.setAntiAlias(true);
        //动画开始才绘制
        if(layoutStartStretch){
            /*当布局大小改变的时候调用*/
            if(layoutChange){
                startStretch(2);
                layoutChange = false;
            }
            Log.e("onDraw-my","pHeight:"+ pix2dip(pHeight)+"   pWidth:"+pix2dip(pWidth)+"  mRectFLeft:"+pix2dip(mRectFLeft)+"   mRectFRight:"+pix2dip(mRectFRight));
            //绘制矩形
            mRectF = new RectF(mRectFLeft,0,mRectFRight,pHeight);
            canvas.drawRoundRect(mRectF,0,0,mRectFPaint);
            //在左边矩形画个圆
            canvas.drawCircle(mRectFLeft+1,pHeight/2,pHeight/2,mRectFPaint);
            //在右矩形边上画个圆
            canvas.drawCircle(mRectFRight-1,pHeight/2,pHeight/2,mRectFPaint);
        }else {
            //在左边矩形画个圆
            canvas.drawCircle(pWidth/2,pHeight/2,pHeight/2,mPointPaint);
        }
    }

    public boolean isLayoutStartStretch() {
        return layoutStartStretch;
    }

    public void setLayoutStartStretch(boolean layoutStartStretch) {
        this.layoutStartStretch = layoutStartStretch;
    }
    public boolean isLayoutStartCompress() {
        return layoutStartCompress;
    }

    public void setLayoutStartCompress(boolean layoutStartCompress) {
        this.layoutStartCompress = layoutStartCompress;
    }

    public boolean isLayoutChange() {
        return layoutChange;
    }

    public void setLayoutChange(boolean layoutChange) {
        this.layoutChange = layoutChange;
    }

    /**
     * 开始拉伸
     * @param milliseconds
     */
    public void startStretch(int milliseconds){
        mRectFLeft = mRectFRight = pWidth/2;
        Log.e("startStretch","  mRectFLeft:"+pix2dip(mRectFLeft)+"   mRectFRight:"+pix2dip(mRectFRight));
        mDisposable = Flowable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .doOnNext(aLong -> {})
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
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
                    invalidate();
                });
    }

    /**
     * 开始收缩
     * @param milliseconds
     */
    public void startCompress(int milliseconds){

        mDisposable = Flowable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .doOnNext(aLong -> {})
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    invalidate();
                    //每隔 milliseconds 左边矩形距离left加1
                    mRectFLeft+=2;
                    if(pWidth/2-mRectFLeft>pHeight/2
                            ||pWidth/2 -mRectFLeft == pHeight/2){
                        mRectFLeft = pWidth/2-pHeight/2;
                        compressStop();
                    }
                    //每隔 milliseconds 右边矩形right加1
                    mRectFRight-=2;
                    if(mRectFRight < pWidth/2+pHeight/2 || mRectFRight == pWidth/2+pHeight/2){
                        mRectFRight = pWidth/2+pHeight/2;
                        compressStop();
                    }
                });
    }

    /**
     * 回缩停止
     */
    private void compressStop() {
        layoutStartStretch = false;
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

    public void setpColor(int pColor) {
        this.pColor = pColor;
    }

    public void setpStretchColor(int pStretchColor) {
        this.pStretchColor = pStretchColor;
    }
}
