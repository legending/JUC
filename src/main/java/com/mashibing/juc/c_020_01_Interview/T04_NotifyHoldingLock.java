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
 * notify�������ͷ�����ֻ�Ǹ��ߵ��ù�wait�������߳̿���ȥ���뵱ǰ���ľ��������ܱ�֤һ���õ��� => X
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

		//��ִ��t1����ִ��t2
		//t1��start�ȵõ��������Ǵ�ӡ����Ԫ�أ�Ȼ��t2����wait����Ϊ��t1����ʱt2������������t1������ʱ��list.size()���Ѳ���5������t2������wait��
		//t1.start();
		//t2.start();

		//��ִ��t2����ִ��t1
		//t2��start�ȵõ�����������wait���ͷ�����֮��t1���е�notifyʱ��δ�ͷ���������t1ʼ�յò�������ֱ�����t1������ϣ�t2�ŵ�����wait��λ�ü�������
		t2.start();
		t1.start();
	}
}
