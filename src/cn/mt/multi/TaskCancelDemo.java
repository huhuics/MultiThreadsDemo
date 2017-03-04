/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 测试任务取消
 * @author HuHui
 * @version $Id: TaskCancelDemo.java, v 0.1 2017年3月4日 下午4:23:55 HuHui Exp $
 */
public class TaskCancelDemo {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        TaskCancelDemo demo = new TaskCancelDemo();
        demo.testCancel();
    }

    public void testCancel() throws Exception {
        Runner runner = new Runner();
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Boolean> ret = threadPool.submit(runner);
        Thread.sleep(2000);
        ret.cancel(true);
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private class Runner implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {

            System.out.println(Thread.currentThread().getName() + "进入call方法,开始执行任务");

            Thread.sleep(5000);

            System.out.println("任务执行完毕");

            return null;
        }
    }

}
