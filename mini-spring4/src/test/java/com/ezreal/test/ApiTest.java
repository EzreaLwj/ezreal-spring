package com.ezreal.test;

import com.ezreal.springframework.PropertyValue;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.beans.BeanReference;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.ezreal.test.bean.UserDao;
import com.ezreal.test.bean.UserService;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        BeanDefinition userDaoBeanDefinition = new BeanDefinition(UserDao.class);
        defaultListableBeanFactory.registerBeanDefinition("userDao", userDaoBeanDefinition);

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        BeanDefinition userServiceBeanDefinition = new BeanDefinition(UserService.class, propertyValues);
        defaultListableBeanFactory.registerBeanDefinition("userService", userServiceBeanDefinition);

        UserService service = (UserService) defaultListableBeanFactory.getBean("userService");
        service.queryUserInfo();
    }
}
