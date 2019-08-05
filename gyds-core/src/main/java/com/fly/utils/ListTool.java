package com.fly.utils;

import org.apache.commons.collections.ListUtils;

import java.util.List;

/**'
 * @author 马凌冰
 */
public class ListTool extends ListUtils {
    /**
     * 判断list为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list){
        if(!list.isEmpty()&&null!=list){
            return false;
        }
        return true;
    }
}
