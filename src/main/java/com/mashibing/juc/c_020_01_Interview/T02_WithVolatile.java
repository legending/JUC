/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，
 * 而且，如果在if 和 break之间被别的线程打断，得到的结果也不精确，
 * 该怎么做呢？
 *
 */
package com.mashibing.juc.c_020_01_Interview;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class T02_WithVolatile {

	//添加volatile，使t2能够得到通知
	//volatile List lists = new LinkedList();
	volatile List lists = Collections.synchronizedList(new LinkedList<>());

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {

		T02_WithVolatile c = new T02_WithVolatile();
		new Thread(() -> {
			for(int i=0; i<10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				//因为sleep的时间很充足读与写之间产生冲突的可能性不大，去掉sleep之后问题依然无法解决
				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}
		}, "t1").start();
		
		new Thread(() -> {
			while(true) {
				if(c.size() == 5) {
					break;
				}
			}
			System.out.println("t2 结束");
		}, "t2").start();
	}
}

/*
* 发现一个很有意思的现象，当sleep 1秒的时候，lists的size会被同步（线程可见只能保证引用本身可见，但并不能保证引用对象内的属性可见）
* 所以可以推断，给与足够时间，volatile修饰的引用对应的对象的属性也可能存在可见性
*
* 建议
* 1. 尽量不要使用volatile
* 2. 如果用的话，尽量只用在基础类型上，不要用在引用类型
* */