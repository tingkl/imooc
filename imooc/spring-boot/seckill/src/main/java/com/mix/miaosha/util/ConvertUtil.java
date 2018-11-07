package com.mix.miaosha.util;

import com.alibaba.fastjson.JSON;

public class ConvertUtil {
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String)value;
        }
//        System.out.println("beanToString:" + JSON.toJSONString(value));
        return JSON.toJSONString(value);
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return(T)Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return(T)Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T)str;
        }
//        System.out.println("stringToBean:" + str);
        return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }
}
