package com.ezreal.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.PropertyValue;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import com.ezreal.springframework.beans.factory.*;
import com.ezreal.springframework.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 具有自动创建bean的功能
 *
 * @author Ezreal
 * @Date 2023/9/2
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 创建 bean
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义
     * @param args           参数
     * @return 对象
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {

        Object bean = null;
        try {

            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (bean != null) {
                return bean;
            }
            // bean 的实例化
            bean = createInstance(beanName, beanDefinition, args);

            // bean 的初始化
            // 依赖注入
            applyPropertyValues(beanName, bean, beanDefinition);

            // 执行初始化方法
            bean = initializeBean(beanName, bean, beanDefinition);

            registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }

        return bean;
    }

    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    public Object applyBeanPostProcessorBeforeInstantiation(Class<?> aClass, String beanName) {
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object bean = null;
                try {
                    bean = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(aClass, beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bean != null) {
                    return bean;
                }
            }
        }
        return null;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition.getDestroyMethodName()));
        }
    }

    /**
     * 执行初始化方法
     *
     * @param beanName       bean名称
     * @param bean           bean对象
     * @param beanDefinition bean定义
     * @return 结果
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        // invokeAwareMethods
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // 前置处理器
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            // 执行初始化方法
            invokeInitialMethod(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 后置处理器
        return applyBeanPostProcessorsAfterInitialization(bean, beanName);
    }

    /**
     * 执行初始化方法
     *
     * @param beanName       bean名称
     * @param wrappedBean    包装的bean
     * @param beanDefinition bean定义
     */
    private void invokeInitialMethod(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {

        if (wrappedBean instanceof InitializingBean) {
            InitializingBean bean = (InitializingBean) wrappedBean;
            bean.afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method method = wrappedBean.getClass().getMethod(initMethodName);
            method.invoke(wrappedBean);
        }
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

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }
}
