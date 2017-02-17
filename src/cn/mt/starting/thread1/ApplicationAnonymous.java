/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.starting.thread1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 匿名类实现多线程
 * @author HuHui
 * @version $Id: ApplicationAnonymous.java, v 0.1 2017年2月7日 下午7:53:32 HuHui Exp $
 */
public class ApplicationAnonymous {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationAnonymous app = new ApplicationAnonymous();
        app.testInterrupt();
        System.out.println("end");
    }

    public void testInterrupt() {

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Future<Integer> future = threadPool.submit(new Callable<Integer>() {

            @Override
            public Integer call() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Hello: " + i + " Thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("捕获InterruptedException");
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("线程中断", e);
                    }
                }
                return 1999;
            }

        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future.cancel(true);

        threadPool.shutdown();

    }

}
