package com.project.pan.myproject.observer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.pan.myproject.R;


public class ObserverActivity extends AppCompatActivity {

    PersonObservable personObservable;
    MyObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        personObservable = new PersonObservable();

    }

    public void btnClick (View view){
        myObserver = new MyObserver();
        personObservable.addObserver(myObserver);
        personObservable.setAge(25);
    }
}
