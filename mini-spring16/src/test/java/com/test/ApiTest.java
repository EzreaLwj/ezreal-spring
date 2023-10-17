package com.test;

import com.ezreal.springframework.context.support.ClassPathXmlApplicationContext;
import com.test.bean.IUserService;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/10/16
 */
public class ApiTest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}
