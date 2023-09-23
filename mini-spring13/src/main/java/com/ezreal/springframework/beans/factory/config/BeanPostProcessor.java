package com.ezreal.springframework.beans.factory.config;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return 结果对象
     * @throws BeansException 异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean     bean对象
     * @param beanName bean名称
     * @return bean
     * @throws BeansException 异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
