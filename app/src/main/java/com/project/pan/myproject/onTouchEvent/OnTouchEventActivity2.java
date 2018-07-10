package com.project.pan.myproject.onTouchEvent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.project.pan.myproject.R;


public class OnTouchEventActivity2 extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    MyTextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_touch_event2);
        myTextView = findViewById(R.id.myTextView);
        myTextView.setOnClickListener(this);
        myTextView.setOnTouchListener(this);
    }

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
                Log.d("OnTouchEventActivity2","dispatchTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d("OnTouchEventActivity2","dispatchTouchEvent"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("OnTouchEventActivity2","dispatchTouchEvent"+"ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("OnTouchEventActivity2","onTouchEvent:"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                Log.d("OnTouchEventActivity2","onTouchEvent"+"ACTION_HOVER_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("OnTouchEventActivity2","onTouchEvent"+"ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
