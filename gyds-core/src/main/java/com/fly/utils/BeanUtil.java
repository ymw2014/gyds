package com.fly.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
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
    
    /** 
     * @param class1 用于赋值的实体类  
     * @param class2 需要待赋值的实体类
     * 描述：反射实体类赋值 
     */  
    public static void reflectionAttr(Object class1,Object class2) throws Exception{  
        Class clazz1 = class1.getClass();
        Class clazz2 = class2.getClass();
		//  获取两个实体类的所有属性  
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();  
		// 遍历class1Bean，获取逐个属性值，然后遍历class2Bean查找是否有相同的属性，如有相同则赋值  
        for (Field f1 : fields1) {  
            if(f1.getName().equals("id"))  
                continue;
            //设置访问权限
            f1.setAccessible(true);
            Object value = f1.get(class1);	    
            for (Field f2 : fields2) {  
                if(f1.getName().equals(f2.getName())){ 
               		//设置访问权限
                    f2.setAccessible(true);
                    f2.set(class2,value);	         
                }  
            }  
        }    
    } 

}