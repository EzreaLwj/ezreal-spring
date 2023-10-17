package com.ezreal.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.ezreal.springframework.beans.factory.annotation.AutowireAnnotationBeanPostProcessor;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Set;

/**
 * @author Ezreal
 * @Date 2023/9/23
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner() {
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);

            for (BeanDefinition beanDefinition : candidateComponents) {
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

        // 把 AutowiredAnnotationBeanPostProcessor 主动添加上去
        registry.registerBeanDefinition("com.ezreal.springframework.beans.factory.annotation.AutowireAnnotationBeanPostProcessor",
                new BeanDefinition(AutowireAnnotationBeanPostProcessor.class));
    }

    /**
     * 根据 beanDefinition 获取 bean 的名称
     *
     * @param beanDefinition bean 的定义
     * @return 返回值
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        String name = beanClass.getAnnotation(Component.class).value();
        if (StrUtil.isEmpty(name)) {
            name = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return name;
    }

    /**
     * 获取该 bean 的作用域
     *
     * @param beanDefinition bean 的定义
     * @return 返回值
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }

        return StrUtil.EMPTY;
    }
}
