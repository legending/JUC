/**
 * ʹ��Latch�����ţ����wait notify������֪ͨ
 * �ô���ͨ�ŷ�ʽ�򵥣�ͬʱҲ����ָ���ȴ�ʱ��
 * ʹ��await��countdown�������wait��notify
 * CountDownLatch���漰��������count��ֵΪ��ʱ��ǰ�̼߳�������
 * �����漰ͬ�����������⣩��ֻ���漰�߳�ͨ�ŵ�ʱ����synchronized + wait/notify���Ե�̫����
 * ��ʱӦ�ÿ���countdownlatch/cyclicbarrier/semaphore
 *
 * ����ͨ��sleep��ʵ��lists�Ŀɼ��ԣ����ȥ��sleep��CountDownLatch�޷�ʵ�ֹ��� -> x
 */
package com.legend.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class T06_CountDownLatch {

    List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		T06_CountDownLatch c = new T06_CountDownLatch();

		CountDownLatch latch = new CountDownLatch(1);

		Thread t1 = new Thread(() -> {
			if (c.size() != 5) {
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("5 bingo");
		}, "t2");

		//����ȴ�һ�������ʹlists�����ݿɼ�������ó�����Ȼ�޷�ʵ��
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					// �����ţ���t2����ִ��
					latch.countDown();
				}

                //����ȴ�һ�������ʹlists�����ݿɼ�������ó�����Ȼ�޷�ʵ��
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}, "t1");

		t2.start();
		t1.start();
	}
}
