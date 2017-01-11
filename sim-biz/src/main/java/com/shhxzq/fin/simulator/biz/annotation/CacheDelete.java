package com.shhxzq.fin.simulator.biz.annotation;

import java.lang.annotation.*;

/**
 * 删除缓存
 *
 * @author kangyonggan
 * @since 2016/12/8
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDelete {

    String value();

}
