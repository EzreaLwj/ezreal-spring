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
    public void test_xml() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutDownHook();

        // 2. 获取Bean对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
        System.out.println("ApplicationContextAware："+userService.getApplicationContext());
        System.out.println("BeanFactoryAware："+userService.getBeanFactory());
    }
}
