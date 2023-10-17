package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册bean定义的接口
 * @author Ezreal
 * @Date 2023/9/2
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    /**
     * 使用Bean名称查询BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * Return the names of all beans defined in this registry.
     *
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}
