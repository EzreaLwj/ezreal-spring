package com.ezreal.springframework.context.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.ConfigurableListableBeanFactory;
import com.ezreal.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.ezreal.springframework.beans.factory.config.BeanPostProcessor;
import com.ezreal.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.ezreal.springframework.context.ConfigurableApplicationContext;
import com.ezreal.springframework.core.io.DefaultResourceLoader;
import com.ezreal.springframework.core.io.Resource;

import java.util.Map;

/**
 * 应用上下文抽象类实现
 * 包含默认资源加载器和上下文接口
 *
 * @author Ezreal
 * @Date 2023/9/3
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {

        //1. 创建BeanFactory
        refreshBeanFactory();

        //2. 获取bean工厂
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3. 执行后工厂处理器
        invokeBeanFactoryPostProcessors(beanFactory);

        //4. 注册后处理器
        registerBeanPostProcessors(beanFactory);

        //5. 提前创建所有单例对象
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory() throws BeansException;

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeanOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor value : beanFactoryPostProcessorMap.values()) {
            value.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeanOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeanOfType(type);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

}
