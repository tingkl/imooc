package com.imooc.classloader;

/**
 * BaseManager的子类，此类需要实现java类的热加载功能
 * Created by tingkl on 2017/11/16.
 */
public class MyManager implements BaseManager{
    @Override
    public void logic() {
        System.out.println("我在慕课网学习如何实现一个java类的热加载案例,findClass");
    }
}
