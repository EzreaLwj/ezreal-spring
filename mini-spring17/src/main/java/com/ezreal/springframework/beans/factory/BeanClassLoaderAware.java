package com.ezreal.springframework.beans.factory;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);

}
