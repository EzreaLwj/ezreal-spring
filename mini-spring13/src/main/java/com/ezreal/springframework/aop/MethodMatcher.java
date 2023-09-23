package com.ezreal.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);

}
