package com.project.pan.myproject.onTouchEvent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.project.pan.myproject.R;

/**
 * @author panRongfu
 * @date 2018/6/12 15:59
 * @describe Todo
 * @email panrongfu@banggood.com
 */

public class OnTouchEventActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    private MyTextView myTextView;
    private static final String OnTouchEventActivity = "OnTouchEventActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ontouch_event_activity);
        myTextView = findViewById(R.id.myTextView);
        myTextView.setOnTouchListener(this);
        myTextView.setOnClickListener(this);
    }

    public void viewGroupTouch(View view){
        startActivity(new Intent(this,OnTouchEventActivity2.class));
    }

    /**
     * 1）.返回false 则会执行当前view（MyTextView）的onTouchEvent()方法
     * 2）.返回true 表示自己消费当前事件（如果设置了setOnClickListener，则会调用onclick（）方法）
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.myTextView:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d("myTextView","onTouch:"+"ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_HOVER_MOVE:
                        Log.d("myTextView","onTouch"+"ACTION_HOVER_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("myTextView","onTouch"+"ACTION_UP");
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myTextView:
                Log.d("myTextView","onClick");
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(OnTouchEventActivity,"dispatchTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d(OnTouchEventActivity,"dispatchTouchEvent"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(OnTouchEventActivity,"dispatchTouchEvent"+"ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(OnTouchEventActivity,"onTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d(OnTouchEventActivity,"onTouchEvent"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(OnTouchEventActivity,"onTouchEvent"+"ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
