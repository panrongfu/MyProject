package com.project.pan.myproject.classLoader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.pan.myproject.R;


/**
 * ClassLoader就是类加载器，普通的java开发者其实用得并不多，但是对于某些框架开发者来说，却是非常常见。
 * 理解ClassLoader的加载机制，也有利于我们编写出更高效的代码，ClassLoader的具体作用就是将Class文件
 * 加载到jvm虚拟机中去，程序就可以正确运行了，但是JVM启动的时候，并不会一次性加载所有的class文件，而是
 * 根据需要去动态加载
 */
public class ClassLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_loader);
    }
}
