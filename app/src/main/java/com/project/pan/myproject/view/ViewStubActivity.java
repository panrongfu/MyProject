package com.project.pan.myproject.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.project.pan.myproject.R;


public class ViewStubActivity extends AppCompatActivity {

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
}
