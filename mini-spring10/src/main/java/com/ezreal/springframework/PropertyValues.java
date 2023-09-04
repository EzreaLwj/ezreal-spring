package com.ezreal.springframework;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ezreal
 * @Date 2023/9/3
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public PropertyValue[] getProperValues() {
        return propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue getProperValue(String name) {

        for (PropertyValue propertyValue : propertyValues) {
            String propertyValueName = propertyValue.getName();
            if (propertyValueName.equals(name)) {
                return propertyValue;
            }
        }
        return null;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }

}
