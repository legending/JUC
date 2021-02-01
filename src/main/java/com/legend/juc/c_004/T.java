/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * synchronized(obj)只对当前对象有效
 * synchronized(*.class)代码块的作用其实和synchronized static方法作用一样，Class锁对类的所有对象实例起作用。
 */

package com.legend.juc.c_004;

public class T {

	private static int count = 10;
	
	public synchronized static void m() { //这里等同于synchronized(FineCoarseLock.class)
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void mm() {
		synchronized(T.class) { //考虑一下这里写synchronized(this)是否可以？
			count --;
		}
	}

}
