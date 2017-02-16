/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.callable13;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 测试Callable抛异常场景
 * @author HuHui
 * @version $Id: App1.java, v 0.1 2017年2月16日 下午9:56:32 HuHui Exp $
 */
public class App1 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        final Random random = new Random();

        Future future = executor.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                int time = random.nextInt(4000);
                if (time > 2000) {
                    throw new RuntimeException("休眠时间过长, time=" + time);
                }

                Thread.sleep(time);

                return time;
            }
        });

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Object ret = future.get();
            System.out.println(ret);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
