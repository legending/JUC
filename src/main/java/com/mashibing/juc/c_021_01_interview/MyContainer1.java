/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * 使用wait和notify/notifyAll来实现
 *
 * 抓药要考虑的问题是：线程间如何通信、协作
 * 
 * 写完要点回顾
 * 1. put/get 每调用一次就产生或消费一个商品，这样便于给线程分配任务（生产多少，没给线程分多少），充分利用多线程性能
 * 2. put/get 都要操作lists，会出现并发问题，要用synchronized修饰: (1)wait/notify/notifyAll需要用在同步环境中
 * 3. 要明确知道volatile与synchronized的区别（为什么lists不用volatile修饰？）
 *    按理说lists需要线程间可见，但奈何volatile只能用于基本类型，加上lists存在并发问题（多个线程同时读写）
 *    综上两点，所以需要用synchronized => 使用synchronized强制刷新变量值来实现可见性，https://www.cnblogs.com/xuwenjin/p/9044230.html
 * 3. put/get 一定是先循环wait，然后执行生产/消费动作
 * 4. 使用泛型
 *
 * 但是仍有不足：notifyALl会唤醒包含除自己之外的其他线程，其他线程既包括生产者，也包括消费者
 * 而实际上我们需要的是某个生产线程只通知所有消费线程，某个消费线程只需要通知所有生产线程
 *
 * 为什么用while而不是用if？
 * 因为当线程被唤醒的时候，任然需要判断当前的队列情况
 */
package com.mashibing.juc.c_021_01_interview;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyContainer1<T> {
	final private LinkedList<T> lists = new LinkedList<>();//队列存在多个线程同时读写的情况（通过同步方法）
	final private int MAX = 10; //最多10个元素
	private int count = 0; //同队列一样，因为已经通过同步方法来操作了，所以就不需要加volatile了

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
		this.notifyAll(); //通知消费者线程进行消费
	}
	
	public synchronized T get() {
		T t = null;
		while(lists.size() == 0) {
		//if(lists.size() == 0) {
			try {
				this.wait();
				//Thread.sleep(100);//用于验证用if有问题
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t = lists.removeFirst();
		System.out.println(t.toString() + " is consumed by " + Thread.currentThread().getName());
		count--;
		this.notifyAll(); //通知生产者进行生产
		return t;
	}
	
	public static void main(String[] args) {
		MyContainer1<String> container = new MyContainer1<>();
		//启动消费者线程
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
		
		//启动生产者线程
		for(int i=0; i<2; i++) {
			new Thread(()->{
				for(int j=0; j<25; j++) {
					container.put(Integer.toString(new Random().nextInt(100)));
				}
			}, "producer-" + i).start();
		}
	}
}
