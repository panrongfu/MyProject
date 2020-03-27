package com.project.pan.myproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.pan.myproject.R;

public class BatteryActivity extends AppCompatActivity {

    NoTextBatteryView mBatteryView;
    float defaultPower = 80f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        mBatteryView = findViewById(R.id.batteryView);
    }

    public void add(View view) {
        defaultPower++;
        mBatteryView.setBatteryPower(defaultPower);
    }

    public void reduce(View view) {
        defaultPower -- ;
        mBatteryView.setBatteryPower(defaultPower);
    }
}
