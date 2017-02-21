/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.Random;

/**
 * 组合状态
 * @author HuHui
 * @version $Id: InvalidCombinationStateDemo.java, v 0.1 2017年2月20日 下午9:41:01 HuHui Exp $
 */
public class InvalidCombinationStateDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {

        InvalidCombinationStateDemo demo = new InvalidCombinationStateDemo();

        Runner runner = demo.getInstance();

        Thread thread = new Thread(runner);
        thread.start();

        Random random = new Random();

        while (true) {
            int randomInt = random.nextInt(1000);
            runner.m = randomInt;
            runner.n = 2 * randomInt;
        }
    }

    public Runner getInstance() {
        return new Runner();
    }

    private class Runner implements Runnable {//读写要同步才能解决这个问题

        public int m;
        public int n;

        @Override
        public void run() {
            System.out.println("子线程开始运行");
            int c1 = 0, c2 = 0;
            for (int i = 0;; i++) {
                if (2 * m != n) {
                    c1++;
                    System.out.println("第" + c1 + "次不相同, 2*m=" + 2 * m + ", n=" + n);
                } else {
                    c2++;
                    System.out.println("第" + c2 + "次相等, 2*m=" + 2 * m + ", n=" + n);
                }
            }
        }
    }

}
