package com.project.pan.myproject.designMode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: panrongfu
 * Date:2018/7/2 20:09
 * Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindName {
    String name();//定义注解的属性
    int age() default 25;
}
