package com.test;

import com.ezreal.springframework.context.support.ClassPathXmlApplicationContext;
import com.test.bean.IUserService;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/9/23
 */
public class ApiTest {

    private final String DEFAULT_BEAN_VALUE_PREFIX = "${";

    private final String DEFAULT_BEAN_VALUE_SUFFIX = "}";

    @Test
    public void test() {
        String value = "${user.token}";
        int startIndex = value.indexOf(DEFAULT_BEAN_VALUE_PREFIX);
        int endIndex = value.indexOf(DEFAULT_BEAN_VALUE_SUFFIX);

        String key = value.substring(startIndex + 2, endIndex);
        System.out.println(key);
    }

    @Test
    public void test_property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService);
    }

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = context.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}
