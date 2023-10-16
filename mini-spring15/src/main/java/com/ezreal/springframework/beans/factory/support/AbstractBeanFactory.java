package com.ezreal.springframework.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.FactoryBean;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.config.BeanPostProcessor;
import com.ezreal.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.ezreal.springframework.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象bean工厂
 *
 * @author Ezreal
 * @Date 2023/9/2
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport  implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private ClassLoader beanClassLoader = ClassUtil.getClassLoader();

    /**
     * 字符串解析器
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {

        Object bean = getSingleton(name);
        if (bean != null) {

            // 判断该bean是不是factoryBean
            return (T) getObjectForBeanInstance(bean, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    private Object getObjectForBeanInstance(Object bean, String name) {

        if (!(bean instanceof FactoryBean)) {
            return bean;
        }

        Object object = getCachedObjectForFactoryBean(name);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
            object = getObjectFromFactoryBean(factoryBean, name);
        }

        return object;
    }

    /**
     * 获取bean定义
     *
     * @param name bean的名称
     * @return bean定义
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

    /**
     * 创建一个bean
     *
     * @param name           bean名称
     * @param beanDefinition bean定义
     * @return 结果
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object... args) throws BeansException;

    @Override
    protected void addSingleton(String name, Object singletonObject) {
        super.addSingleton(name, singletonObject);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }


    @Override
    public void addEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        embeddedValueResolvers.add(stringValueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver embeddedValueResolver : embeddedValueResolvers) {
            result = embeddedValueResolver.resolveStringValue(result);
        }
        return result;
    }
}
