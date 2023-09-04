package com.ezreal.test.bean;

import com.ezreal.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {

    @Override
    public IUserDao getObject() throws Exception {

        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("10001", "小傅哥");
                hashMap.put("10002", "八杯水");
                hashMap.put("10003", "阿毛");

                return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
            }
        };

        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, invocationHandler);
    }

    @Override
    public Class<?> getClassType() {
        return IUserDao.class;
    }

    @Override
    public Boolean isSingleton() {
        return true;
    }
}
