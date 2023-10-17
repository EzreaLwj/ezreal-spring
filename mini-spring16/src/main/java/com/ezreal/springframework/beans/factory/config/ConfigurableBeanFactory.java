package com.ezreal.springframework.beans.factory.config;

import com.ezreal.springframework.beans.HierarchicalBeanFactory;
import com.ezreal.springframework.util.StringValueResolver;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory ,SingletonBeanRegistry{
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();

    /**
     * 添加字符解析器
     *
     * @param stringValueResolver 字符串解析器
     */
    void addEmbeddedValueResolver(StringValueResolver stringValueResolver);

    /**
     * 解析字符串
     *
     * @param value 字符串
     * @return 结果
     */
    String resolveEmbeddedValue(String value);
}
