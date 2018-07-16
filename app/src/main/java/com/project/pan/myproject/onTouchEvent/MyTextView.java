package com.project.pan.myproject.onTouchEvent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author panRongfu
 * @date 2018/6/12 16:00
 * @describe Todo
 * @email panrongfu@banggood.com
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {

    private final static String MyTextView = "MyTextView";
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    /**
     * 所有的触摸事件，都需要通过这个方法来分发的
     * 1）.若返回 true 则表示事件被当前事件拦截
     * 2）.若返回 super.dispatchTouchEvent(event); 则表示继续分发事件
     * 3）.若返回 false 则表示当前事件不处理，往上传递给父层
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                Log.d(MyTextView,"dispatchTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d(MyTextView,"dispatchTouchEvent:"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(MyTextView,"dispatchTouchEvent:"+"ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 1）.返回值为true 表示当前视图可以处理对应的事件,事件将不会向上传递给父视图；
     * 2）.返回值为false 表示当前视图不处理这个事件，事件会被传递给父视图的onTouchEvent()进行处理
     * 3）.onTouch() 方法优先于onTouchEvent()
     * 4）.如果当前view设置了onClickListener 并且返回了true 则不会再调用onClick事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(MyTextView,"onTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d(MyTextView,"onTouchEvent"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(MyTextView,"onTouchEvent"+"ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
