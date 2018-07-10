package com.project.pan.myproject.dagger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.project.pan.myproject.BaseApplication;
import com.project.pan.myproject.R;
import com.project.pan.myproject.dagger2.di.component.AppComponent;
import com.project.pan.myproject.dagger2.di.component.DaggerMyComponent;
import com.project.pan.myproject.dagger2.di.module.MyModule;
import javax.inject.Inject;
public class DaggerActivity extends AppCompatActivity {

    @Inject
    Person person;
    AppComponent mAppComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        mAppComponent = ((BaseApplication)getApplicationContext()).getAppComponent();
        DaggerMyComponent
                .builder()
                .myModule(new MyModule())
                .appComponent(mAppComponent)
                .build()
                .inject(this);

        person.setName("hello dagger");
        Toast.makeText(this, person.getName(),Toast.LENGTH_LONG).show();


        mAppComponent.getAppTools().showTools();
    }
}
