package com.project.pan.myproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.project.pan.myproject.R;

public class HoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole);
//        CenterLayoutAnimView centerLayoutAnimView = findViewById(R.id.center_view);
//        centerLayoutAnimView.showSwitchPageAnim();
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_layer_name);
        lottieAnimationView.setAnimation("test.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
    }
}
