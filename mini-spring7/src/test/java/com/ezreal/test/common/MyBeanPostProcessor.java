package com.ezreal.test.common;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.config.BeanPostProcessor;
import com.ezreal.test.bean.UserService;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
