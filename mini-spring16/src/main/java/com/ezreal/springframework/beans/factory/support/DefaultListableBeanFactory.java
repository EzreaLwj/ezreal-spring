package com.ezreal.springframework.beans.factory.support;

import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.beans.ConfigurableListableBeanFactory;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;

import java.util.*;

/**
 * @author Ezreal
 * @Date 2023/9/2
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new BeansException("No bean named " + name + " beanDefinition");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    @Override
    public <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException {
        HashMap<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        Set<Map.Entry<String, BeanDefinition>> entries = beanDefinitionMap.entrySet();
        for (Map.Entry<String, BeanDefinition> entry : entries) {
            Class<?> beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }

        if (beanNames.size() == 1) {
            return getBean(beanNames.get(0), requiredType);
        }
        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
}
