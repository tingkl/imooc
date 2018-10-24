package com.imooc.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by tingkl on 2017/11/16.
 * 自定义java类加载器来实现java类的热加载
 */

public class MyClassLoader extends ClassLoader{
    // 要加载的java类的classpath路径
    private String classpath;

    public MyClassLoader(String classpath) {
        super(ClassLoader.getSystemClassLoader());
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // return super.findClass(name);
        System.out.println("findClass");
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    // 加载class文件中的内容
    private byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "//");
            FileInputStream fis = new FileInputStream(new File(classpath + name + ".class"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while((b = fis.read()) != -1) {
                baos.write(b);
            }
            fis.close();
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
