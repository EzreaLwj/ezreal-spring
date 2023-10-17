package com.ezreal.test.bean;


import com.ezreal.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class SpouseAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object object, Object[] arguments) {
        System.out.println("关怀小两口(切面)：" + method);
    }
}
