package com.project.pan.myproject.view.countdown;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.project.pan.myproject.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * @Author: panrongfu
 * @CreateDate: 2019/5/10 15:06
 * @Description: 圆圈倒计时
 */
public class CountDownCircleProgressBar extends View {

    /**控件的矩形宽高*/
    private Rect countDownRectF;
    /**外圈的画笔*/
    private Paint outsidePaint;
    /**内圈底部画笔*/
    private Paint insideBottomPaint;
    /**内圈顶部画笔*/
    private Paint insideTopPaint;
    /**文字画笔*/
    private Paint textPaint;
    /**外圈的宽度*/
    private float outsideCircleWidth;
    /**内圈圆的宽度*/
    private float insideCircleWidth;
    /**外圈圆的半径*/
    private float outsideCircleRadius;
    /**内圈圆的半径*/
    private float intsideCircleRadius;
    /**外圈圆的颜色*/
    private  int outsideCircleColor;
    /**内圈底部圆的颜色*/
    private int insideBottomCircleColor;
    /**内圈顶部圆的颜色*/
    private int insideTopCircleColor;
    /**文字的颜色*/
    private int centerTextColor;
    /**文字大小*/
    private float centerTextSize;
    /**旋转角度*/
    private float sweepAngle = 0;
    /**圆弧起始角度*/
    private final float startAngle = 270;
    /**内部扇形的矩形显示区域 */
    private RectF insideTopRectF;
    /**文字绘制矩形区域*/
    private RectF textRectF;
    /**文字进度*/
    private int countdownTotalNumber = 7;
    /**当前进度*/
    private int currentCountdownNumber;
    /**每一份所占的角度*/
    private int everyAngle;
    private Disposable disposable;
    private Disposable lastDisposable;
    private int total;
    private Handler mHandler = new Handler(Looper.myLooper());
    private Runnable invalidateRunnable = this::invalidate;
    public CountDownCircleProgressBar(Context context) {
        super(context);
        init(null);
    }

    public CountDownCircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CountDownCircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.CountDownCircleProgressBar);
        outsideCircleWidth = typedArray.getDimension(R.styleable.CountDownCircleProgressBar_outsideCircleWidth,5);
        outsideCircleColor = typedArray.getColor(R.styleable.CountDownCircleProgressBar_outsideCircleColor, Color.BLUE);
        insideCircleWidth = typedArray.getDimension(R.styleable.CountDownCircleProgressBar_insideCircleWidth,5);
        insideBottomCircleColor = typedArray.getColor(R.styleable.CountDownCircleProgressBar_insideBottomCircleColor,Color.GRAY);
        insideTopCircleColor = typedArray.getColor(R.styleable.CountDownCircleProgressBar_insideTopCircleColor,Color.RED);
        centerTextColor = typedArray.getColor(R.styleable.CountDownCircleProgressBar_centerTextColor,Color.WHITE);
        centerTextSize = typedArray.getDimension(R.styleable.CountDownCircleProgressBar_centerTextSize,16);
        typedArray.recycle();
        countDownRectF = new Rect();

        //外圈圆
        outsidePaint = new Paint();
        outsidePaint.setStyle(Paint.Style.STROKE);
        outsidePaint.setColor(outsideCircleColor);
        outsidePaint.setAntiAlias(true);
        outsidePaint.setStrokeWidth(outsideCircleWidth);

        //内圈底部圆
        insideBottomPaint = new Paint();
        insideBottomPaint.setStyle(Paint.Style.STROKE);
        insideBottomPaint.setColor(insideBottomCircleColor);
        insideBottomPaint.setAntiAlias(true);
        insideBottomPaint.setStrokeWidth(insideCircleWidth);

        //内圈顶部圆
        insideTopPaint = new Paint();
        insideTopPaint.setStyle(Paint.Style.STROKE);
        insideTopPaint.setColor(insideTopCircleColor);
        insideTopPaint.setAntiAlias(true);
        insideTopPaint.setStrokeWidth(insideCircleWidth);

        //文字
        textPaint = new Paint();
        textPaint.setColor(centerTextColor);
        textPaint.setTextSize(centerTextSize);
        textPaint.setAntiAlias(true);

        //内部扇形的矩形显示区域
        insideTopRectF = new RectF();

        //文字的矩形显示区域
        textRectF = new RectF();

        everyAngle = 360 / countdownTotalNumber;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getDrawingRect(countDownRectF);

        // 控件的宽高
        float width = countDownRectF.right - countDownRectF.left;
        float height = countDownRectF.bottom - countDownRectF.top;

        //控件的中心
        float centerX = width / 2;
        float centerY = height / 2;
        outsideCircleRadius = width / 2 - outsideCircleWidth / 2;
        canvas.drawCircle(centerX,centerY,outsideCircleRadius - 1,outsidePaint);
        float insideCircleRadius = outsideCircleRadius - outsideCircleWidth / 2 - insideCircleWidth / 2 ;

        canvas.drawCircle(centerX,centerY,insideCircleRadius,insideBottomPaint);

        //圆弧进度
        insideTopRectF.left = outsideCircleWidth + insideCircleWidth / 2;
        insideTopRectF.top =outsideCircleWidth + insideCircleWidth / 2;
        insideTopRectF.right = width - outsideCircleWidth - insideCircleWidth / 2 ;
        insideTopRectF.bottom = height - outsideCircleWidth- insideCircleWidth / 2 ;
        canvas.drawArc(insideTopRectF,startAngle,sweepAngle,false,insideTopPaint);

        //绘制文字
        textRectF.left = 0;
        textRectF.top = 0;
        textRectF.right = width;
        textRectF.bottom = height;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((textRectF.bottom + textRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        float offsetX = getTextWidth(textPaint,"0"+currentCountdownNumber);
        canvas.drawText("0"+currentCountdownNumber, textRectF.centerX() - offsetX / 2, baseline, textPaint);
    }

    /**
     * 获取字符串的长度
     * @param textPaint
     * @param text
     * @return
     */
    public float getTextWidth(Paint textPaint, String text) {
        return textPaint.measureText(text);
    }

    /**
     * 总的倒计时描述
     * @param total
     */
    public void setCountdownTotalNumber(int total) {
        countdownTotalNumber = total;
        //为了弥补小数差，所以多加5用来满足当倒数完的时候圆圈画完
        everyAngle = 360 / countdownTotalNumber + 1;
    }

    /**
     * 设置倒计时
     * @param number
     */
    public void setCountDownNumber(int number) {
        currentCountdownNumber = number;
        sweepAngle = (countdownTotalNumber - currentCountdownNumber) * everyAngle;
        mHandler.post(invalidateRunnable);
    }

    /**
     * 开始倒计时
     * @param startNumber
     */
    public void quickCountDown(int startNumber) {
        total = startNumber;
        if (lastDisposable != null) {
            lastDisposable.dispose();
        }
        disposable = Flowable.interval(1, TimeUnit.SECONDS)
                .subscribe(aLong -> {
                    if (total < 0 ) {
                        stopCountDown();
                        disposable.dispose();
                        total = 7;
                    }
                    setCountDownNumber(total);
                    total --;
                });
        lastDisposable = disposable;
    }

    /**
     * 停止计时
     */
    public void stopCountDown(){
        if (lastDisposable != null) {
            lastDisposable.dispose();
            lastDisposable = null;
        }
        mHandler.removeCallbacks(invalidateRunnable);
    }

}
