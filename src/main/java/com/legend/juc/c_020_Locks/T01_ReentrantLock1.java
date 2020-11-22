/**
 * reentrantlock�������synchronized
 * ����������m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 * synchronized����Ҳ�ǿ�������
 * �����룺һ���̳߳���ĳ�����������������߳��ٴ������������ʱ�򣬿����ٴ��õ���������磺��ʵ����һ��ͬ����������Ե�����һ��ͬ������
 *
 */
package com.legend.juc.c_020_Locks;

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

//synchronized�����ǿ��Ա��̳е�
class AA extends T01_ReentrantLock1 {
	public static void main(String[] args) {
		new AA().m1();
	}
	synchronized void m1() {System.out.println(11111);}
}
