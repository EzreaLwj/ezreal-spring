package com.ezreal.springframework.util;

public class ClassUtil {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtil.class.getClassLoader();
        }
        return cl;
    }

    /**
     * 判断改类是不是一个 cglib 代理对象
     *
     * @param clazz 类对象
     * @return 结果
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * 通过类名判断是不是 cglib 的代理对象
     *
     * @param className 类名称
     * @return 结果
     */
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }
}
