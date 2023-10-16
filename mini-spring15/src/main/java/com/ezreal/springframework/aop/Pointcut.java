package com.ezreal.springframework.aop;

/**
 * 切点接口
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
