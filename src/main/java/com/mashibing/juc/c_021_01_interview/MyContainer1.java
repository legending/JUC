/**
 * �����⣺дһ���̶�����ͬ��������ӵ��put��get�������Լ�getCount������
 * �ܹ�֧��2���������߳��Լ�10���������̵߳���������
 * ʹ��wait��notify/notifyAll��ʵ��
 *
 * ץҩҪ���ǵ������ǣ��̼߳����ͨ�š�Э��
 * 
 * д��Ҫ��ع�
 * 1. put/get ÿ����һ�ξͲ���������һ����Ʒ���������ڸ��̷߳��������������٣�û���̷ֶ߳��٣���������ö��߳�����
 * 2. put/get ��Ҫ����lists������ֲ������⣬Ҫ��synchronized����: (1)wait/notify/notifyAll��Ҫ����ͬ��������
 * 3. Ҫ��ȷ֪��volatile��synchronized������Ϊʲôlists����volatile���Σ���
 *    ����˵lists��Ҫ�̼߳�ɼ������κ�volatileֻ�����ڻ������ͣ�����lists���ڲ������⣨����߳�ͬʱ��д��
 *    �������㣬������Ҫ��synchronized => ʹ��synchronizedǿ��ˢ�±���ֵ��ʵ�ֿɼ��ԣ�https://www.cnblogs.com/xuwenjin/p/9044230.html
 * 3. put/get һ������ѭ��wait��Ȼ��ִ������/���Ѷ���
 * 4. ʹ�÷���
 *
 * �������в��㣺notifyALl�ỽ�Ѱ������Լ�֮��������̣߳������̼߳Ȱ��������ߣ�Ҳ����������
 * ��ʵ����������Ҫ����ĳ�������߳�ֻ֪ͨ���������̣߳�ĳ�������߳�ֻ��Ҫ֪ͨ���������߳�
 *
 * Ϊʲô��while��������if��
 * ��Ϊ���̱߳����ѵ�ʱ����Ȼ��Ҫ�жϵ�ǰ�Ķ������
 */
package com.mashibing.juc.c_021_01_interview;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyContainer1<T> {
	final private LinkedList<T> lists = new LinkedList<>();//���д��ڶ���߳�ͬʱ��д�������ͨ��ͬ��������
	final private int MAX = 10; //���10��Ԫ��
	private int count = 0; //ͬ����һ������Ϊ�Ѿ�ͨ��ͬ�������������ˣ����ԾͲ���Ҫ��volatile��

	public synchronized void put(T t) {
		while(lists.size() == MAX) {
		//if(lists.size() == MAX) {
			try {
				this.wait(); //effective java
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		lists.add(t);
		System.out.println(t.toString() + " is produced by " + Thread.currentThread().getName());
		++count;
		this.notifyAll(); //֪ͨ�������߳̽�������
	}
	
	public synchronized T get() {
		T t = null;
		while(lists.size() == 0) {
		//if(lists.size() == 0) {
			try {
				this.wait();
				//Thread.sleep(100);//������֤��if������
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t = lists.removeFirst();
		System.out.println(t.toString() + " is consumed by " + Thread.currentThread().getName());
		count--;
		this.notifyAll(); //֪ͨ�����߽�������
		return t;
	}
	
	public static void main(String[] args) {
		MyContainer1<String> container = new MyContainer1<>();
		//�����������߳�
		for(int i=0; i<10; i++) {
			new Thread(()->{
				for(int j=0; j<5; j++) {
					container.get();
				}
			}, "consumer-" + i).start();
		}
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//�����������߳�
		for(int i=0; i<2; i++) {
			new Thread(()->{
				for(int j=0; j<25; j++) {
					container.put(Integer.toString(new Random().nextInt(100)));
				}
			}, "producer-" + i).start();
		}
	}
}
