package com.project.pan.myproject.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.project.pan.myproject.R;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_STARTING;

public class WindowActivity extends AppCompatActivity {

    Button button;
    WindowManager.LayoutParams layoutParams;
    WindowManager windowManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        button = new Button(this);
        button.setText("浮动按钮");
        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_BASE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);
        layoutParams.x = 100;
        layoutParams.y = 300;
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        Log.e(">>>>>",rawX+"::"+rawY);
                        layoutParams.x = rawX;
                        layoutParams.y = rawY;
                        windowManager.updateViewLayout(button,layoutParams);
                        break;
                }
                return true;
            }
        });
        windowManager.addView(button,layoutParams);

    }

    @Override
    protected void onDestroy() {
        windowManager.removeView(button);
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }
}
