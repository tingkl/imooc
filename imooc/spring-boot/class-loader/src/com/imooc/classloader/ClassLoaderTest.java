package com.imooc.classloader;

/**
 * 测试java类的热加载
 * Created by tingkl on 2017/11/16.
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        new Thread(new MsgHandler()).start();
    }
}
