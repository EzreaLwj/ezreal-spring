package com.ezreal.springframework.beans.factory;

/**
 * FactoryBean 接口
 *
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getClassType();

    Boolean isSingleton();

    public Class<?> getObjectType();

}
