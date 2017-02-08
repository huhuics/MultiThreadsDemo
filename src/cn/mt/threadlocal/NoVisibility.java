/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.threadlocal;

/**
 * 可见性测试
 * @author HuHui
 * @version $Id: NoVisibility.java, v 0.1 2017年2月7日 下午9:03:01 HuHui Exp $
 */
public class NoVisibility {

    private static volatile boolean ready;

    private static volatile int     number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println("number=" + number);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

}
