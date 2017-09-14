package com.mix;

import mix.MixRSA;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by tingkl on 2017/9/14.
 */
public class TestRSA {

    @Test
    public void test() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("/Users/tingkl/Desktop/java/4bai/nodeJava/private.key"));
        String a = MixRSA.encrypt(properties.getProperty("public"), "===\\定国343");
        System.out.println(a);
        String b = MixRSA.decrypt(properties.getProperty("private"), a);
        System.out.println(b);
    }

    @Test
    public void test2() throws Exception {
        String a = MixRSA.encrypt("===\\定国343");
        System.out.println(a);
        String b = MixRSA.decrypt(a);
        System.out.println(b);
    }
}
