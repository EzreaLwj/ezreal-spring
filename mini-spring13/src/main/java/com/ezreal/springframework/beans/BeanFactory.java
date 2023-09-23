package com.ezreal.springframework.beans;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
