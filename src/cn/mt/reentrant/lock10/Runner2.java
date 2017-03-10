/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.reentrant.lock10;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author HuHui
 * @version $Id: Runner2.java, v 0.1 2017年3月9日 下午2:36:14 HuHui Exp $
 */
public class Runner2 implements Runnable {

    private final Lock lock = new ReentrantLock();

    /**
     * @param args
     */
    public static void main(String[] args) {
        Runner2 runner2 = new Runner2();
        Thread thread1 = new Thread(runner2);
        Thread thread2 = new Thread(runner2);
        thread1.start();
        thread2.start();
    }

    public void test1() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + "获取到test1锁");

                Thread.sleep(5000);
            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "没获取到test1锁");
        }
    }

    public void test2() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + "获取到test2锁");

                Thread.sleep(3000);
            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "没获取到test2锁");
        }
    }

    @Override
    public void run() {
        test1();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }

        test2();
    }

}
