package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册FactoryBean类
 *
 * @author Ezreal
 * @Date 2023/9/4
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object factoryBean = factoryBeanObjectCache.get(beanName);
        return factoryBean == NULL_OBJECT ? null : factoryBean;
    }

    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {

        if (factoryBean.isSingleton()) {
            Object cachedBean = getCachedObjectForFactoryBean(beanName);
            if (cachedBean == null) {
                cachedBean = doGetObjectFromFactoryBean(factoryBean, beanName);
                factoryBeanObjectCache.put(beanName, cachedBean == null ? NULL_OBJECT : cachedBean);
            }
            return cachedBean == NULL_OBJECT ? null : cachedBean;
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }

    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
