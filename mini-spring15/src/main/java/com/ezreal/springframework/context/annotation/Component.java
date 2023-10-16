package com.ezreal.springframework.context.annotation;

import java.lang.annotation.*;

/**
 * @author Ezreal
 * @Date 2023/9/23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Component {

    String value() default "";
}
