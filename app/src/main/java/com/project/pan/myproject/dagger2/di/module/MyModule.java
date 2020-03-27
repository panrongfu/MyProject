package com.project.pan.myproject.dagger2.di.module;

import com.project.pan.myproject.dagger2.Hobby;
import com.project.pan.myproject.dagger2.LeeHobby;
import com.project.pan.myproject.dagger2.TomHobby;
import com.project.pan.myproject.dagger2.qualifier.Name;

import dagger.Module;
import dagger.Provides;

/**
 * Author: panrongfu
 * Date:2018/6/26 13:13
 * Description:
 */
@Module
public class MyModule {

    @Provides
    @Name("hobby1")
    Hobby provideHobby1(LeeHobby leeHobby){
        leeHobby.playBall();
        leeHobby.singSong();
        return leeHobby;
    }

    @Provides
    @Name("hobby2")
    Hobby provideHobby2(TomHobby tomHobby){
        tomHobby.playBall();
        tomHobby.singSong();
        return tomHobby;
    }

}
