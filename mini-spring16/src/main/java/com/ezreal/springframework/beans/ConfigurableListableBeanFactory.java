package com.ezreal.springframework.beans;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    /**
     * 获取bean的定义
     *
     * @param beanName bean的名称
     * @return BeanDefinition
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;


    /**
     * 提前创建所有单例对象
     *
     * @throws BeansException bean异常
     */
    void preInstantiateSingletons() throws BeansException;

}
