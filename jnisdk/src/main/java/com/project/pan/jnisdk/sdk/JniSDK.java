package com.project.pan.jnisdk.sdk;

import android.util.Log;

/**
 * @author: panrongfu
 * @date: 2018/10/31 14:04
 * @describe:
 */

public class JniSDK {

    static {
        System.loadLibrary("jniSdk");
    }

    public native String get();

    public native void set(String str);

    public static void methodCalledByJni(String msgFromJni){
        Log.e("jni","jni 回调 java 方法");
    }
}
