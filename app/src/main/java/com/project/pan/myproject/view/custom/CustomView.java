package com.project.pan.myproject.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @author: panrongfu
 * @date: 2018/10/12 9:43
 * @describe:
 */

@SuppressLint("AppCompatCustomView")
public class CustomView extends TextView {

    Scroller mScroller;
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 如果有线程或者动画，需要及时停止，则需要在此方法调用即可
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果子View被分发了事件，请求父布局不要在之后拦截事件
       // getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);

    }

    public void init(){
        mScroller = new Scroller(getContext());
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        mScroller.startScroll(scrollX,0,deltaX,0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
       // super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
