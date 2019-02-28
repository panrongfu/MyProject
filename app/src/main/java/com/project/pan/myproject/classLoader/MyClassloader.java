package com.project.pan.myproject.classLoader;

/**
 * @author: panrongfu
 * @date: 2019/2/20 15:59
 * @describe:
 */
public class MyClassloader extends ClassLoader {
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
       //首先，检查请求的类是否已经被加载过了
        Class c = findLoadedClass(name);
        if (c == null){

        }

        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
