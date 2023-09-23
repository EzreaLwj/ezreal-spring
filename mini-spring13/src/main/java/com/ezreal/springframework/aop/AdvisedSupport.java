package com.ezreal.springframework.aop;


import com.ezreal.springframework.util.ClassUtil;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * 封装织入过程
 * @author Ezreal
 * @Date 2023/9/22
 */
public class AdvisedSupport {

    // ProxyConfig
    private boolean proxyTargetClass = false;

    /**
     * 目标对象
     */
    private Object targetClass;

    /**
     * 增强的方法（前置，后置等）
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器
     */
    private MethodMatcher methodMatcher;

    public AdvisedSupport() {

    }

    public AdvisedSupport(Object targetClass, MethodInterceptor methodInterceptor, MethodMatcher methodMatcher) {
        this.targetClass = targetClass;
        this.methodInterceptor = methodInterceptor;
        this.methodMatcher = methodMatcher;
    }

    public Object getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Object targetClass) {
        this.targetClass = targetClass;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    /**
     * 判断是不是一个cglib 创建的对象
     *
     * @return 结果
     */
    public boolean isProxyTargetClass() {
        return ClassUtil.isCglibProxyClass(getTargetClass().getClass());
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }
}
