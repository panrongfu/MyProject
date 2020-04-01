package com.project.pan.myproject.dagger2.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.test.internal.runner.RunnerArgs
import com.project.pan.myproject.R
import com.project.pan.myproject.dagger2.Bike
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class SupportActivity : AppCompatActivity(),HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var bike: Bike

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
        AndroidInjection.inject(this)
        bike.go()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
       return dispatchingActivityInjector
    }

}
