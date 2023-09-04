package com.ezreal.springframework.beans.factory.config;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public class BeanReference {

    private String beanName;

    public BeanReference(String bean) {
        this.beanName = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBean(String beanName) {
        this.beanName = beanName;
    }
}
