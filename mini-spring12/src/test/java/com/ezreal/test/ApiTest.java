package com.ezreal.test;

import com.ezreal.springframework.aop.AdvisedSupport;
import com.ezreal.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.ezreal.springframework.aop.framework.Cglib2AopProxy;
import com.ezreal.springframework.aop.framework.JdkDynamicAopProxy;
import com.ezreal.test.bean.IUserService;
import com.ezreal.test.bean.UserMethodInterceptor;
import com.ezreal.test.bean.UserService;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ezreal
 * @Date 2023/9/22
 */
public class ApiTest {

    @Test
    public void aop_test() {

        Object targetObj = new UserService();

        Proxy.newProxyInstance(ApiTest.class.getClassLoader(), new Class[]{IUserService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

    @Test
    public void test_aop1() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.ezreal.test.bean.UserService.*(..))");
        Class<UserService> userServiceClass = UserService.class;
        Method method = userServiceClass.getMethod("queryUserInfo");

        System.out.println(pointcut.matches(userServiceClass));
        System.out.println(pointcut.matches(method, userServiceClass));
    }

    @Test
    public void test_aop2() {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.ezreal.test.bean.IUserService.*(..))");
        IUserService userService = new UserService();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetClass(userService);
        advisedSupport.setMethodInterceptor(new UserMethodInterceptor());
        advisedSupport.setMethodMatcher(pointcut);

        JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(advisedSupport);

        IUserService proxy = (IUserService) jdkDynamicAopProxy.getProxy();
        System.out.println(proxy.queryUserInfo());

        Cglib2AopProxy cglib2AopProxy = new Cglib2AopProxy(advisedSupport);
        IUserService cglib2AopProxyProxy = (IUserService) cglib2AopProxy.getProxy();
        System.out.println(cglib2AopProxyProxy.register("花花"));


    }
}
