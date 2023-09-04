package com.ezreal.springframework.beans.factory;

import com.ezreal.springframework.BeansException;

/**
 * @author Ezreal
 * @Date 2023/9/4
 */
public interface DisposableBean {

    void destroy() throws Exception;

}
