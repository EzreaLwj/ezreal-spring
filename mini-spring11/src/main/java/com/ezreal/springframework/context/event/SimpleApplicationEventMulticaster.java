package com.ezreal.springframework.context.event;

import com.ezreal.springframework.beans.BeanFactory;
import com.ezreal.springframework.context.ApplicationEvent;
import com.ezreal.springframework.context.ApplicationListener;

import java.util.Collection;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {
        Collection<ApplicationListener> applicationListeners = getApplicationListeners(applicationEvent);

        for (ApplicationListener listener : applicationListeners) {
            listener.onApplicationEvent(applicationEvent);
        }
    }
}
