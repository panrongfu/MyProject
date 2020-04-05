package com.project.pan.myproject

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.project.pan.myproject.crash.CrashHandler
import com.project.pan.myproject.dagger2.di.component.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Author: panrongfu
 * Date:2018/6/26 20:18
 * Description:
 */
class BaseApplication : Application(),HasActivityInjector{

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
//        fatherComponent = DaggerFatherComponent.builder().context(this.applicationContext).build()
//        appComponent = DaggerAppComponent.builder()
//                .name("aaa")
//                .application(this)
//                .build()
        DaggerSupportAppComponent.builder().build().inject(this)

        //这里是为了应用设置异常处理，然后程序才能获取到未处理的异常
        val crashHandler = CrashHandler.getInstance()
        crashHandler.init(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        @JvmStatic
        var appComponent: AppComponent? = null
            private set
        var fatherComponent: FatherComponent? = null
            private set
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}