package com.project.pan.myproject.dagger2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.project.pan.myproject.BaseApplication;
import com.project.pan.myproject.R;
import com.project.pan.myproject.dagger2.di.component.AppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerAppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerManComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerMyComponent;
import com.project.pan.myproject.dagger2.di.component.ManComponent;
import com.project.pan.myproject.dagger2.di.component.SonComponent;
import com.project.pan.myproject.dagger2.di.module.MyModule;
import com.project.pan.myproject.dagger2.qualifier.Name;

import javax.inject.Inject;
public class DaggerActivity extends AppCompatActivity {

    @Inject
    Person person;
//    @Name("hobby2")
//    @Inject
//    Hobby mHobby;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        AppComponent mAppComponent = DaggerAppComponent.builder().build();
        DaggerMyComponent.builder().appComponent(mAppComponent).build().inject(this);

        person.setName("hello dagger");
       // mHobby.playBall();
        Toast.makeText(this, person.getName(),Toast.LENGTH_LONG).show();
    }

    public void secondClick(View view) {
        startActivity(new Intent(this,SecondDagger2Activity.class));
    }
}
