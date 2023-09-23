package com.test;


import com.ezreal.springframework.aop.AdvisedSupport;
import com.ezreal.springframework.aop.ClassFilter;
import com.ezreal.springframework.aop.MethodBeforeAdvice;
import com.ezreal.springframework.aop.aspectj.AspectJExpressionPointCutAdvisor;
import com.ezreal.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.ezreal.springframework.aop.framework.ProxyFactory;
import com.ezreal.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.ezreal.springframework.context.support.ClassPathXmlApplicationContext;
import com.test.bean.IUserService;
import com.test.bean.UserService;
import com.test.bean.UserServiceBeforeAdvice;
import com.test.bean.UserServiceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;

public class ApiTest {

    AdvisedSupport advisedSupport;

    @Before
    public void init() {
        IUserService userService = new UserService();

        advisedSupport = new AdvisedSupport();
        // 目标对象
        advisedSupport.setTargetClass(userService);
        // 方法拦截器
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        // 切点
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.test.bean.IUserService.*(..))"));
    }

    @Test
    public void testProxyFactory() {
        advisedSupport.setProxyTargetClass(true); // false/true，JDK动态代理、CGlib动态代理
        ProxyFactory proxyFactory = new ProxyFactory(advisedSupport);
        IUserService userService = (IUserService) proxyFactory.createProxy().getProxy();
        userService.queryUserInfo();
        System.out.println(userService.getClass());
    }

    @Test
    public void test_beforeAdvice() {
        MethodBeforeAdvice methodBeforeAdvice = new UserServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor methodBeforeAdviceInterceptor = new MethodBeforeAdviceInterceptor(methodBeforeAdvice);
        advisedSupport.setMethodInterceptor(methodBeforeAdviceInterceptor);

        IUserService userService = (IUserService) new ProxyFactory(advisedSupport).getProxy();
        userService.queryUserInfo();
        System.out.println(userService.getClass());

    }

    @Test
    public void test_advisor() {
        // 目标对象
        IUserService userService = new UserService();

        AspectJExpressionPointCutAdvisor advisor = new AspectJExpressionPointCutAdvisor("execution(* com.test.bean.IUserService.*(..))");
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new UserServiceBeforeAdvice()));

        ClassFilter classFilter = advisor.getPointCut().getClassFilter();

        if (classFilter.matches(userService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointCut().getMethodMatcher());
            advisedSupport.setTargetClass(userService);

            IUserService userService1 = (IUserService) new ProxyFactory(advisedSupport).getProxy();
            System.out.println(userService1.queryUserInfo());
        }
    }

    @Test
    public void test_aop() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Object o = applicationContext.getBean("userService", IUserService.class);
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }


}
