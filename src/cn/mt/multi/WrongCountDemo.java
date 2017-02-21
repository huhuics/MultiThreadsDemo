/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无同步的并发计数
 * @author HuHui
 * @version $Id: WrongCountDemo.java, v 0.1 2017年2月21日 下午4:39:39 HuHui Exp $
 */
public class WrongCountDemo {

    private static final int COUNTS = 10000000;

    private AtomicInteger    sum    = new AtomicInteger(0); //使用Atomic变量可以解决无同步的并发计数

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        WrongCountDemo demo = new WrongCountDemo();
        Runner runner = demo.getInstance();

        Thread t1 = new Thread(runner);
        Thread t2 = new Thread(runner);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int actualValue = demo.getSum();
        int expectValue = demo.getCounts() * 2;

        if (actualValue != expectValue) {
            System.out.println("计算结果与期望不同, actualValue=" + actualValue + ", expectValue=" + expectValue);
        } else {
            System.out.println("计算结果与期望相同, actualValue=" + actualValue + ", expectValue=" + expectValue);
        }

    }

    public int getCounts() {
        return COUNTS;
    }

    public Runner getInstance() {
        return new Runner();
    }

    public int getSum() {
        return sum.get();
    }

    private class Runner implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < COUNTS; i++) {
                sum.incrementAndGet();
            }
        }

    }

}
