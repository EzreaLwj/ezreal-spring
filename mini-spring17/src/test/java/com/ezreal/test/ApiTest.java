package com.ezreal.test;

import com.ezreal.springframework.context.support.ClassPathXmlApplicationContext;
import com.ezreal.test.bean.Husband;
import com.ezreal.test.bean.Wife;
import org.junit.Test;

/**
 * @author Ezreal
 * @Date 2023/10/17
 */
public class ApiTest {

    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公：" + wife.queryHusband());
    }
}
