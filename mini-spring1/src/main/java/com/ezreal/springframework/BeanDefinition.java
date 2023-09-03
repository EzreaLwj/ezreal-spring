package com.ezreal.springframework;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public class BeanDefinition {
    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
