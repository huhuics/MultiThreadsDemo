/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.threadlocal;

/**
 * ThreadLocal用法示例
 * @author HuHui
 * @version $Id: ThreadLocalExample.java, v 0.1 2017年2月7日 下午8:31:43 HuHui Exp $
 */
public class ThreadLocalExample {

    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {

                                                     /**
                                                      * 这种方法初始化ThreadLocal变量对所有变量都可见
                                                      */
                                                     @Override
                                                     protected Integer initialValue() {
                                                         return 99;
                                                     }

                                                 };

        @Override
        public void run() {

            /**
             * 通过set方法初始化只对当前线程可见
             */
            threadLocal.set((int) (Math.random() * 1000));

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }

            System.out.println(Thread.currentThread().getName() + ", " + threadLocal.get());
        }

        public int getValue() {
            return threadLocal.get();
        }

    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        MyRunnable instance = new MyRunnable();
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("getValue=" + instance.getValue());
    }

}
