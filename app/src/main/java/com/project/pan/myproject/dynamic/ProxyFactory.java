package com.project.pan.myproject.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: panrongfu
 * @date: 2019/2/27 11:50
 * @describe: 动态代理对象，不需要实现接口，但需要指定接口的类型
 */
public class ProxyFactory {

    //生成代理对象
    public static Object getProxyInstance(Object target){

        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    //Object proxy:被代理的对象
                    //Method method:要调用的方法
                    //Object[] args:方法调用时所需要参数
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始事务2");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        System.out.println("提交事务2");
                        return returnValue;
                    }
                });
    }
}
