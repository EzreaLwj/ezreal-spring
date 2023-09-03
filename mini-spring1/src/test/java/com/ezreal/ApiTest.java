package com.ezreal;

import com.ezreal.bean.UserService;
import com.ezreal.springframework.BeanDefinition;
import com.ezreal.springframework.BeanFactory;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public class ApiTest {

    @Test
    public void test_bean() {

        // 1. 创建bean工厂
        BeanFactory beanFactory = new BeanFactory();

        // 2. 创建测试对象
        UserService userService = new UserService();
        BeanDefinition beanDefinition = new BeanDefinition(userService);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3. 获取对应的bean对象
        UserService service = (UserService) beanFactory.getBean("userService");
        service.queryUserInfo();
    }
}
