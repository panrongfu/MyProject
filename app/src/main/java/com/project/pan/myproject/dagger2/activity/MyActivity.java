package com.project.pan.myproject.dagger2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.pan.myproject.R;
public class MyActivity extends BaseActivity {

//    @Inject
//    Person person;
//    @Name("hobby2")
//    @Inject
//    Hobby mHobby;
//    @Inject
//    Application mApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

//
//        person.setName("hello dagger");
//       // mHobby.playBall();
//        Toast.makeText(this, person.getName(),Toast.LENGTH_LONG).show();
//
//        if (mApplication != null) {
//            Log.e("DaggerActivity","application is not null");
//        }
    }

    public void secondClick(View view) {
        startActivity(new Intent(this, SecondDagger2Activity.class));
    }

    public void daggerAndroid(View view) {
        startActivity(new Intent(this,SupportActivity.class));
    }
}
