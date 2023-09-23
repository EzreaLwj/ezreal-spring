package com.ezreal.springframework.context.support;

import com.ezreal.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.ezreal.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String[] configurations = getConfigurations();
        if (configurations != null) {
            xmlBeanDefinitionReader.loadBeanDefinitions(configurations);
        }

    }

    protected abstract String[] getConfigurations();

}
