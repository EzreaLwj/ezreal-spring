package com.ezreal.springframework.context.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.ConfigurableListableBeanFactory;
import com.ezreal.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 创建bean工厂和加载资源
 *
 * @author Ezreal
 * @Date 2023/9/3
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    protected abstract void loadDefinitions(DefaultListableBeanFactory beanFactory);

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() throws BeansException {
        return beanFactory;
    }
}
