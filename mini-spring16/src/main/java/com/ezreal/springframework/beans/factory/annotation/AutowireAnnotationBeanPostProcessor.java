package com.ezreal.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.beans.BeanFactory;
import com.ezreal.springframework.beans.ConfigurableListableBeanFactory;
import com.ezreal.springframework.beans.factory.BeanFactoryAware;
import com.ezreal.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.ezreal.springframework.util.ClassUtil;

import java.lang.reflect.Field;

/**
 * 这是一个 bean 的后置处理器，用于处理依赖的自动注入
 *
 * @author Ezreal
 * @Date 2023/10/9
 */
public class AutowireAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        if (ClassUtil.isCglibProxyClass(aClass)) {
            aClass = aClass.getSuperclass();
        }

        //  解析 Value 注解，提前写入到对象中
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            Value value = field.getAnnotation(Value.class);
            if (value != null) {
                String strValue = value.value();
                strValue = beanFactory.resolveEmbeddedValue(strValue);
                BeanUtil.setFieldValue(bean, field.getName(), strValue);
            }
        }

        for (Field field : fields) {
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (annotation != null) {

                String fieldName = field.getName();
                Class<?> type = field.getType();

                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if (qualifier != null) {
                    String name = qualifier.value();
                    if (StrUtil.isNotEmpty(name)) {
                        Object beanFactoryBean = beanFactory.getBean(name);
                        if (beanFactoryBean == null) {
                            beanFactoryBean = beanFactory.getBean(type);
                        }
                        BeanUtil.setFieldValue(bean, fieldName, beanFactoryBean);
                    }
                } else {
                    Object beanFactoryBean = beanFactory.getBean(type);
                    BeanUtil.setFieldValue(bean, fieldName, beanFactoryBean);
                }
            }
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> aClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
