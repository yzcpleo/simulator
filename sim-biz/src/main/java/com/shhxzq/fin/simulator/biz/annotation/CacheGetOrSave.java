package com.shhxzq.fin.simulator.biz.annotation;

import java.lang.annotation.*;

/**
 * 有缓存就走缓存，没缓存的最后放入缓存
 *
 * @author kangyonggan
 * @since 2016/12/8
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheGetOrSave {

    String value();

    long timeout() default 0L;

}
