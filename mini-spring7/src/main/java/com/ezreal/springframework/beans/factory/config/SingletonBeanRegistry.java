package com.ezreal.springframework.beans.factory.config;

/**
 * 获取单例bean的接口
 * @author Ezreal
 * @Date 2023/9/2
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String name);
}
