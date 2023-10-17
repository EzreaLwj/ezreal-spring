package com.ezreal.springframework.context;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
