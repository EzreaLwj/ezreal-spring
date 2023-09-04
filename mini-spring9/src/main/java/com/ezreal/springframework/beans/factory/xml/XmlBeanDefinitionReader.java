package com.ezreal.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.ezreal.springframework.BeansException;
import com.ezreal.springframework.PropertyValue;
import com.ezreal.springframework.PropertyValues;
import com.ezreal.springframework.beans.factory.config.BeanDefinition;
import com.ezreal.springframework.beans.factory.config.BeanReference;
import com.ezreal.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.ezreal.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.ezreal.springframework.core.io.Resource;
import com.ezreal.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * 读取xml中bean的定义信息
 *
 * @author Ezreal
 * @Date 2023/9/3
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream inputStream = resource.getInputStream();
            doLoadBeanDefinitions(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取xml信息
     *
     * @param inputStream 输入流
     */
    private void doLoadBeanDefinitions(InputStream inputStream) {
        try {
            Document doc = XmlUtil.readXML(inputStream);
            Element root = doc.getDocumentElement();

            NodeList childNodes = root.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);

                if (!(item instanceof Element)) {
                    continue;
                }
                if (!"bean".equals(item.getNodeName())) {
                    continue;
                }

                // 获取bean的属性
                Element bean = (Element) item;
                String id = bean.getAttribute("id");
                String name = bean.getAttribute("name");
                String className = bean.getAttribute("class");
                String initMethodName = bean.getAttribute("init-method");
                String destroyMethodName = bean.getAttribute("destroy-method");

                Class<?> aClass = Class.forName(className);

                String beanName = StrUtil.isNotEmpty(id) ? id : name;
                if (beanName.isEmpty()) {
                    beanName = StrUtil.lowerFirst(aClass.getSimpleName());
                }

                PropertyValues propertyValues = new PropertyValues();
                BeanDefinition beanDefinition = new BeanDefinition(aClass, propertyValues);
                beanDefinition.setInitMethodName(initMethodName);
                beanDefinition.setDestroyMethodName(destroyMethodName);

                NodeList properties = bean.getChildNodes();
                for (int j = 0; j < properties.getLength(); j++) {
                    Node node = properties.item(j);
                    if (!(node instanceof Element)) {
                        continue;
                    }

                    if (!"property".equals(node.getNodeName())) {
                        continue;
                    }
                    Element property = (Element) node;
                    String propertyName = property.getAttribute("name");
                    String value = property.getAttribute("value");
                    String ref = property.getAttribute("ref");

                    Object valueObj = StrUtil.isNotEmpty(ref) ? new BeanReference(ref) : value;

                    propertyValues.addPropertyValue(new PropertyValue(propertyName, valueObj));

                }

                getRegistry().registerBeanDefinition(beanName, beanDefinition);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
