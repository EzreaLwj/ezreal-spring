package com.ezreal.springframework.context.event;

import com.ezreal.springframework.context.ApplicationEvent;
import com.ezreal.springframework.context.ApplicationListener;

/**
 * 事件广播器
 * @author Ezreal
 * @Date 2023/9/21
 */
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> applicationListener);

    void removeApplicationListener(ApplicationListener<?> applicationListener);

    void multicastEvent(ApplicationEvent applicationEvent);

}
