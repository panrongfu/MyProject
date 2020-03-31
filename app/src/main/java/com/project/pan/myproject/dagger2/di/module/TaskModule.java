package com.project.pan.myproject.dagger2.di.module;

import android.content.Context;

import com.project.pan.myproject.dagger2.TaskUtil;
import com.project.pan.myproject.dagger2.di.component.SonComponent;

import dagger.Module;
import dagger.Provides;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/31 14:25
 * @Description: java类作用描述
 */
@Module(subcomponents = SonComponent.class)
public class TaskModule {

    @Provides
    TaskUtil provideTaskUtil(Context context) {
        return new TaskUtil(context);
    }
}
