/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.interrupt.thread14;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
            ExecutorService threadPool = Executors.newCachedThreadPool();
            Caller caller = new Caller();
            Future<?> ret = threadPool.submit(caller);
           
            threadPool.shutdown();
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
