/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.countdownlatch6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch测试用例
 * @author HuHui
 * @version $Id: App.java, v 0.1 2017年2月10日 上午11:17:59 HuHui Exp $
 */
public class App {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(3);

        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Work work = new Work(latch);
            pool.submit(work);
        }

        pool.shutdown();

        latch.await();

        System.out.println("任务全部完成");

    }

}

class Work implements Runnable {

    private CountDownLatch latch;

    public Work(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

}
