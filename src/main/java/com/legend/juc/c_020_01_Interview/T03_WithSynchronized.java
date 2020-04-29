/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * 
 * ͨ��synchronized�������Խ���ɼ������⣨ͨ��ǿ��ˢ�¹����ڴ������ڴ��ֵ��ʵ�֣������JMM�й���synchronized�Ĺ涨�йأ�
 * �����https://www.cnblogs.com/xuwenjin/p/9044230.html
 * �����������⣬����t1��������5��Ԫ�غ�û�еȴ�t2ȥ���������ѣ����Ǽ���ִ�к���Ĵ���
 *
 * ���Թ����׻��������߳�֮��ͨ�ŵ�����û�д���ã�t1��ʲôʱ��ͣ�������ȴ�t2ȥ��� �������û�д����
 *
 * ��ô�죿
 * ʹ��wait,notify
 *
 * ���໹����覴�
 * whileѭ��һֱ������CPU��Դ
 *
 * ��ν�� -> ʹ���ʵ�if�жϣ�Ȼ����wait����while -> ���T05
 *
 * ��ʹ��notify/waitͨ��ʱһ��Ҫ���趨�ĸ��߳���ִ�У��ĸ���ִ�У�Ȼ�������������ȥд��Ӧ�̵߳��߼�(˳��ͬд�����ĳ���Ҳ�ǲ�ͬ��)
 *
 * synchronizedʵ�ֿɼ��ԣ�����Ҫע�����˳�� => V
 */
package com.legend.juc.c_020_01_Interview;

import java.util.LinkedList;
import java.util.List;

public class T03_WithSynchronized {

	List lists = new LinkedList<>();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) throws InterruptedException {
		T03_WithSynchronized c = new T03_WithSynchronized();

		final Object lock = new Object();

		Thread t1 = new Thread(() -> {
			synchronized (lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					if (c.size() == 5) {
						lock.notify();
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
			synchronized (lock) {
				while(true) {
					if(c.size() == 5) {
						System.out.println("5 bingo");
						lock.notify();
						break;
					}
				}
			}
		}, "t2 loop");

		//��ִ��t1����ִ��t2������
		t1.start();
		t2.start();

		//��ִ��t2����ִ��t1��t2��ѭ��ʼ��ռ�������޷�ִ��t1
		//���Ե�ʹ��notify/waitͨ��ʱһ��Ҫ���趨�ĸ��߳���ִ�У��ĸ���ִ�У�Ȼ�������������ȥд��Ӧ�̵߳��߼�
		//t2.start();
		//t1.start();
	}
}
