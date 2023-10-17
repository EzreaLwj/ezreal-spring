package com.ezreal.springframework.aop.framework;



import com.ezreal.springframework.aop.AdvisedSupport;
import com.ezreal.springframework.aop.MethodMatcher;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advisedSupport.getTargetClass().getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodMatcher methodMatcher = advisedSupport.getMethodMatcher();

        if (methodMatcher.matches(method, advisedSupport.getTargetClass().getClass())) {
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advisedSupport.getTargetClasses(), method, args));
        }

        return method.invoke(advisedSupport.getTargetClass(), args);
    }

}
