package com.project.pan.myproject.dagger2.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/27 11:52
 * @Description: java类作用描述
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Favor {
    String value() default "";
}
