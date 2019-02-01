package com.project.pan.myproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;

import com.project.pan.myproject.R;


public class ViewStubActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ViewStub viewStubLayout1;
    ViewStub viewStubLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        viewStubLayout1 = (ViewStub) findViewById(R.id.view_stub_layout1);
        viewStubLayout2 = (ViewStub) findViewById(R.id.view_stub_layout2);
    }
    //viewStubLayout1显示
    public void clickStub1(View view){
        try {
            View vsContent1 = viewStubLayout1.inflate();
        } catch (Exception e) {
            viewStubLayout1.setVisibility(View.VISIBLE);
        }

    }
    //viewStubLayout1不显示
    public void clickStub11(View view){
        viewStubLayout1.setVisibility(View.GONE);
    }

    //viewStubLayout2显示
    public void clickStub2(View view){
        try {
            View vsContent2 = viewStubLayout2.inflate();
        } catch (Exception e) {
            viewStubLayout2.setVisibility(View.VISIBLE);
        }
    }
    //viewStubLayout2不显示
    public void clickStub22(View view){
        viewStubLayout2.setVisibility(View.GONE);
    }
    //都显示
    public void clickStubAll(View view){
        try {
            viewStubLayout1.inflate();
            viewStubLayout2.inflate();
        } catch (Exception e) {
            viewStubLayout1.setVisibility(View.VISIBLE);
            viewStubLayout2.setVisibility(View.VISIBLE);
        }
    }
    //都不显示
    public void clickStubAll11(View view){
        viewStubLayout1.setVisibility(View.GONE);
        viewStubLayout2.setVisibility(View.GONE);
    }

    GestureDetector gestureDetector = new GestureDetector(this);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 手指轻触屏幕的一瞬间，由一个ACTION_DOWN 触发
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 手指轻触屏幕，尚未松开或拖动，由一个ACTION_DOWN触发
     * 它强调的是没有松开或者拖动的状态
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 手指（轻松触碰屏幕后）松开，伴随着ACTION_UP而触发，这是单击行为
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 手指按下屏幕并拖动，由ACTION_DOWN,多个ACTION_MOVE触发，这是拖动行为
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 用户长按
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 用户按下触摸屏，快速滑动后松开 由一个ACTION_DOWN,多个ACTION_MOVE,和一个ACTION_UP
     * 触发
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
