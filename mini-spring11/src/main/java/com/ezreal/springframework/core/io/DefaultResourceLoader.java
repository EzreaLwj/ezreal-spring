package com.ezreal.springframework.core.io;

/**
 * 获取对应资源加载方式
 *
 * @author Ezreal
 * @Date 2023/9/3
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        return null;
    }
}
