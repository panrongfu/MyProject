package com.project.pan.myproject.dagger2.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: panrongfu
 * @CreateDate: 2020/3/31 09:22
 * @Description: java类作用描述
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface SonScope {
}
