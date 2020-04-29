/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 通过synchronized方法可以解决可见性问题（通过强制刷新工做内存与主内存的值来实现，这个跟JMM中关于synchronized的规定有关）
 * 具体见https://www.cnblogs.com/xuwenjin/p/9044230.html
 * 但还是有问题，由于t1在添加完第5个元素后并没有等待t2去完成输出提醒，而是继续执行后面的代码
 *
 * 所以归根结底还是两个线程之间通信的问题没有处理好：t1在什么时候停下来，等待t2去检测 这个问题没有处理好
 *
 * 怎么办？
 * 使用wait,notify
 *
 * 不多还是有瑕疵
 * while循环一直在消耗CPU资源
 *
 * 如何解决 -> 使用适当if判断，然后用wait代替while -> 详见T05
 *
 * 当使用notify/wait通信时一定要先设定哪个线程先执行，哪个后执行，然后在这个基础上去写对应线程的逻辑(顺序不同写出来的程序也是不同的)
 *
 * synchronized实现可见性，但需要注意调用顺序 => V
 */
package com.legend.juc.c_020_01_Interview;

import java.util.LinkedList;
import java.util.List;

public class T03_WithSynchronized {

	List lists = new LinkedList<>();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) throws InterruptedException {
		T03_WithSynchronized c = new T03_WithSynchronized();

		final Object lock = new Object();

		Thread t1 = new Thread(() -> {
			synchronized (lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					if (c.size() == 5) {
						lock.notify();
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, "t1");
		
		Thread t2 = new Thread(() -> {
			synchronized (lock) {
				while(true) {
					if(c.size() == 5) {
						System.out.println("5 bingo");
						lock.notify();
						break;
					}
				}
			}
		}, "t2 loop");

		//先执行t1，后执行t2，正常
		t1.start();
		t2.start();

		//先执行t2，后执行t1，t2死循环始终占有锁，无法执行t1
		//所以当使用notify/wait通信时一定要先设定哪个线程先执行，哪个后执行，然后在这个基础上去写对应线程的逻辑
		//t2.start();
		//t1.start();
	}
}
