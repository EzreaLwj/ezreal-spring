package com.ezreal.test;

import com.ezreal.springframework.context.support.ClassPathXmlApplicationContext;
import com.ezreal.test.bean.UserService;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public class ApiTest {

    @Test
    public void test_factory_bean() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutDownHook();

        // 2. 调用代理方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}
