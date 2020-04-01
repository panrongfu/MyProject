package com.project.pan.myproject.dagger2.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.project.pan.myproject.BaseApplication
import com.project.pan.myproject.R
import com.project.pan.myproject.dagger2.Bike
import com.project.pan.myproject.dagger2.Car
import com.project.pan.myproject.dagger2.TaskUtil
import javax.inject.Inject

class SecondDagger2Activity : AppCompatActivity() {

    @Inject
    lateinit var car: Car
    @Inject
    lateinit var bike: Bike
    @Inject
    lateinit var taskUtil: TaskUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_dagger2)
        var fatherComponent = BaseApplication.fatherComponent;
        fatherComponent?.sonComponent()?.build()?.inject(this)
        bike.go()
        car.go()
        taskUtil.sayHi()
    }
}
