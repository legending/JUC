/**
 * ʹ��Latch�����ţ����wait notify������֪ͨ
 * �ô���ͨ�ŷ�ʽ�򵥣�ͬʱҲ����ָ���ȴ�ʱ��
 * ʹ��await��countdown�������wait��notify
 * CountDownLatch���漰��������count��ֵΪ��ʱ��ǰ�̼߳�������
 * �����漰ͬ����ֻ���漰�߳�ͨ�ŵ�ʱ����synchronized + wait/notify���Ե�̫����
 * ��ʱӦ�ÿ���countdownlatch/cyclicbarrier/semaphore
 *
 * LockSupport����sleepͬ������ʵ�֣�t1��t2����ִ��˳��ͬ������ʵ�� => V
 */
package com.legend.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class T08_LockSupport_WithoutSleep {

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	static Thread t1 = null, t2 = null;

	public static void main(String[] args) {
		T08_LockSupport_WithoutSleep c = new T08_LockSupport_WithoutSleep();

		t1 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					LockSupport.unpark(t2);
					LockSupport.park();
				}
			}
		}, "t1");

		t2 = new Thread(() -> {
			//System.out.println("t2����");
			//if (c.size() != 5) {
				LockSupport.park();
			//}
			System.out.println("5 bingo");
			LockSupport.unpark(t1);

		}, "t2");

		//t1,t2����˳�򶼿���ʵ�֣���һ�����wait/notify
		t1.start();
		t2.start();
	}
}
