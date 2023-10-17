package com.ezreal.springframework.aop;

import java.lang.reflect.Method;

/**
 * 前置方法执行
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

    void before(Method method, Object object, Object[] arguments);

}
