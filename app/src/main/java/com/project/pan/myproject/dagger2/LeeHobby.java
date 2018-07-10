package com.project.pan.myproject.dagger2;

import android.util.Log;
import javax.inject.Inject;

/**
 * Author: panrongfu
 * Date:2018/6/26 13:50
 * Description:
 */

public class LeeHobby implements Hobby {

    @Inject
    public LeeHobby() {
    }

    @Override
    public void playBall() {
        Log.e("LeeHobby","打球去");
    }

    @Override
    public void singSong() {
        Log.e("LeeHobby","唱歌去");
    }
}
