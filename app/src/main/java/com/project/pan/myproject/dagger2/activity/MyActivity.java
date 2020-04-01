package com.project.pan.myproject.dagger2.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.project.pan.myproject.BaseApplication;
import com.project.pan.myproject.R;
import com.project.pan.myproject.dagger2.Person;
import com.project.pan.myproject.dagger2.di.component.AppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerMyComponent;

import javax.inject.Inject;
public class MyActivity extends AppCompatActivity {

    @Inject
    Person person;
//    @Name("hobby2")
//    @Inject
//    Hobby mHobby;
    @Inject
    Application mApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        AppComponent appComponent = BaseApplication.getAppComponent();
        DaggerMyComponent.builder().appComponent(appComponent).build().inject(this);

        person.setName("hello dagger");
       // mHobby.playBall();
        Toast.makeText(this, person.getName(),Toast.LENGTH_LONG).show();

        if (mApplication != null) {
            Log.e("DaggerActivity","application is not null");
        }
    }

    public void secondClick(View view) {
        startActivity(new Intent(this, SecondDagger2Activity.class));
    }
}
