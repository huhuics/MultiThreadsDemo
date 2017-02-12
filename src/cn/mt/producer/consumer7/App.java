/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.producer.consumer7;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用阻塞队列实现生产者与消费者
 * @author HuHui
 * @version $Id: App.java, v 0.1 2017年2月10日 下午3:46:29 HuHui Exp $
 */
public class App {

    private static Random                 random = new Random();

    private static BlockingQueue<Integer> queue  = new ArrayBlockingQueue<Integer>(10);

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    producer();
                    System.out.println("生产一个整数,当前队列大小:" + queue.size());
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    consumer();
                    System.out.println("消费一个整数,当前队列大小:" + queue.size());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join(10000);
        thread2.join(10000);

        System.out.println("运行完成");

        System.exit(0);

    }

    public static void producer() {
        try {
            queue.put(random.nextInt());
        } catch (InterruptedException e) {
            System.out.println("生产者线程发生中断");
        }
    }

    public static Integer consumer() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            System.out.println("消费者线程发生中断");
        }
        return null;
    }

}
