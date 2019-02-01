package com.project.pan.myproject.dynamic;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: panrongfu
 * @date: 2018/12/8 15:26
 * @describe:
 */

public class Reflect {
    private String test = "test abc";
    protected void launchTargetActivity(String className){
        //   File dexOutputDir = this.getDir("dex",0);
        System.out.println("launchTargetActivity");
    }

    public static void main(String[] arg){

        ClassLoader c = DynamicActivity.class.getClassLoader();
        System.out.println(c);
        ClassLoader cl = c.getParent();
        System.out.println(cl.getParent());


        try {
            Class clazz = Class.forName("com.project.pan.myproject.dynamic.Reflect");
            Reflect reflect = (Reflect) clazz.newInstance();
            Method method = clazz.getDeclaredMethod("launchTargetActivity",String.class);
            method.setAccessible(true);
            Field testField =  clazz.getDeclaredField("test");
            testField.setAccessible(true);
            String testValue = (String) testField.get(reflect);
            System.out.println(testValue+">>>");
            method.invoke(reflect,"abc");
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
