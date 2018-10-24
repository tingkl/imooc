package com.imooc.classloader;

/**
 * 后台启动一条线程不断刷新重新加载实现了热加载的类
 * Created by tingkl on 2017/11/16.
 */
public class MsgHandler implements Runnable {
    @Override
    public void run() {
        while(true) {
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
