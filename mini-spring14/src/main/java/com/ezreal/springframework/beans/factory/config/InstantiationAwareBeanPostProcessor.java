package com.ezreal.springframework.beans.factory.config;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    Object postProcessBeforeInstantiation(Class<?> aClass, String beanName) throws BeansException;

}
