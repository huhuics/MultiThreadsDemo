/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.wait.notify8;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多生产者和多消费者
 * @author HuHui
 * @version $Id: MultiProducerAndConsumer.java, v 0.1 2017年2月12日 下午5:22:28 HuHui Exp $
 */
public class MultiProducerAndConsumer {

    private ExecutorService     producerPool = Executors.newFixedThreadPool(10);

    private ExecutorService     consumerPool = Executors.newFixedThreadPool(3);

    private Random              random       = new Random();

    private BlockQueue<Integer> queue        = new BlockQueue<Integer>(30);

    private ProducerTask        producerTask = new ProducerTask(queue, random);

    private ConsumerTask        consumerTask = new ConsumerTask(queue);

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        MultiProducerAndConsumer mpc = new MultiProducerAndConsumer();
        mpc.run();

        System.out.println("运行结束");
        System.exit(0);
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            producerPool.submit(producerTask);
        }
        producerPool.shutdown();

        for (int j = 0; j < 3; j++) {
            consumerPool.submit(consumerTask);
        }
        consumerPool.shutdown();

        producerPool.awaitTermination(5L, TimeUnit.SECONDS);
        consumerPool.awaitTermination(5L, TimeUnit.SECONDS);
        System.out.println("time out");
    }
}

/**
 * 生产者任务
 */
class ProducerTask implements Runnable {

    private BlockQueue<Integer> queue;

    private Random              random;

    public ProducerTask(BlockQueue<Integer> queue, Random random) {
        this.queue = queue;
        this.random = random;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.put(random.nextInt());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 消费者任务
 */
class ConsumerTask implements Runnable {

    private BlockQueue<Integer> queue;

    public ConsumerTask(BlockQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.take();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
