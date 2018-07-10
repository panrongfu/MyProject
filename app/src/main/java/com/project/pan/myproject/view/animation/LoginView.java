package com.project.pan.myproject.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.project.pan.myproject.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * author: panrongfu
 * date:2018/7/10 10:36
 * describe: 登录按钮
 */

public class LoginView extends View {

    private RectF mRectF;//一个矩形
    private RectF mArcRectF;//圆弧区域
    private Paint mRectFPaint;//矩形画笔
    private Paint mTextPaint;//文字画笔
    private Paint mArcPaint;//圆弧画笔
    private Paint mArcTranPaint;//透明圆弧画笔

    private int mWidth;//控件的宽度
    private int mHeight;//控件的高度
    private int tempWidth;//这个主要是用来做循环减法的
    private boolean isShrink = false; //是否在执行收缩动画
    private boolean isStretch;//开始拉伸
    private int mRectFLeft;//收缩时候left边距
    private int mRectFRight;//收缩时候right边距

    private int startAngle=180;
    private int endAngle=270;
    private Disposable mDisposable;

    private static final String DEFAULT_TEXT="";
    private static final int DEFAULT_TEXT_SIZE =14;//14dp
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_PROGRESS_COLOR = Color.WHITE;
    private static final int DEFAULT_BG_COLOR =Color.YELLOW;
    private static final int DEFAULT_RADIUS = 15;//15dp
    private  String lText="";
    private  int    lTextSize;
    private  int    lTextColor;
    private  int    lProgressColor;
    private  int    lBgColor;
    private  int    lRadius;

    public LoginView(Context context) {
        super(context);
        initView(context,null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context mContext, AttributeSet attrs) {

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.loginView);
        lText = ta.getString(R.styleable.loginView_lText);
        lTextColor = ta.getColor(R.styleable.loginView_lTextColor,DEFAULT_TEXT_COLOR);
        lTextSize = (int) ta.getDimension(R.styleable.loginView_lTextSize,DEFAULT_TEXT_SIZE);//获取到的是px
        lProgressColor = ta.getColor(R.styleable.loginView_lProgressColor,DEFAULT_PROGRESS_COLOR);
        lBgColor = ta.getColor(R.styleable.loginView_lBgColor,DEFAULT_BG_COLOR);
        lRadius = (int) ta.getDimension(R.styleable.loginView_lRadius,DEFAULT_RADIUS);//获取到的是px
        ta.recycle();

        mRectFPaint = new Paint();
        mTextPaint = new Paint();
        mArcPaint = new Paint();
        mArcTranPaint = new Paint();
        mRectFPaint.setAntiAlias(true);//设置抗锯齿
        mTextPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        tempWidth=mWidth = getWidth();//获取控件的宽度
        mHeight = getHeight();//获取控件的高度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setTextSize(lTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mRectFPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(5);
        mRectFPaint.setColor(lBgColor);
        mTextPaint.setColor(lTextColor);
        mArcPaint.setColor(lProgressColor);
        mArcTranPaint.setColor(Color.TRANSPARENT);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();

        if(isShrink){//收缩状态
            mRectF = new RectF(mRectFLeft,0,mRectFRight,mHeight);
            int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawRoundRect(mRectF,lRadius,lRadius,mRectFPaint);
            canvas.drawText(lText, mRectF.centerX(), baseline, mTextPaint);
            if(mRectFLeft == mWidth/2-mHeight/2
                    || mRectFRight == mWidth/2+mHeight/2){
                //把原来的文字覆盖掉
                canvas.drawRoundRect(mRectF,lRadius,lRadius,mRectFPaint);
                mArcRectF = new RectF(mRectFLeft+mHeight/4,mHeight/4,mRectFRight-mHeight/4,mHeight-mHeight/4);
                //canvas.drawArc 是顺时针绘制
                //绘制不透明部分
                canvas.drawArc(mArcRectF, startAngle, 270, false, mArcPaint);
                //绘制透明部分
                canvas.drawArc(mArcRectF, endAngle, 90, false, mArcTranPaint);
            }
        }else {
            if(!isStretch){//刚开始初始化状态
                mRectFLeft = 0;
                mRectFRight = mWidth;
            }
            mRectF = new RectF(mRectFLeft,0,mRectFRight,mHeight);
            int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawRoundRect(mRectF,lRadius,lRadius,mRectFPaint);
            canvas.drawText(lText, mRectF.centerX(), baseline, mTextPaint);
        }
    }

    public void setlText(String lText) {
        this.lText = lText;
        invalidate();
    }

    public void setlTextColor(int lTextColor) {
        this.lTextColor = lTextColor;
        invalidate();
    }

    public void setlProgressColor(int lProgressColor) {
        this.lProgressColor = lProgressColor;
        invalidate();
    }

    public void setlBgColor(int lBgColor) {
        this.lBgColor = lBgColor;
        invalidate();
    }

    /**
     * 开始收缩
     * @param milliseconds
     */
    public void startShrink(int milliseconds){
        isStretch = false;//拉伸设置为false
        if(isShrink) return;//如果正在执行动画这返回（点击无效）
        isShrink = true;
        mRectFLeft = 0;
        mRectFRight = mWidth;
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
                        startAngle+=1;
                        endAngle+=1;
                       // if(startAngle == 0) startAngle = 180;
                      //  if(endAngle == 180) endAngle = 0;
                        tempWidth--;
                        //宽度取余用来对mRectFRight作减法 和mRectFLeft做加法
                        if(tempWidth%2==0){
                            //当mRectFRight减到
                            if(mRectFRight < mWidth/2+mHeight/2
                                    || mRectFRight == mWidth/2+mHeight/2){
                                mRectFRight = mWidth/2+mHeight/2;
                                //shrinkStop();
                            }else {
                                mRectFRight--;
                            }
                        }else {
                            if(mRectFLeft == mWidth/2-mHeight/2
                                    || mRectFLeft > mWidth/2-mHeight/2){
                                mRectFLeft = mWidth/2-mHeight/2;
                                //shrinkStop();
                            }else {
                                mRectFLeft++;
                            }
                        }
                    }
                });
    }

    /**
     * 开始拉伸
     */
    public void startStretch(int milliseconds){
        isShrink = false;//收缩设置为false
        if(isStretch) return;//如果正在执行动画则返回（点击无效）
        isStretch = true;
        shrinkStop();
        isShrink = false;
        mRectFLeft = mRectFRight = mWidth/2;
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
                        tempWidth ++;
                        if(tempWidth%2==0){
                            if(mRectFLeft < 0 || mRectFLeft == 0){
                                mRectFLeft = 0;
                                stretchStop();
                            }
                            mRectFLeft--;
                        }else {
                            if(mRectFRight > mWidth || mRectFRight == mWidth){
                                mRectFRight = mWidth;
                                stretchStop();
                            }
                            mRectFRight++;
                        }
                    }
                });
        
    }

    /**
     * 拉伸停止
     */
    public void stretchStop(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

    /**
     * 回缩停止
     */
    private void shrinkStop() {
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
