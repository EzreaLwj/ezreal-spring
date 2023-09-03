package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.core.io.Resource;
import com.ezreal.springframework.core.io.ResourceLoader;

/**
 * 读取资源定义的接口,聚合了资源加载器和bean信息注入类
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface BeanDefinitionReader {

    ResourceLoader getResourceLoader();

    BeanDefinitionRegistry getRegistry();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

}
