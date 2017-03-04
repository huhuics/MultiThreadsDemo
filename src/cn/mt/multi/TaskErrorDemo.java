/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author HuHui
 * @version $Id: TaskErrorDemo.java, v 0.1 2017年3月4日 下午4:47:15 HuHui Exp $
 */
public class TaskErrorDemo {

    private final Random random = new Random();

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        TaskErrorDemo demo = new TaskErrorDemo();
        demo.testError();
    }

    public void testError() throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Runner> tasks = new ArrayList<Runner>();

        for (int i = 0; i < 20; i++) {
            tasks.add(new Runner());
        }

        List<Future<Boolean>> rets = null;
        System.out.println("调用invokeAll前");
        try {
            rets = threadPool.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("调用invokeAll后");

        int suc = 0, fai = 0;
        for (Future<Boolean> ret : rets) {
            try {
                Boolean getFuture = ret.get();
                if (getFuture) {
                    ++suc;
                } else {
                    ++fai;
                }
            } catch (ExecutionException e) {
                System.out.println("捕获future一个异常");
                ++fai;
            }
        }

        System.out.println("统计完成");

        threadPool.shutdown();
        threadPool.awaitTermination(5, TimeUnit.HOURS); //invokeAll会等所有任务执行完成再返回结果

        System.out.println("suc=" + suc + ",fai=" + fai);

    }

    private class Runner implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {

            int ranInt = random.nextInt(20);

            if (ranInt >= 10) {
                System.out.println("随机数过大,任务抛异常");
                throw new RuntimeException("随机数过大,抛出一个异常");
            } else {
                Thread.sleep(1000);
                return true;
            }

        }
    }

}
