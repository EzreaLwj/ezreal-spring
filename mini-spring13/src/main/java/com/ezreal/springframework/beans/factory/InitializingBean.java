package com.ezreal.springframework.beans.factory;

import com.ezreal.springframework.BeansException;

/**
 * 初始化方法类
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface InitializingBean {

    void afterPropertiesSet() throws BeansException;

}
