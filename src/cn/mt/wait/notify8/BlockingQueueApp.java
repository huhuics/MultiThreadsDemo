/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.wait.notify8;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Queue接口实现阻塞队列
 * @author HuHui
 * @version $Id: BlockingQueueApp.java, v 0.1 2017年2月12日 下午4:33:11 HuHui Exp $
 */
public class BlockingQueueApp {

    private static Random              random     = new Random();

    private static BlockQueue<Integer> blockQueue = new BlockQueue(10);

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        blockQueue.put(random.nextInt());
                        Thread.sleep(100);
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
                    try {
                        blockQueue.take();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join(8000);
        thread2.join(8000);

        System.exit(0);

    }

}

class BlockQueue<T> {

    private Queue<T>  queue    = new LinkedList<T>();

    private int       capacity;

    private Lock      lock     = new ReentrantLock();

    private Condition notFull  = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    public BlockQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                System.out.println("队列已满");
                notFull.await();
            }
            queue.add(t);
            System.out.println("生产一个整数,队列大小:" + queue.size());

            notEmpty.signal();
        } finally {
            lock.unlock();
        }

    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("队列已空");
                notEmpty.await();
            }
            T t = queue.remove();
            System.out.println("消费一个整数,队列大小:" + queue.size());
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return queue.size();
    }
}
