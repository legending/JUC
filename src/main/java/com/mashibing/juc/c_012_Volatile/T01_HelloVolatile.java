/**
 * volatile 关键字，使一个变量在多个线程间可见 -> 主要是由缓存一致性协议来保证，主要指MESI
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 * 
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 * 
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 * 
 * 可以阅读这篇文章进行更深入的理解
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 * 
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * 如果说多个线程同时修改running有可能出现问题
 *
 * 另一方面说明一个问题：通过synchronized方法可以解决可见性问题（通过强制刷新工做内存与主内存的值来实现，这个跟JMM中关于synchronized的规定有关）
 * 具体见https://www.cnblogs.com/xuwenjin/p/9044230.html
 *
 */
package com.mashibing.juc.c_012_Volatile;

import java.util.concurrent.TimeUnit;

public class T01_HelloVolatile {
	/*volatile*/ boolean running = true; //对比一下有无volatile的情况下，整个程序运行结果的区别
	void m1() {
		System.out.println("m1 start");
		while(running) {
		}
		System.out.println("m1 end!");
	}

	synchronized boolean getStatus() { //
		return running;
	}

	synchronized void setStatus (boolean flag) {
		System.out.println("setRunning start");
		this.running = flag;
		System.out.println("setRunning end");
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("main start");
		T01_HelloVolatile t = new T01_HelloVolatile();

		//实验一：测试volatile的线程可见性
		//只有running加了volatile，m1才会停止
		/*new Thread(t::m1, "t1").start();
		TimeUnit.SECONDS.sleep(2);
		t.running = false;*/

		//实验二：测试 用同步方法修改running值，可以使线程停止
		//因为对running的修改都做了同步，所以不存在多个线程同时读写的问题，所以也就不用加volatile
		new Thread(() -> {
			while(true) {
				//if (!t.running) {
				if (!t.getStatus()) {
					System.out.println("running = false");
					break;
				}
			}
		}).start();
		TimeUnit.SECONDS.sleep(2);
		//t.running = false;
		t.setStatus(false);

		System.out.println("main end");
	}
	
}


