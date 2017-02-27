/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author HuHui
 * @version $Id: TryLockTest.java, v 0.1 2017年2月27日 下午5:14:54 HuHui Exp $
 */
public class TryLockTest {

    private final Lock lock = new ReentrantLock();

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        /* Runner runner = new Runner();
         Thread thread1 = new Thread(runner);

         Thread thread2 = new Thread(runner);

         thread1.start();
         thread2.start();*/

        TryLockTest tlt = new TryLockTest();
        tlt.doSomeThing();

    }

    public void doSomeThing() throws InterruptedException {
        if (!lock.tryLock(3, TimeUnit.SECONDS)) {
            System.out.println("获取锁超时");
            return;
        }

        System.out.println("获取锁成功,开始处理");

        Thread.sleep(5000);

        lock.unlock();
        System.out.println("处理完成");

    }

}

class Runner implements Runnable {

    private final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            boolean lockRet = lock.tryLock(3, TimeUnit.SECONDS);
            if (lockRet) {
                System.out.println(Thread.currentThread().getName() + "成功获取锁");
            } else {
                System.out.println(Thread.currentThread().getName() + "获取锁失败");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "进入run方法,开始sleep");
            Thread.sleep(6000);
            System.out.println(Thread.currentThread().getName() + "sleep结束");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "线程中断");
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            lock.unlock();
        }
    }

}
