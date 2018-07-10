package com.project.pan.myproject.designMode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: panrongfu
 * Date:2018/7/2 20:40
 * Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindText {

    String testValue() default "test";
   // 类型 参数名() default 默认值;
}
