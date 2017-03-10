/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 一些测试
 * @author HuHui
 * @version $Id: SomeTest.java, v 0.1 2017年3月5日 下午1:15:11 HuHui Exp $
 */
public class SomeTest {

    private static final int loop = 3000000;

    /**
     * @param args
     */
    public static void main(String[] args) {
        SomeTest test = new SomeTest();
        long start = System.currentTimeMillis();
        test.test1();
        System.out.println("test1 costs " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        test.test2();
        System.out.println("test2 costs " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        test.test3();
        System.out.println("test3 costs " + (System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        test.test4();
        System.out.println("test4 costs " + (System.currentTimeMillis() - start) + " ms");
    }

    public void test1() {
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < loop; i++) {
            String key = UUID.randomUUID().toString();
            map.put(key, key);
        }
    }

    public void test2() {
        Map<String, String> map = new HashMap<String, String>(4194304);
        for (int i = 0; i < loop; i++) {
            String key = UUID.randomUUID().toString();
            map.put(key, key);
        }
    }

    public void test3() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < loop; i++) {
            String key = UUID.randomUUID().toString();
            list.add(key);
        }
    }

    public void test4() {
        List<String> list = new ArrayList<String>(4194304);
        for (int i = 0; i < loop; i++) {
            String key = UUID.randomUUID().toString();
            list.add(key);
        }
    }
}
