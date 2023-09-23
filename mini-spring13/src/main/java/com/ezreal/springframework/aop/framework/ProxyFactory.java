package com.ezreal.springframework.aop.framework;

import com.ezreal.springframework.aop.AdvisedSupport;

/**
 * 代理工厂
 * @author Ezreal
 * @Date 2023/9/22
 */
public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createProxy().getProxy();
    }

    public AopProxy createProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
