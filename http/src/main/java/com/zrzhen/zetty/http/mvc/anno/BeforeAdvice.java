package com.zrzhen.zetty.http.mvc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 前置拦截器
 * 注解在拦截器上，id值标识该拦截器，为空则默认为简单类名的小写
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeforeAdvice {

    String id() default "";
}
