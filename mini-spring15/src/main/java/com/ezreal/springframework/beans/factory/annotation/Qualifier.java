package com.ezreal.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author Ezreal
 * @Date 2023/10/9
 */
@Target({ElementType.TYPE, ElementType.METHOD,ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Qualifier {
    String value() default "";
}
