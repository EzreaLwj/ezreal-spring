package com.ezreal.springframework.beans.factory;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String beanName) throws BeansException;

}
