/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.interrupt.thread14;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author HuHui
 * @version $Id: App.java, v 0.1 2017年2月17日 下午2:25:36 HuHui Exp $
 */
public class App {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(8);

        List<Callable<Integer>> taskList = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 10; i++) {
            taskList.add(new Callable<Integer>() {
                @Override
                public Integer call() {
                    System.out.println(Thread.currentThread().getName() + "启动");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + "线程休眠被中断");
                    }
                    if (Thread.interrupted()) {
                        System.out.println(Thread.currentThread().getName() + "线程被中断");
                    }
                    return (int) Thread.currentThread().getId();
                }
            });
        }

        try {
            List<Future<Integer>> rets = threadPool.invokeAll(taskList);
            threadPool.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("threadPool.invokeAll interrupted");
        }

    }
}
