package com.ezreal.springframework.beans;

import com.ezreal.springframework.BeansException;

import java.beans.Beans;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

}
