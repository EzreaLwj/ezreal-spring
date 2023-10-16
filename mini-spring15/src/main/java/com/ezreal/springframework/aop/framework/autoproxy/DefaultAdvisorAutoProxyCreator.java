package com.ezreal.springframework.aop.framework.autoproxy;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.aop.AdvisedSupport;
import com.ezreal.springframework.aop.Advisor;
import com.ezreal.springframework.aop.ClassFilter;
import com.ezreal.springframework.aop.Pointcut;
import com.ezreal.springframework.aop.aspectj.AspectJExpressionPointCutAdvisor;
import com.ezreal.springframework.aop.framework.ProxyFactory;
import com.ezreal.springframework.beans.BeanFactory;
import com.ezreal.springframework.beans.factory.BeanFactoryAware;
import com.ezreal.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.ezreal.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Map;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    public DefaultAdvisorAutoProxyCreator() {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> aClass, String beanName) throws BeansException {
        if (isInfrastructureClass(aClass)) {
            return null;
        }

        Map<String, AspectJExpressionPointCutAdvisor> pointcutAdvisorMap = beanFactory.getBeanOfType(AspectJExpressionPointCutAdvisor.class);

        for (Map.Entry<String, AspectJExpressionPointCutAdvisor> entry : pointcutAdvisorMap.entrySet()) {
            AspectJExpressionPointCutAdvisor pointcutAdvisor = entry.getValue();
            ClassFilter classFilter = pointcutAdvisor.getPointCut().getClassFilter();
            if (!classFilter.matches(aClass)) {
                continue;
            }

            Object bean = null;
            try {
                bean = aClass.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            AdvisedSupport advisedSupport = new AdvisedSupport();
            advisedSupport.setMethodMatcher(pointcutAdvisor.getPointCut().getMethodMatcher());
            advisedSupport.setTargetClass(bean);
            advisedSupport.setMethodInterceptor((MethodInterceptor) pointcutAdvisor.getAdvice());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    private boolean isInfrastructureClass(Class<?> clazz) {
        return Advice.class.isAssignableFrom(clazz)
                || Pointcut.class.isAssignableFrom(clazz)
                || Advisor.class.isAssignableFrom(clazz);
    }
}
