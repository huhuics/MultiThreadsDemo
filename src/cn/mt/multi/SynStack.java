/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package cn.mt.multi;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 通过非阻塞方式实现线程安全的栈
 * @author HuHui
 * @version $Id: SynStack.java, v 0.1 2017年3月1日 上午11:32:35 HuHui Exp $
 */
public class SynStack<E> {

    private AtomicReference<Node<E>> atoNode = new AtomicReference<Node<E>>();

    public void push(E e) {
        Node<E> newHead = new Node<E>(e);
        Node<E> oldHead;
        do {
            oldHead = atoNode.get();
            newHead.nextNode = oldHead;
        } while (!atoNode.compareAndSet(oldHead, newHead));

    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = atoNode.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.nextNode;
            oldHead.nextNode = null;
        } while (!atoNode.compareAndSet(oldHead, newHead));

        return oldHead.e;
    }

    /**
     * 内部静态类,表示栈的节点
     */
    private static class Node<E> {

        /** 栈元素中的内容对象 */
        public E       e;

        /** 指向下一个节点 */
        public Node<E> nextNode;

        public Node(E e) {
            this.e = e;
        }

    }
}
