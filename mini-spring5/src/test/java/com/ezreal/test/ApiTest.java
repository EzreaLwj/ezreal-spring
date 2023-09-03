package com.ezreal.test;

import cn.hutool.core.io.IoUtil;
import com.ezreal.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.ezreal.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.ezreal.springframework.core.io.DefaultResourceLoader;
import com.ezreal.springframework.core.io.Resource;
import com.ezreal.test.bean.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public class ApiTest {

    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);

        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        UserService userService = (UserService) defaultListableBeanFactory.getBean("userService");
        String result = userService.queryUserInfo();
        System.out.println("测试结果为：" + result);
    }


}
