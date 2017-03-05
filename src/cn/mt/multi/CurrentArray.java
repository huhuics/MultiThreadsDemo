/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁分片
 * @author HuHui
 * @version $Id: LockSegmtDemo.java, v 0.1 2017年3月5日 下午3:57:48 HuHui Exp $
 */
public class CurrentArray<T> {

    private int        capacity;

    private Lock[]     locks;

    private Entry<T>[] entries;

    public CurrentArray(int capacity) {
        this.capacity = capacity;

        locks = new ReentrantLock[capacity];

        entries = new Entry[capacity];
    }

    public T put(T t) {

        int hash = t.hashCode();
        int index = hash % entries.length;

        Lock lock = getLock(index);
        lock.lock();
        try {
            Entry<T> entry = entries[index];
            if (entry == null) {
                entry = new Entry<T>();
                entries[index] = entry;
            }
            entry.setT(t);
        } finally {
            lock.unlock();
        }

        return t;
    }

    public T get(int index) {
        Lock lock = getLock(index);
        lock.lock();
        try {
            Entry<T> entry = entries[index];
            if (entry == null) {
                return null;
            }
            return entry.getT();
        } finally {
            lock.unlock();
        }
    }

    private Lock getLock(int index) {
        Lock lock = locks[index];
        if (lock == null) {
            lock = new ReentrantLock();
            locks[index] = lock;
        }
        return lock;
    }

    private class Entry<T> {

        T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

}
