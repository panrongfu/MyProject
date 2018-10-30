package com.project.pan.myproject.jni;

/**
 * @author: panrongfu
 * @date: 2018/10/30 11:16
 * @describe:
 */

public class JniTest {
    static {
        System.loadLibrary("jniTest");
    }

    public native String get();

    public native void set(String str);
}
