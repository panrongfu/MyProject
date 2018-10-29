package com.project.pan.myproject.onTouchEvent;

import android.content.Context;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * @author panRongfu
 * @date 2018/6/13 11:10
 * @describe Todo
 * @email panrongfu@banggood.com
 */

public class MyRelativeLayout extends RelativeLayout {

    private static final String MyRelativeLayout = "MyRelativeLayout";

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 所有的触摸事件，都需要通过这个方法来分发的
     * 1）.若返回 true 则表示事件被当前事件拦截(例如：MotionEvent.ACTION_UP 返回 true)
     *    OnTouchEventActivity2: dispatchTouchEvent:ACTION_DOWN
     *    MyRelativeLayout: dispatchTouchEvent:ACTION_DOWN
     *    MyRelativeLayout: onInterceptTouchEventACTION_UP
     *    MyTextView: dispatchTouchEvent:ACTION_DOWN
     *    myTextView: onTouch:ACTION_DOWN
     *    MyTextView: onTouchEvent:ACTION_DOWN
     *    OnTouchEventActivity2: dispatchTouchEventACTION_UP
     *    MyRelativeLayout: dispatchTouchEvent:ACTION_UP
     * 2）.若返回 false 则表示事件向上传递到父布局的onTouchEvent()方法(例如：MotionEvent.ACTION_UP 返回 false)
     *    OnTouchEventActivity2: dispatchTouchEvent:ACTION_DOWN
     *    MyRelativeLayout: dispatchTouchEvent:ACTION_DOWN
     *    MyRelativeLayout: onInterceptTouchEventACTION_UP
     *    MyTextView: dispatchTouchEvent:ACTION_DOWN
     *    myTextView: onTouch:ACTION_DOWN
     *    MyTextView: onTouchEvent:ACTION_DOWN
     *    OnTouchEventActivity2: dispatchTouchEventACTION_UP
     *    MyRelativeLayout: dispatchTouchEvent:ACTION_UP
     *    OnTouchEventActivity2: onTouchEventACTION_UP
     * 3）.若返回 super.dispatchTouchEvent(event); 则表示继续分发事件
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(MyRelativeLayout,"dispatchTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d(MyRelativeLayout,"dispatchTouchEvent:"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(MyRelativeLayout,"dispatchTouchEvent:"+"ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 1）.返回值为true 表示当前视图可以处理对应的事件,事件将不会向上传递给父视图；
     * 2）.返回值为false 表示当前视图不处理这个事件，事件会被传递给父视图的onTouchEvent()进行处理
     * 3）.返回值为false 表示当前视图不处理这个事件，事件会被传递给父视图的onTouchEvent()进行处理
     * 4）.onTouch() 方法优先于onTouchEvent()
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(MyRelativeLayout,"onTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d(MyRelativeLayout,"onTouchEvent"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(MyRelativeLayout,"onTouchEvent"+"ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 1）.返回super 则调用子view的dispatchTouchEvent()方法
     * 2）.返回false 则调用子view的dispatchTouchEvent()方法
     * 3）.返回true  则执行当前viewgroup的onTouchEvent()方法
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(MyRelativeLayout,"onInterceptTouchEvent"+"ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(MyRelativeLayout,"onInterceptTouchEvent"+"ACTION_UP");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(MyRelativeLayout,"onInterceptTouchEvent"+"ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

}
