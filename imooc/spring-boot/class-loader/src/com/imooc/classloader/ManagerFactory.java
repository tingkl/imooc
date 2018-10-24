package com.imooc.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载manager的工厂
 * Created by tingkl on 2017/11/16.
 */
public class ManagerFactory {
    // 记录热加载类的加载信息
    private static final Map<String, LoadInfo> loadTimeMap = new HashMap<String, LoadInfo>();
    public static final String CLASS_PATH = "/Users/tingkl/Desktop/java/imooc/spring-boot/class-loader/out/production/class-loader/";
    // 实现热加载类的全名称
    public static final String MY_MANAGER = "com.imooc.classloader.MyManager";

    public static BaseManager getManager(String className) {
        File loadFile = new File(CLASS_PATH + className.replaceAll("\\.", "/") + ".class");
        long lastModified = loadFile.lastModified();
        if (loadTimeMap.containsKey(className)) {
            System.out.println(loadTimeMap.get(className).getLoadTime() + "," + lastModified);
            if (loadTimeMap.get(className).getLoadTime() != lastModified) {
                load(className, lastModified);
            }
        } else {
            load(className, lastModified);
        }
        return loadTimeMap.get(className).getManager();
    }

    private static void load(String className, long lastModified) {
        MyClassLoader myClassLoader = new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = myClassLoader.findClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(myClassLoader, lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className, loadInfo);
    }


    // 以反射的方式创建BaseManager子类对象
    private static BaseManager newInstance(Class<?> loadClass) {
        try {
            return (BaseManager) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
