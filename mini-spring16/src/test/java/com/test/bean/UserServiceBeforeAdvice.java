package com.test.bean;




import com.ezreal.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object object, Object[] arguments) {
        System.out.println("拦截方法：" + method.getName());
    }
}
