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
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        classPathXmlApplicationContext.registerShutDownHook();

        // 2. 获取Bean对象调用方法
        UserService userService = classPathXmlApplicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }
}
