/**
 * ThreadLocal线程局部变量
 *
 * 本实例模拟一个常见的需求
 * 多个线程要访问通一个变量，并对其操作，所以当某个线程操作完之后下一个线程获取到的值已经变了
 * 所以很自然的就想到：能不能声明一个变量，它可以为每个线程生成一个当前变量的一个副本，那么每个线程只会操作自己的副本
 * 那么久不会产生线程之期间相互干扰了
 * 而这就是ThreadLocal存在的意义
 */
package com.legend.juc.c_022_RefTypeAndThreadLocal;

import java.util.concurrent.TimeUnit;

public class ThreadLocal1 {
	volatile static Person p = new Person();
	
	public static void main(String[] args) {
				
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(p.name);
		}).start();
		
		new Thread(()->{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.name = "lisi";
		}).start();
	}
}

class Person {
	String name = "zhangsan";
}
