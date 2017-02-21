/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author HuHui
 * @version $Id: SynchronizationOnMutableFieldDemo.java, v 0.1 2017年2月21日 下午8:14:14 HuHui Exp $
 */
public class SynchronizationOnMutableFieldDemo {

    static final int ADD_COUNT = 10000;

    private static class Listener {

    }

    volatile List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        SynchronizationOnMutableFieldDemo demo = new SynchronizationOnMutableFieldDemo();

        Thread thread1 = new Thread(demo.getInstance());
        Thread thread2 = new Thread(demo.getInstance());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int actualSize = demo.listeners.size();
        int expectSize = ADD_COUNT * 2;

        if (actualSize != expectSize) {
            System.out.println("不相等, actualSize=" + actualSize + ", expectSize=" + expectSize);
        } else {
            System.out.println("相等, actualSize=" + actualSize + ", expectSize=" + expectSize);
        }

    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public ConcurrencyCheckTask getInstance() {
        return new ConcurrencyCheckTask();
    }

    private class ConcurrencyCheckTask implements Runnable {
        @Override
        public void run() {
            System.out.println("ConcurrencyCheckTask started!");
            for (int i = 0; i < ADD_COUNT; i++) {
                addListener(new Listener());
            }
            System.out.println("ConcurrencyCheckTask finished!");
        }
    }

}
