package com.ezreal.springframework.core.io;

/**
 * 资源加载器
 * @author Ezreal
 * @Date 2023/9/3
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);

}
