/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * 
 * 分析下面这个程序，能完成这个功能吗？
 *
 * 可见性无法保证 => x
 */
package com.mashibing.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class T01_WithoutVolatile {

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		T01_WithoutVolatile c = new T01_WithoutVolatile();

		new Thread(() -> {
			for(int i=0; i<10; i++) {
				c.add(new Object());
				System.out.println("add " + i);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1").start();
		
		new Thread(() -> {
			while(true) {
				if(c.size() == 5) {
					System.out.println("5 bingo");
					break;
				}
			}
			System.out.println("t2 结束");
		}, "t2").start();
	}
}


/*
* 问题
* 1. ArrayList线程不安全（在执行size的过程中，可能出现添加元素的操作，那么获取到的size可能会比实际小）
* 2. lists线程间不可见
* */