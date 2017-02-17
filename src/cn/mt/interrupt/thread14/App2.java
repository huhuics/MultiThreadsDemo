/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.interrupt.thread14;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 
 * @author HuHui
 * @version $Id: App2.java, v 0.1 2017年2月17日 下午2:49:33 HuHui Exp $
 */
public class App2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Caller caller = new Caller();
            MyExceptionHandler meh = new MyExceptionHandler();
            Thread thread = new Thread(caller);
            //            thread.setUncaughtExceptionHandler(meh);
            thread.start();
            thread.join();
        } catch (Exception e) {
            System.out.println("join interrupted");
        }
        System.out.println("end");

    }
}

class Caller implements Runnable {

    @Override
    public void run() {

        throw new RuntimeException(Thread.currentThread().getName() + "线程抛出一个运行时异常");
    }
}

class MyExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t.getName() + ":捕获到异常:" + e);
    }

}
