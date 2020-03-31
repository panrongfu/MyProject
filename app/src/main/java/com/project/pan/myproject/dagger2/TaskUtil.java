package com.project.pan.myproject.dagger2;

import android.content.Context;
import android.util.Log;

import com.project.pan.myproject.R;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/31 14:26
 * @Description: java类作用描述
 */
public class TaskUtil {

    Context mContext;

    public TaskUtil(Context context) {
        mContext = context;
        if (mContext != null) {
            Log.e("TaskUtil","context is not null");
        }
    }

    public void sayHi(){
        Log.e("TaskUtil", mContext.getResources().getString(R.string.app_name) +">>>>>>>>>>");
    }
}
