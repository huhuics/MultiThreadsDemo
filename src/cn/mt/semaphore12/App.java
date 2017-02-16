/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.semaphore12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore测试类
 * @author HuHui
 * @version $Id: App.java, v 0.1 2017年2月16日 下午8:04:07 HuHui Exp $
 */
public class App {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        final ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

        for (int i = 0; i < 20; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    connectionFactory.doConnecte();
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.DAYS);

        /* Semaphore semaphore = new Semaphore(1);
         System.out.println("0.permits: " + semaphore.availablePermits());
         semaphore.acquire();
         System.out.println("1.permits: " + semaphore.availablePermits());
         semaphore.release(2);
         System.out.println("2.permits: " + semaphore.availablePermits());
         semaphore.release();
         System.out.println("3.permits: " + semaphore.availablePermits());
         semaphore.acquire();
         System.out.println("4.permits: " + semaphore.availablePermits());
         semaphore.release(4);
         System.out.println("5.permits: " + semaphore.availablePermits());*/
    }

}
