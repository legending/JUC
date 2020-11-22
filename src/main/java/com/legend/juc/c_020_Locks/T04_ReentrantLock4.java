/**
 * ʹ��ReentrantLock�����Ե���lockInterruptibly���������Զ��߳�interrupt����������Ӧ��
 * ��һ���̵߳ȴ����Ĺ����У����Ա����
 * 
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
				lock.lockInterruptibly();//���Զ�interrupt()����������Ӧ
				System.out.println("t1 start");
				TimeUnit.SECONDS.sleep(100);
				System.out.println("t1 end");
			} catch (InterruptedException e) {
				System.out.println("t1 interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t1.start();
		
		Thread t2 = new Thread(()->{
			try {
				lock.lock();
				System.out.println("t2 start");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("t2 end");
			} catch (InterruptedException e) {
				System.out.println("t2 interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t2.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Interrupt t1");
		t1.interrupt(); //����߳�1�ĵȴ�
		
	}
}
