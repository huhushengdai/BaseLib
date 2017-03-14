package com.blizzmi.bxlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date： 2017/2/23
 * Description:
 * 获取布局id的注解
 *
 * @author WangLizhi
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutId {

    int value() default 0;
}
