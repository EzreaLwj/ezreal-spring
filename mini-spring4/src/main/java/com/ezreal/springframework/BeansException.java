package com.ezreal.springframework;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public class BeansException extends RuntimeException{

    public BeansException() {
    }

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
