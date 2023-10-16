package com.ezreal.springframework.context.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.config.BeanPostProcessor;
import com.ezreal.springframework.context.ApplicationContext;
import com.ezreal.springframework.context.ApplicationContextAware;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
