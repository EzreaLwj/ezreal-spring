package com.ezreal.test;

import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.ezreal.test.bean.UserService;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        // 1.注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        defaultListableBeanFactory.registerBeanDefinition("userService", beanDefinition);

        // 2.第一次获取bean
        UserService service = (UserService) defaultListableBeanFactory.getBean("userService","ezreal");
        service.queryUserInfo();

    }
}
