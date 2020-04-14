/**
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 * synchronized本身也是可重入锁
 * 可重入：一个线程持有某个对象的锁，当这个线程再次请求这个锁的时候，可以再次拿到这个锁，如：其实就是一个同步方法里可以调用另一个同步方法
 *
 */
package com.mashibing.juc.c_020;

import java.util.concurrent.TimeUnit;

public class T01_ReentrantLock1 {
	synchronized void m1() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			if(i == 2) m2();
		}
		
	}
	
	synchronized void m2() {
		System.out.println("m2 ...");
	}
	
	public static void main(String[] args) {
		T01_ReentrantLock1 rl = new T01_ReentrantLock1();
		new Thread(rl::m1).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//new Thread(rl::m2).start();
	}
}

//synchronized方法是可以被继承的
class AA extends T01_ReentrantLock1 {
	public static void main(String[] args) {
		new AA().m1();
	}
	synchronized void m1() {System.out.println(11111);}
}
