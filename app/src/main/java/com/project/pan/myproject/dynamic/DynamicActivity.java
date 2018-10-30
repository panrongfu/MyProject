package com.project.pan.myproject.dynamic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.pan.myproject.R;

import java.io.File;

public class DynamicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
    }



    protected void launchTargetActivity(String className){
        File dexOutputDir = this.getDir("dex",0);
    }
}
