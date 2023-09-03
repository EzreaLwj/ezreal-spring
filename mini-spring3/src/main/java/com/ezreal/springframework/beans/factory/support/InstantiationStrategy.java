package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public interface InstantiationStrategy {

    /**
     * 实例化一个Bean
     *
     * @param beanDefinition bean的定义
     * @param beanName       bean名称
     * @param constructor    构造器
     * @param args           参数
     * @return 返回创建对象
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object... args);

}
