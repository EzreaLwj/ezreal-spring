package com.ezreal.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.DisposableBean;

import java.lang.reflect.Method;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public class DisposableBeanAdapter implements DisposableBean {

    private Object bean;

    private String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter() {
    }

    public DisposableBeanAdapter(Object bean, String beanName, String destroyMethodName) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (null == method) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            method.invoke(bean);
        }
    }
}
