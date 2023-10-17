package com.ezreal.springframework.aop.framework.adapter;

import com.ezreal.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice methodBeforeAdvice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice methodBeforeAdvice) {
        this.methodBeforeAdvice = methodBeforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 执行前置处理
        this.methodBeforeAdvice.before(methodInvocation.getMethod(), methodInvocation.getThis(), methodInvocation.getArguments());

        // 执行原来的方法
        return methodInvocation.proceed();
    }

    public MethodBeforeAdvice getMethodBeforeAdvice() {
        return methodBeforeAdvice;
    }

    public void setMethodBeforeAdvice(MethodBeforeAdvice methodBeforeAdvice) {
        this.methodBeforeAdvice = methodBeforeAdvice;
    }
}
