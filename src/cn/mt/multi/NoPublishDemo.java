/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

/**
 * 无同步的修改在另一个线程中看不到
 * @author HuHui
 * @version $Id: NoPublishDemo.java, v 0.1 2017年2月20日 下午8:41:53 HuHui Exp $
 */
public class NoPublishDemo {

    private boolean flag = false;

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        NoPublishDemo demo = new NoPublishDemo();
        Runner runner = demo.getInstance();
        Thread thread = new Thread(runner);
        thread.start();

        Thread.sleep(1000);

        System.out.println("设置flag为true");
        demo.setFlag(true);
        System.out.println("主线程退出");
    }

    public void setFlag(boolean b) {
        flag = b;
    }

    public Runner getInstance() {
        return new Runner();
    }

    class Runner implements Runnable {
        @Override
        public void run() {
            System.out.println("子线程开始运行");
            while (!flag) {//如果主线程中的flag对子线程可见，那么循环会退出，但实际情况却不一定，在我开发机上几乎循环不会退出。解决方法是用volatile修饰flag变量
            }
            System.out.println("子线程退出");
        }

    }

}
