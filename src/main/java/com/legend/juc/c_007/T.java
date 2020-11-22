/**
 * 两个线程是否可以同时调用一个对象的同步和非同步方法？
 * 进一步，是否可以同时调用一个对象的同一个同步方法？
 * 再进一步，是否可以调用同一个对象的不同的同步方法？
 */

package com.legend.juc.c_007;

public class T {

	public synchronized void m1() { 
		System.out.println(Thread.currentThread().getName() + " m1 start...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m1 end");
	}
	
	public void m2() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m2 ");
	}

	public synchronized void m3() {
		System.out.println(Thread.currentThread().getName() + " m3 start...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m3 end");
	}
	
	public static void main(String[] args) {
		T t = new T();
		
		/*new Thread(()->t.m1(), "t1").start();
		new Thread(()->t.m2(), "t2").start();*/

		//两个线程可以同时调用一个对象的同步方法与非同步方法
		/*new Thread(t::m1, "t1").start();
		new Thread(t::m2, "t2").start();*/

		//两个线程不能同时调用一个对象的同一个同步方法，只能一个先一个后同步运行
		/*new Thread(t::m1, "t1").start();
		new Thread(t::m1, "t2").start();*/

		//两个线程不能同时调用一个对象的不同同步方法，只能一个先一个后同步运行
		new Thread(t::m1, "t1").start();
		new Thread(t::m3, "t2").start();
		
		/*
		//1.8之前的写法
		new Thread(new Runnable() {

			@Override
			public void run() {
				t.m1();
			}
			
		});
		*/
		
	}
	
}
