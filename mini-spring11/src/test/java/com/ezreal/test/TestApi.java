package com.ezreal.test;

import com.ezreal.springframework.context.support.ClassPathXmlApplicationContext;
import com.ezreal.test.event.CustomEvent;
import org.junit.Test;

public class TestApi {

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.registerShutDownHook();
        context.publishEvent(new CustomEvent(context, 1L, "你好"));
    }
}

