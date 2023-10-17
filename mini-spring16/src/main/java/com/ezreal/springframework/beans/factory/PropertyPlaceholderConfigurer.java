package com.ezreal.springframework.beans.factory;


import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.PropertyValue;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.beans.ConfigurableListableBeanFactory;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.ezreal.springframework.core.io.DefaultResourceLoader;
import com.ezreal.springframework.core.io.Resource;
import com.ezreal.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Properties;

/**
 * 修改 @Value("${com.ezreal}")
 *
 *
 * 解析${com.ezreal}
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private final String DEFAULT_BEAN_VALUE_PREFIX = "${";

    private final String DEFAULT_BEAN_VALUE_SUFFIX = "}";

    /**
     * 指定配置文件
     */
    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

        try {

            // 读取配置文件
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            // 获取所有 bean 的定义
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

            for (String beanDefinitionName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();


                for (PropertyValue propertyValue : propertyValues.getProperValues()) {
                    Object value = propertyValue.getValue();

                    if (value instanceof String) {
                        String strValue = (String) value;
                        int startIndex = strValue.indexOf(DEFAULT_BEAN_VALUE_PREFIX);
                        int endIndex = strValue.indexOf(DEFAULT_BEAN_VALUE_SUFFIX);

                        // 如果字符串是 "${}"  @Value("${}") 要获取里面的值
                        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                            continue;
                        }

                        // 获取 key
                        String propKey = strValue.substring(startIndex + 2, endIndex);
                        // 从文件读取 以 propKey 为 key 的值
                        String propValue = properties.getProperty(propKey);
                        PropertyValue newPropertyValue = new PropertyValue();
                        newPropertyValue.setName(propertyValue.getName());
                        newPropertyValue.setValue(propValue);
                        propertyValues.addPropertyValue(newPropertyValue);
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
