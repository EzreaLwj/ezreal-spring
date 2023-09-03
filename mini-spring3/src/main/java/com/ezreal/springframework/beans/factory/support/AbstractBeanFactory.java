package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.BeanFactory;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import jdk.nashorn.internal.codegen.ObjectCreator;

/**
 * 抽象bean工厂
 * @author Ezreal
 * @Date 2023/9/2
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }


    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    /**
     * 获取bean定义
     * @param name bean的名称
     * @return bean定义
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    /**
     * 创建一个bean
     * @param name bean名称
     * @param beanDefinition bean定义
     * @return 结果
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object... args) throws BeansException;
}
