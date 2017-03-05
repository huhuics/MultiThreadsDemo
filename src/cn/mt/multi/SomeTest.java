/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一些测试
 * @author HuHui
 * @version $Id: SomeTest.java, v 0.1 2017年3月5日 下午1:15:11 HuHui Exp $
 */
public class SomeTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<String, String>();

        map.put("1", "2");

        System.out.println(3 & 4);

    }

}
