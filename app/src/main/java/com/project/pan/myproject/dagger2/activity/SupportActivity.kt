package com.project.pan.myproject.dagger2.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.test.internal.runner.RunnerArgs
import com.project.pan.myproject.R
import com.project.pan.myproject.dagger2.Bike
import com.project.pan.myproject.dagger2.Move
import com.project.pan.myproject.dagger2.TomHobby
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class SupportActivity : BaseActivity() {

//    @Inject lateinit var bike: Bike
//    @Inject lateinit var hobby: TomHobby;
    @Inject lateinit var move: Move
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
      //  bike.go()
    }
}
