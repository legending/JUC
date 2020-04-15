/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * 
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ��������ô���أ�
 * 
 * ����ʹ��wait��notify������wait���ͷ�������notify�����ͷ���
 * ��Ҫע����ǣ��������ַ���������Ҫ��֤t2��ִ�У�Ҳ����������t2�����ſ���
 * 
 * �Ķ�����ĳ��򣬲�����������
 * ���Զ���������������size=5ʱt2�˳�������t1����ʱt2�Ž��յ�֪ͨ���˳�
 * ��������Ϊʲô��
 * 
 * notify֮��t1�����ͷ�����t2�˳���Ҳ����notify��֪ͨt1����ִ��
 * ����ͨ�Ź��̱ȽϷ���
 *
 * synchronizedʵ�ֿɼ��ԣ�t1,t2˳��ִ�У�����ִ�ж������� => V
 */
package com.mashibing.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;

public class T05_NotifyFreeLock {

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		T05_NotifyFreeLock c = new T05_NotifyFreeLock();
		
		final Object lock = new Object();

		Thread t1 = new Thread(() -> {
			synchronized(lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					if(c.size() == 5) {
						lock.notify();//��Ҫ��wait��˳��㷴��
						//�ͷ�������t2����ִ��
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
			synchronized(lock) {
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("5 bingo");
				//֪ͨt1����ִ��
				lock.notify();
			}
		}, "t2");

		//t1,t2˳����Եߵ����������T04
		t2.start();
		t1.start();

	}
}
