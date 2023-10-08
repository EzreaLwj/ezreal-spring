package com.ezreal.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Ezreal
 * @Date 2023/9/23
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        LinkedHashSet<BeanDefinition> set = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> aClass : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(aClass);
            set.add(beanDefinition);
        }
        return set;
    }
}
