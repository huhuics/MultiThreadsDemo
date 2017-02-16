/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试Callable
 * @author HuHui
 * @version $Id: CallableTester.java, v 0.1 2017年2月16日 下午9:22:29 HuHui Exp $
 */
public class CallableTester {

    /**
     * @param args
     * @throws InterruptedException 
     * @throws ExecutionException 
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        List<CountTask> tasks = new ArrayList<CountTask>();
        for (int i = 0; i < 10; i++) {
            CountTask task = new CountTask(i);
            tasks.add(task);
        }

        Integer rets = threadPool.invokeAny(tasks);

        System.out.println(rets);

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);

    }
}

class CountTask implements Callable<Integer> {

    private int    id;

    private Random random = new Random();

    public CountTask(int id) {
        this.id = id;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " is running");
        Thread.sleep(random.nextInt(200));
        return id;
    }

}
