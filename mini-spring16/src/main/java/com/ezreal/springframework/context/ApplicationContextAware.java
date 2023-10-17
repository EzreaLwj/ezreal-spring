package com.ezreal.springframework.context;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.factory.Aware;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
