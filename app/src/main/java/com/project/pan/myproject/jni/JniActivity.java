package com.project.pan.myproject.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.pan.myproject.R;

public class JniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
    }

    public void jniClick(View view){

       // JniSDK jniTestSdk = new JniSDK();

       // Toast.makeText(this, jniTestSdk.get(),Toast.LENGTH_LONG).show();
    }
}
