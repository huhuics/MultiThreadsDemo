/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.lock.object4;

/**
 * 
 * @author HuHui
 * @version $Id: Worder.java, v 0.1 2017年2月8日 下午10:10:02 HuHui Exp $
 */
public class Worder {

    /**
     * @param args
     */
    public static void main(String[] args) {

        LockBlock lb = new LockBlock();
        LockMethod lm = new LockMethod();
        Long start = System.currentTimeMillis();
        Long cost;
        lb.doWork();
        cost = System.currentTimeMillis() - start;

        System.out.println("lock block costs: " + cost);

        start = System.currentTimeMillis();
        lm.doWork();
        cost = System.currentTimeMillis() - start;

        System.out.println("lock method costs: " + cost);

    }

}
