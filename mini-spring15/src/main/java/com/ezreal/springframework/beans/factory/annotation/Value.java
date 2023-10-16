package com.ezreal.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author Ezreal
 * @Date 2023/10/9
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
