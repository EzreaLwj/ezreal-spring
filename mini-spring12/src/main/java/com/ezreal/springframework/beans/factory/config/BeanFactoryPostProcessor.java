package com.ezreal.springframework.beans.factory.config;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.ConfigurableListableBeanFactory;

/**
 * 后置工厂处理器
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有beanDefinition处理完后执行该方法
     * @param configurableBeanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory configurableBeanFactory) throws BeansException;
}
