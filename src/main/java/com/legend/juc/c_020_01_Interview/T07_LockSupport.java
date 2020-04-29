/**
 * ʹ��Latch�����ţ����wait notify������֪ͨ
 * �ô���ͨ�ŷ�ʽ�򵥣�ͬʱҲ����ָ���ȴ�ʱ��
 * ʹ��await��countdown�������wait��notify
 * CountDownLatch���漰��������count��ֵΪ��ʱ��ǰ�̼߳�������
 * �����漰ͬ����ֻ���漰�߳�ͨ�ŵ�ʱ����synchronized + wait/notify���Ե�̫����
 * ��ʱӦ�ÿ���countdownlatch/cyclicbarrier/semaphore
 *
 * LockSupport����sleep������¿���ʵ�ָù��ܣ��������sleepͬ������ʵ�֣�t1��t2����ִ��˳��ͬ������ʵ��
 * LockSupport�Դ��ɼ��� => V
 */
package com.legend.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class T07_LockSupport {

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		T07_LockSupport c = new T07_LockSupport();

		Thread t2 = new Thread(() -> {
			if (c.size() != 5) {
				LockSupport.park();
			}
		}, "t2");

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					System.out.println("5 bingo");
					LockSupport.unpark(t2);
				}

				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}
		}, "t1");

		t1.start();
		/*try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		t2.start();
	}
}
