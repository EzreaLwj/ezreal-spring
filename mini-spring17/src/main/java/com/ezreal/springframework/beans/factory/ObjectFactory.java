package com.ezreal.springframework.beans.factory;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/10/17
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
