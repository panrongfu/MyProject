package com.project.pan.myproject.view.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.pan.myproject.R;


public class AnimationActivity extends AppCompatActivity {

    PointView pointView;
    LoginView loginView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        pointView = findViewById(R.id.pointer_view);
        loginView = findViewById(R.id.hrz_login_btn);
        loginView.setOnClickListener(view-> loginView.startShrink(1));
    }

    public void clickStart (View view){

        pointView.startStretch(1);
    }

    public void clickFinish(View view){
        pointView.startCompress(3);
    }

    public void clickLoginButton(View view){

        loginView.startStretch(1);
    }


}
