package com.project.pan.myproject.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * @author: panrongfu
 * @date: 2018/11/16 15:25
 * @describe: 内部拦截
 */

public class HorizontalScrollViewEx2 extends ViewGroup {

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;

    private int mLastX = 0;
    private int mLastY = 0;

    private int mLastXIntercept = 0 ;
    private int mLastYIntercept = 0;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx2(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mScroller = new Scroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            if(!mScroller.isFinished()){
                mScroller.abortAnimation();
                return true;
            }
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX( );
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if(Math.abs(xVelocity) >= 50){
                    mChildIndex = xVelocity>0? mChildIndex-1:mChildIndex+1;
                }else {
                    mChildIndex = (scrollX+mChildWidth/2)/mChildWidth;
                }
                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildrenSize-1));

                int dx = mChildIndex * mChildWidth-scrollX;
                smoothScrollBy(dx,0);
                mVelocityTracker.clear();
                break;
                default:
                    break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx, int i) {
        mScroller.startScroll(getScrollX(),0,dx,0);
        invalidate();
    }

    @Override
    public void computeScroll() {
       if(mScroller.computeScrollOffset()){
           scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
           postInvalidate();
       }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth;
        int measuredHeight;
        final  int childCount = getChildCount();

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);

        if(childCount == 0){
            setMeasuredDimension(0,0);
        }else if(heightSpaceMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize,measuredHeight);
        }else if (widthSpaceMode == MeasureSpec.AT_MOST){
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth();
            setMeasuredDimension(measuredWidth,heightSpaceSize);
        }else {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth,measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

         int childLeft = 0;
         final int childCount = getChildCount();
         mChildrenSize = childCount;

         for(int i = 0; i < childCount; i++){
             final View childView = getChildAt(i);
             if(childView.getVisibility() != View.GONE){
                 final int childWidth = childView.getMeasuredWidth();
                 mChildWidth = childWidth;
                 childView.layout(childLeft,0,childLeft+childWidth,childView.getMeasuredHeight());
                 childLeft += childWidth;
             }
         }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
