package com.ezreal.springframework.context;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException bean异常
     */
    void refresh() throws BeansException;

    void registerShutDownHook() ;

    void close();

}
