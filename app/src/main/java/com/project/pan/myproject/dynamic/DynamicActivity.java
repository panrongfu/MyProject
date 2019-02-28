package com.project.pan.myproject.dynamic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.pan.myproject.R;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        hook();
    }

    private void hook() {
        Button button = findViewById(R.id.button28);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        hookOnClickListener(button);
    }

    private void hookOnClickListener(View view) {
        try {
            // 得到 View 的 ListenerInfo 对象
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);
            // 得到 原始的 OnClickListener 对象
            Class<?> listenerInfoClz  = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);
            // 用自定义的 OnClickListener 替换原始的 OnClickListener
            View.OnClickListener hookedOnClickListener = (View.OnClickListener) Proxy.newProxyInstance(originOnClickListener.getClass().getClassLoader(),
                    originOnClickListener.getClass().getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Log.i("hook","Before click, do what you want to to.");
                            Object object = method.invoke(originOnClickListener, args);
                            return object;
                        }
                    });
            mOnClickListener.set(listenerInfo,hookedOnClickListener);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){

        /**
         * 静态代理总结:
         * 1.可以做到在不修改目标对象的功能前提下,对目标功能扩展.
         * 2.缺点:
         *
         * 因为代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多.同时,
         * 一旦接口增加方法,目标对象与代理对象都要维护.
         */

        //目标对象
        UserDao target1 = new UserDao();
        //代理对象，把目标对象传给代理对象，建立代理关系
        UserDaoProxy proxy1 = new UserDaoProxy(target1);
        //执行代理方法
        proxy1.save();

        /**
         * 动态代理
         * 代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理
         */

        // 目标对象
        IUserDao target2 = new UserDao();
        // 给目标对象，创建代理对象
        IUserDao proxy2 = (IUserDao) ProxyFactory.getProxyInstance(target2);
        // 执行方法   【代理对象】
        proxy2.save();
    }


}
