package com.project.pan.myproject.view.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.project.pan.myproject.R;

/**
 * @author: panrongfu
 * @date: 2018/10/13 10:17
 * @describe:
 */

public class CustomViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            // TODO: 2018/10/13 获取控件的宽高
        }

        TextView textView = new TextView(this);

    }

    public void scrollClick(View view){

        CustomView textView = findViewById(R.id.custom_view);
        textView.smoothScrollTo(-10,10);

    }
}
