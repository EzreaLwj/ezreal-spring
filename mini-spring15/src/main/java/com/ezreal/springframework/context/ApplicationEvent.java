package com.ezreal.springframework.context;

import java.util.EventObject;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
