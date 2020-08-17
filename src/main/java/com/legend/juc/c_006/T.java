/**
 * 对比上面一个小程序，分析一下这个程序的输出
 *
 */

package com.legend.juc.c_006;

public class T implements Runnable {

	private volatile int count = 100;
	
	public synchronized void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void main(String[] args) {
		T t = new T();
		for(int i=0; i<100; i++) {
			new Thread(t, "THREAD" + i).start();
		}
	}
	
}
