package com.ezreal.springframework.context.event;

import com.ezreal.springframework.context.ApplicationEvent;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public class ContextRefreshedEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
