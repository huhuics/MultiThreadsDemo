/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.semaphore12;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 生成连接的工程
 * @author HuHui
 * @version $Id: ConnectionFactory.java, v 0.1 2017年2月16日 下午7:56:56 HuHui Exp $
 */
public class ConnectionFactory {

    private static ConnectionFactory instance  = new ConnectionFactory();

    private Semaphore                semaphore = new Semaphore(10);

    private Random                   random    = new Random(3000L);

    private ConnectionFactory() {

    }

    public static ConnectionFactory getInstance() {
        return instance;
    }

    public void doConnecte() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " 获取到了一个许可,许可还剩" + semaphore.availablePermits());

            if (semaphore.availablePermits() == 0) {
                System.out.println("许可用完,扩大许可");
                semaphore.release(15);
                System.out.println("扩大后许可数量" + semaphore.availablePermits());
            }

            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + " 释放了一个许可,许可还剩" + semaphore.availablePermits());
        }
    }
}
