package com.ezreal.springframework.context;

import com.ezreal.springframework.beans.ListableBeanFactory;

/**
 * 上下文接口
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher {

}
