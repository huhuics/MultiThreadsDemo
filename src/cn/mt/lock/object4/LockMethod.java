/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.lock.object4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 锁方法
 * @author HuHui
 * @version $Id: LockMethod.java, v 0.1 2017年2月8日 下午5:29:06 HuHui Exp $
 */
public class LockMethod {

    private Random              random = new Random();

    private final List<Integer> list1  = new ArrayList<Integer>();
    private final List<Integer> list2  = new ArrayList<Integer>();

    public synchronized void section1() throws InterruptedException {
        list1.add(random.nextInt());
        Thread.sleep(1);
    }

    public synchronized void section2() throws InterruptedException {
        list2.add(random.nextInt());
        Thread.sleep(1);
    }

    public void doWork() {
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        section1();
                        section2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        section1();
                        section2();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();

            System.out.println("lock method, list1.size=" + list1.size() + ", list2.size=" + list2.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
