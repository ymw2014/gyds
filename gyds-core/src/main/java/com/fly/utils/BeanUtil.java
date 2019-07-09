package com.fly.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 当把Person类作为BeanUtilTest的内部类时，程序出错<br>
 * java.lang.NoSuchMethodException: Property '**' has no setter method<br>
 * 本质：内部类 和 单独文件中的类的区别 <br>
 * BeanUtils.populate方法的限制：<br>
 * The class must be public, and provide a public constructor that accepts no arguments. <br>
 * This allows tools and applications to dynamically create new instances of your bean, <br>
 * without necessarily knowing what Java class name will be used ahead of time
 */
public class BeanUtil {

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }        
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}