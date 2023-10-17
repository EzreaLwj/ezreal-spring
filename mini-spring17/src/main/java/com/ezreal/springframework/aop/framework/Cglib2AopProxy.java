package com.ezreal.springframework.aop.framework;

import com.ezreal.springframework.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public class Cglib2AopProxy implements AopProxy {

    private AdvisedSupport advisedSupport;

    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetClass().getClass());
        enhancer.setInterfaces(advisedSupport.getTargetClass().getClass().getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor());
        return enhancer.create();
    }

    private class DynamicAdvisedInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            if (advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetClass().getClass())) {
                return advisedSupport.getMethodInterceptor().invoke(new CglibMethodInvocation(advisedSupport.getTargetClasses(), method, objects, methodProxy));
            }
            return method.invoke(advisedSupport.getTargetClass(), objects);
        }
    }


    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object object, Method method, Object[] arguments,MethodProxy methodProxy) {
            super(object, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }
    }

}
