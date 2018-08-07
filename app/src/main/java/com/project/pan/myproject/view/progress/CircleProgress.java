package com.project.pan.myproject.view.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.project.pan.myproject.R;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author: panrongfu
 * @date: 2018/8/2 19:56
 * @describe: 自定义圆形进度条
 */

public class CircleProgress extends View {
    /**文字画笔*/
    private Paint mTextPaint;
    /**起始小圆画笔*/
    private Paint startCirclePaint;
    /**结束时小圆画笔*/
    private Paint endCirclePaint;
    /**底层圆画笔*/
    private Paint inCirclePaint;
    /**外层圆画笔*/
    private Paint outCirclePaint;
    /**文字画笔颜色*/
    private int progressTextColor = Color.GRAY;
    /**底层圆画笔颜色*/
    private int inCircleColor = Color.GRAY;
    /**外层圆画笔的颜色*/
    private int outCircleColor = Color.RED;
    /**底层圆画笔的宽度 单位：dp*/
    private float inCircleStrokeWidth = 4;
    /**外层圆画笔的宽度 单位：dp*/
    private float outCircleStrokeWidth = 12;
    /**底层圆绘制的矩形区域*/
    private RectF inCircleRectF;
    /**外层圆绘制的矩形区域*/
    private RectF outCircleRectF;
    /**文字绘制矩形区域*/
    private RectF textRectF;
    /**圆的中心X轴坐标*/
    private int mWidth;
    /**圆中心Y轴坐标*/
    private int mHeight;

    /**旋转角度*/
    private float mSweepAngle = 0;
    /**圆弧起始角度*/
    private final float mStartAngle = 270;
    /**文字*/
    private String mProgressText;
    /**文字大小*/
    private int progressTextSize = 30;
    /**外圈起始颜色*/
    private int outCircleStartColor;
    /**外圈结束颜色*/
    private int outCircleEndColor;

    public CircleProgress(Context context) {
        super(context);
        initViewUI(context,null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViewUI(context,attrs);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewUI(context,attrs);
    }

    private void initViewUI(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        progressTextColor = ta.getColor(R.styleable.CircleProgress_progressTextColor, Color.GRAY);
        inCircleColor = ta.getColor(R.styleable.CircleProgress_inCircleColor, Color.GRAY);
        outCircleColor = ta.getColor(R.styleable.CircleProgress_outCircleColor, Color.BLUE);
        outCircleStrokeWidth = ta.getDimension(R.styleable.CircleProgress_outCircleStrokeWidth,12f);
        outCircleStartColor = ta.getColor(R.styleable.CircleProgress_outCircleStartColor, Color.BLUE);
        outCircleEndColor = ta.getColor(R.styleable.CircleProgress_outCircleEndColor, Color.WHITE);
        progressTextSize = (int) ta.getDimension(R.styleable.CircleProgress_progressTextSize,21);

        inCircleStrokeWidth = outCircleStrokeWidth/3;
        inCirclePaint = new Paint();
        inCirclePaint.setColor(inCircleColor);
        inCirclePaint.setAntiAlias(true);
        inCirclePaint.setStyle(Paint.Style.STROKE);
        inCirclePaint.setStrokeWidth(inCircleStrokeWidth);

        outCirclePaint = new Paint();
        outCirclePaint.setColor(outCircleColor);
        outCirclePaint.setAntiAlias(true);
        outCirclePaint.setStyle(Paint.Style.STROKE);
        outCirclePaint.setStrokeWidth(outCircleStrokeWidth);
        /**
         * 让弧线的两端是圆滑的，需要给Pain设置一个属性：
         * 之前不知道这个属性，想了很复杂，在起始和结束的位置画小圆填充，使之变成圆角，做了很多工作
         * 不过这个过程学到了很多，画小圆的部分代码保留，祭奠流逝的时间
         */
        outCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        startCirclePaint = new Paint();
        startCirclePaint.setColor(outCircleColor);
        startCirclePaint.setAntiAlias(true);
        startCirclePaint.setStyle(Paint.Style.FILL);

        //结束小圆画笔初始化
        endCirclePaint = new Paint();
        endCirclePaint.setColor(outCircleColor);
        endCirclePaint.setAntiAlias(true);
        endCirclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(progressTextColor);
        mTextPaint.setTextSize(progressTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        float inCircleLeft = outCircleStrokeWidth/4+inCircleStrokeWidth/2;
        float inCircleTop = outCircleStrokeWidth/4+inCircleStrokeWidth/2;
        float inCircleRight = mWidth-outCircleStrokeWidth/4-inCircleStrokeWidth/2;
        float inCircleBottom = mHeight-outCircleStrokeWidth/4 -inCircleStrokeWidth/2;

        //底层圆 这个边距是基于当前控件的
        inCircleRectF = new RectF(inCircleLeft,inCircleTop,inCircleRight, inCircleBottom);
        canvas.drawArc(inCircleRectF,0,360,false,inCirclePaint);

        float outCircleLeft = outCircleStrokeWidth/2;
        float outCircleTop = outCircleStrokeWidth/2;
        float outCircleRight = mWidth-outCircleStrokeWidth/2;
        float outCircleBottom = mHeight-outCircleStrokeWidth/2;
        outCircleRectF = new RectF(outCircleLeft, outCircleTop,outCircleRight,outCircleBottom);
        LinearGradient mShader = new LinearGradient(
                outCircleRectF.left,
                outCircleRectF.top,
                outCircleRectF.left,
                outCircleRectF.bottom,
                outCircleStartColor,
                outCircleEndColor,
                Shader.TileMode.MIRROR
        );
        outCirclePaint.setShader(mShader);
        //xy轴 0点为圆心 右边0度 下边90度 左边180度 上边270度
        canvas.drawArc(outCircleRectF,mStartAngle,mSweepAngle,false,outCirclePaint);

        ///////////////////////////////////////画小圆的代码//////////////////////////////////////////////////
        //  外圆的半径                                                                                    //
        //  float outCircleRadius = mHeight/2 - outCircleStrokeWidth/2;                                 //
        //  起始小圆的x坐标                                                                                //
        //  int startCircleCx = mWidth/2;                                                               //
        //  起始小圆的y坐标                                                                                //
        //  float startCircleCy = mHeight/2 - outCircleRadius;                                          //
        //  canvas.drawCircle(startCircleCx,startCircleCy,outCircleStrokeWidth/2,startCirclePaint);     //
        //  需要注意的是，参数传入的是PI，所以要先用角度除以180获得PI值                                            //
        //  float endCircleCx = (float) (mWidth/2+ Math.sin(Math.PI*mSweepAngle/180)*outCircleRadius);   //
        //  float endCircleCy = (float) (mHeight/2- Math.cos(Math.PI*mSweepAngle/180)*outCircleRadius);  //
        //  canvas.drawCircle(endCircleCx,endCircleCy,outCircleStrokeWidth/2,startCirclePaint);          //
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        textRectF = new RectF(0,0,mWidth,mHeight);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (int) ((textRectF.bottom + textRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        if(!TextUtils.isEmpty(mProgressText)){
            canvas.drawText(mProgressText, textRectF.centerX(), baseline, mTextPaint);
        }
    }

    /**
     * 旋转角度
     */
    public void sweepAngle(float sweepAngle){
        mSweepAngle = sweepAngle;
        invalidate();
    }

    /**
     * 设置文字
     * @param progressText
     */
    public void setProgressText(String progressText){
        mProgressText = progressText;
        invalidate();
    }
}
