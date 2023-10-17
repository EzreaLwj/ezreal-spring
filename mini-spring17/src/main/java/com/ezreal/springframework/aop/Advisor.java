package com.ezreal.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface Advisor {

    Advice getAdvice();

}
