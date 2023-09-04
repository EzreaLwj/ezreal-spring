package com.ezreal.springframework.beans.factory.config;

import com.ezreal.springframework.beans.HierarchicalBeanFactory;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();
}
