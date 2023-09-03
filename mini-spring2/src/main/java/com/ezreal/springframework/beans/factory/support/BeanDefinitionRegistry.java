package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册bean定义的接口
 * @author Ezreal
 * @Date 2023/9/2
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

}
