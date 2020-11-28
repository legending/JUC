/**
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * lock：只有获取了锁才能响应中断。
 * lockInterruptibly：不用获取锁，可以直接中断
 *
 */
package com.legend.juc.c_020_Locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T04_ReentrantLock4 {
		
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		
		Thread t1 = new Thread(()->{
			try {
				lock.lock();//可以对interrupt()方法做出响应
				System.out.println("t1 start");
				TimeUnit.SECONDS.sleep(15);
				System.out.println("t1 end");
			} catch (InterruptedException e) {
				System.out.println("t1 interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t1.start();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Thread t2 = new Thread(()->{
			try {
				//lock.lock();
				lock.lockInterruptibly();
				System.out.println("t2 start");
				TimeUnit.SECONDS.sleep(5);
				System.out.println("t2 end");
			} catch (InterruptedException e) {
				System.out.println("t2 interrupted!");
			} finally {
				lock.unlock();
				System.out.println("free");
			}
		});
		t2.start();

		//打断线程2的等待
		//如果t2线程中使用的是lock.lock()则无法被立马打断，只有等到t1中的lock被释放，t2才会被打断
		//如果t2线程中使用的是lock.lockInterruptibly()，则t2会立马被打断
		t2.interrupt();
	}
}
