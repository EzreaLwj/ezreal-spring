package com.ezreal.springframework.beans.factory.config;

/**
 * 存储Bean的信息
 * @author Ezreal
 * @Date 2023/9/2
 */

public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

}
