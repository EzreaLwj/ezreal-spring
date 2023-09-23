package com.ezreal.springframework.context;

import java.util.EventListener;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理对应的事件
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);
}
