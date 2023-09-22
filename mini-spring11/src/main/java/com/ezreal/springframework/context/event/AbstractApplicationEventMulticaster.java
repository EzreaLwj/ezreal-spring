package com.ezreal.springframework.context.event;

import com.ezreal.springframework.beans.BeanFactory;
import com.ezreal.springframework.beans.factory.BeanFactoryAware;
import com.ezreal.springframework.context.ApplicationEvent;
import com.ezreal.springframework.context.ApplicationListener;
import com.ezreal.springframework.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    private BeanFactory beanFactory;

    private LinkedHashSet<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>)applicationListener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> applicationListener) {
        applicationListeners.remove((ApplicationListener<ApplicationEvent>)applicationListener);
    }

    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent applicationEvent) {
        LinkedList<ApplicationListener> listeners = new LinkedList<>();

        for (ApplicationListener applicationListener : applicationListeners) {
            if (supportEvent(applicationEvent, applicationListener)) {
                listeners.add(applicationListener);
            }
        }
        return listeners;
    }

    private boolean supportEvent(ApplicationEvent applicationEvent, ApplicationListener<ApplicationEvent> applicationListener) {
        Class<?> aClass = applicationListener.getClass();
        Class<?> targetClass = ClassUtil.isCglibProxyClass(aClass) ? aClass.getSuperclass() : aClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];

        // 获取泛型类的名称
        String typeName = actualTypeArgument.getTypeName();
        Class<?> typeClass = null;

        try {
           typeClass = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return typeClass.isAssignableFrom(applicationEvent.getClass());
    }

    @Override
    public abstract void multicastEvent(ApplicationEvent applicationEvent);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
