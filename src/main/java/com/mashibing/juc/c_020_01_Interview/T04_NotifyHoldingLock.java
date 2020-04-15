/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 * 
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 * 
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 * 想想这是为什么？
 *
 * notify并不会释放锁，只是告诉调用过wait方法的线程可以去参与当前锁的竞争，不能保证一定得到锁 => X
 *
 */
package com.mashibing.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;

public class T04_NotifyHoldingLock { //wait notify

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		T04_NotifyHoldingLock c = new T04_NotifyHoldingLock();
		
		final Object lock = new Object();

		Thread t1 = new Thread(() -> {
			synchronized(lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);

					if(c.size() == 5) {
						lock.notify();
					}
				}
			}
		}, "t1");
		
		Thread t2 = new Thread(() -> {
			synchronized(lock) {
				System.out.println(c.size());
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("5 bingo");
			}
		}, "t2");

		//先执行t1，后执行t2
		//t1先start先得到锁，于是打印所有元素，然后t2永久wait，因为在t1运行时t2抢不到锁，当t1结束的时候list.size()早已不是5，所以t2就永久wait了
		//t1.start();
		//t2.start();

		//先执行t2，后执行t1
		//t2先start先得到锁，上来就wait并释放锁，之后t1运行但notify时并未释放锁，所以t1始终得不到锁，直到最后t1运行完毕，t2才得以在wait的位置继续运行
		t2.start();
		t1.start();
	}
}
