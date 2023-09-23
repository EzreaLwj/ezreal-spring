package com.ezreal.springframework.aop.aspectj;

import com.ezreal.springframework.aop.Pointcut;
import com.ezreal.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 形成一个切面拦截器，可以对应于xml中的配置
 * @author Ezreal
 * @Date 2023/9/22
 */
public class AspectJExpressionPointCutAdvisor implements PointcutAdvisor {

    // 切点
    private AspectJExpressionPointcut pointcut;

    // 切点表达式
    private String expression;

    // 增强方法
    private Advice advice;

    public AspectJExpressionPointCutAdvisor() {

    }

    public AspectJExpressionPointCutAdvisor(String expression) {
        this.expression = expression;
        pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointCut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
