/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author HuHui
 * @version $Id: CompletionServiceDemo.java, v 0.1 2017年3月5日 下午5:59:40 HuHui Exp $
 */
public class CompletionServiceDemo {

    private final Random                    random            = new Random();

    private final ExecutorService           threadPool        = Executors.newFixedThreadPool(5);

    private final CompletionService<String> completionService = new ExecutorCompletionService<String>(threadPool);

    /**
     * @param args
     */
    public static void main(String[] args) {
        CompletionServiceDemo demo = new CompletionServiceDemo();
        demo.test();
    }

    public void test() {
        for (int i = 0; i < 10; i++) {
            completionService.submit(new Runner());
        }

        try {
            for (int t = 0; t < 10; t++) {
                Future<String> f = completionService.take();
                String ret = f.get();
                System.out.println(ret + " 完成运行");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException("获取future结果错误", e);
        }

    }

    private class Runner implements Callable<String> {

        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();

            int sTime = random.nextInt(1000);

            Thread.sleep(sTime);

            return name;
        }

    }

}
