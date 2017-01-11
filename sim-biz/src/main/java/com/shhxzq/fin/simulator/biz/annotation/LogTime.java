package com.shhxzq.fin.simulator.biz.annotation;

import java.lang.annotation.*;

/**
 * 在方法上加此注解会打印入参和出参，会计算方法消耗的时间
 *
 * @author kangyonggan
 * @since 2016/12/8
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTime {
}
