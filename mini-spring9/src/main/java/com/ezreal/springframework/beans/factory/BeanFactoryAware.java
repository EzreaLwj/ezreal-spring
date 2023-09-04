package com.ezreal.springframework.beans.factory;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.BeanFactory;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
