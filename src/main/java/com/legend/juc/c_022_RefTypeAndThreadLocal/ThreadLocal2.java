/**
 * ThreadLocal�ֲ߳̾�����
 *
 * ThreadLocal��ʹ�ÿռ任ʱ�䣬synchronized��ʹ��ʱ�任�ռ�
 * ������hibernate��session�ʹ�����ThreadLocal�У�����synchronized��ʹ��
 *
 * ��������ĳ������ThreadLocal��ThreadLocalMap��Դ��
 *
 * ThreadLocal��;
 * ����ʽ����(mybatis,hibernate)����֤ͬһ��connection
 *
 */
package com.legend.juc.c_022_RefTypeAndThreadLocal;

import java.util.concurrent.TimeUnit;

public class ThreadLocal2 {
	//volatile static Person p = new Person();
	static ThreadLocal<Person> tl = new ThreadLocal<>();//ThreadLocal��Դ��

	public static void main(String[] args) {
				
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("t1:" + tl.get());//�����ӡ��������null����ΪThreadLocal���̼߳����໥������
		}).start();
		
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tl.set(new Person());

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("t2: " + tl.get());//�������get������Ϊ����Ϊ�ڱ��߳���

		}).start();
	}
	
	static class Person {
		String name = "zhangsan";
	}
}


