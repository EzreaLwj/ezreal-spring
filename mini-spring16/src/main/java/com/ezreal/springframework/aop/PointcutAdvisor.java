package com.ezreal.springframework.aop;

/**
 * 切点与增强方法的聚合
 * @author Ezreal
 * @Date 2023/9/22
 */
public interface PointcutAdvisor extends Advisor{

    Pointcut getPointCut();

}
