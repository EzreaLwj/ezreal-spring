package com.ezreal.test.event;

import com.ezreal.springframework.context.ApplicationEvent;
import com.ezreal.springframework.context.event.ApplicationContextEvent;

/**
 * @author Ezreal
 * @Date 2023/9/21
 */
public class CustomEvent extends ApplicationContextEvent {

    private Long id;

    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
