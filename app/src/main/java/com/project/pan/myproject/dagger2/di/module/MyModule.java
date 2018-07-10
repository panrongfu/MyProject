package com.project.pan.myproject.dagger2.di.module;

import com.project.pan.myproject.dagger2.Hobby;
import com.project.pan.myproject.dagger2.LeeHobby;
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
    Hobby provideHobby(LeeHobby leeHobby){
        leeHobby.playBall();
        leeHobby.singSong();
        return leeHobby;
    }

}
