/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.starting.thread;

/**
 * 匿名类实现多线程
 * @author HuHui
 * @version $Id: ApplicationAnonymous.java, v 0.1 2017年2月7日 下午7:53:32 HuHui Exp $
 */
public class ApplicationAnonymous {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Hello: " + i + " Thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("捕获InterruptedException");
                        throw new RuntimeException("线程中断", e);
                    }
                }
            }

        });

        thread.start();

        thread.interrupt();

    }
}
