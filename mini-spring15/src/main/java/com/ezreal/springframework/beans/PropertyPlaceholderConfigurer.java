package com.ezreal.springframework.beans;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.ezreal.springframework.util.StringValueResolver;

/**
 * @author Ezreal
 * @Date 2023/10/9
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableBeanFactory) throws BeansException {

    }
}
