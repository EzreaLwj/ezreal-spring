package com.ezreal.springframework.aop;

/**
 * 类过滤器
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);

}