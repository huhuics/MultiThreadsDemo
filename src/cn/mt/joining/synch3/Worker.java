/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.joining.synch3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试joining和synchronized
 * @author HuHui
 * @version $Id: Worker.java, v 0.1 2017年2月8日 下午2:56:17 HuHui Exp $
 */
public class Worker {

    private AtomicInteger count = new AtomicInteger(0);

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.doWork();
    }

    public void increment(String threadName) throws InterruptedException {
        count.incrementAndGet();
        System.out.println("thread in progress: " + threadName + " and count is: " + count);
        Thread.sleep(50);
    }

    public void doWork() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        increment(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        increment(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Count is: " + count);

    }
}
