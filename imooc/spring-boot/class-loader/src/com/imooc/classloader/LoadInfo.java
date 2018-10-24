package com.imooc.classloader;

/**
 * 封装加载类的信息
 * Created by tingkl on 2017/11/16.
 */
public class LoadInfo {
    // 自定义的类加载
    private MyClassLoader myClassLoader;

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    private long loadTime;

    public BaseManager getManager() {
        return manager;
    }

    public void setManager(BaseManager manager) {
        this.manager = manager;
    }

    private BaseManager manager;

    public LoadInfo(MyClassLoader myClassLoader, long loadTime) {
        this.myClassLoader = myClassLoader;
        this.loadTime = loadTime;
    }
}
