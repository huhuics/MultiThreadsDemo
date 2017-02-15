/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.reentrant.lock10;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * @author HuHui
 * @version $Id: Runner.java, v 0.1 2017年2月15日 下午8:10:09 HuHui Exp $
 */
public class Runner {

    private int       count     = 0;

    private Lock      lock      = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            ++count;
        }
    }

    public void threadMethodOne() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            System.out.println(Thread.currentThread().getName() + " starts waiting...");
            condition.await();
            System.out.println(Thread.currentThread().getName() + " has waken up");
            increment();
            System.out.println(Thread.currentThread().getName() + " finished threadMethodOne increment");
        } finally {
            lock.unlock();
        }
    }

    public void threadMethodTwo() throws InterruptedException {
        Thread.sleep(1000);
        try {
            System.out.println(Thread.currentThread().getName() + " starts waking other thread");
            lock.lockInterruptibly();
            condition.signal();
            increment();
            System.out.println(Thread.currentThread().getName() + " finished threadMethodTwo increment");
        } finally {
            lock.unlock();
        }
    }

    public void display() {
        System.out.println("count = " + count);
    }

}
