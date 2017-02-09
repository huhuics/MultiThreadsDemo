/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.thread.pool5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池.多个线程执行同一个任务
 * @author HuHui
 * @version $Id: WorkerThreadPool.java, v 0.1 2017年2月9日 下午11:55:17 HuHui Exp $
 */
public class WorkerThreadPool {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Worker worker = new Worker();

        Long start = System.currentTimeMillis();

        for (int i = 0; i < 2; i++) {
            threadPool.submit(worker);
        }

        threadPool.shutdown();

        threadPool.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("costs time: " + (System.currentTimeMillis() - start));

        System.out.println("list1.size = " + worker.list1.size() + ", list2.size = " + worker.list2.size());

    }
}

class Worker implements Runnable {

    private Random       random = new Random();

    public List<Integer> list1  = new ArrayList<Integer>();

    public List<Integer> list2  = new ArrayList<Integer>();

    private Object       obj1   = new Object();

    private Object       obj2   = new Object();

    @Override
    public void run() {
        stageOne();
        stageTwo();
    }

    public void stageOne() {
        synchronized (obj1) {
            for (int i = 0; i < 1000; i++) {
                list1.add(random.nextInt());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stageTwo() {
        synchronized (obj2) {
            for (int i = 0; i < 1000; i++) {
                list2.add(random.nextInt());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
