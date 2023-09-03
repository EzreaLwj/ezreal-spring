package com.ezreal.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.PropertyValue;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.beans.BeanReference;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 具有自动创建bean的功能
 *
 * @author Ezreal
 * @Date 2023/9/2
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 创建 bean
     *
     * @param name           bean名称
     * @param beanDefinition bean定义
     * @param args           参数
     * @return 对象
     * @throws BeansException
     */
    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object... args) throws BeansException {

        Object bean = null;
        try {

            bean = createInstance(name, beanDefinition, args);

            // 依赖注入
            applyPropertyValues(name, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(name, bean);
        return bean;

    }

    /**
     * 封装创建bean的过程
     *
     * @param name           bean名称
     * @param beanDefinition bean定义
     * @param args           bean参数
     * @return 结果对象
     */
    protected Object createInstance(String name, BeanDefinition beanDefinition, Object... args) {

        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?> constructor = null;

        Constructor<?>[] constructors = beanClass.getConstructors();
        for (Constructor<?> ctr : constructors) {
            if (args != null && ctr.getParameterTypes().length == args.length) {
                constructor = ctr;
                break;
            }
        }

        return getInstantiationStrategy().instantiate(beanDefinition, name, constructor, args);
    }

    public void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();

            PropertyValue[] values = propertyValues.getProperValues();
            for (PropertyValue value : values) {
                String name = value.getName();
                Object obj = value.getValue();

                if (obj instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) obj;
                    obj = getBean(beanReference.getBeanName());
                }

                BeanUtil.setFieldValue(bean, name, obj);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property value: " + beanName, e);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
