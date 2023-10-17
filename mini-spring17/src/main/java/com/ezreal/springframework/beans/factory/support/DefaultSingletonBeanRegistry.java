package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.DisposableBean;
import com.ezreal.springframework.beans.factory.ObjectFactory;
import com.ezreal.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供获取bean单例的功能
 *
 * @author Ezreal
 * @Date 2023/9/2
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存
     */
    private Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * 三级缓存
     */
    private Map<String, ObjectFactory<?>> singletonFactory = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String name) {

        Object singletonObject = singletonObjects.get(name);
        if (singletonObject == null) {
            singletonObject = earlySingletonObjects.get(name);
            if (singletonObject == null) {
                ObjectFactory<?> objectFactory = singletonFactory.get(name);
                if (objectFactory != null) {
                    singletonObject = objectFactory.getObject();
                    // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(name, singletonObject);
                    singletonFactory.remove(name);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);

        singletonFactory.remove(beanName);
        earlySingletonObjects.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> objectFactory) {

        if (!singletonObjects.containsKey(beanName)) {

            singletonFactory.put(beanName, objectFactory);
            earlySingletonObjects.remove(beanName);
        }
    }

    protected void addSingleton(String name, Object singletonObject) {
        singletonObjects.put(name, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }

}
