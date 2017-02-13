/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.thread.pool5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池.多个线程分别执行不同的任务
 * @author HuHui
 * @version $Id: App.java, v 0.1 2017年2月8日 下午10:47:38 HuHui Exp $
 */

public class App {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            threadPool.submit(new Process(i));
        }

        threadPool.shutdown();

        threadPool.awaitTermination(1, TimeUnit.SECONDS);

        System.out.println("所有任务已执行完");

    }
}

class Process implements Runnable {

    private int id;

    public Process(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("id: " + id + " starting");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("id: " + id + " end");
    }

}
