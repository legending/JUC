/**
 * ReentrantLock可以指定为公平锁，哪个线程先来哪个线程先获得锁
 * synchronized只有不公平锁，谁抢到是谁的
 *
 */
package com.legend.juc.c_020_Locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class T05_ReentrantLock5 extends Thread {
		
	private static ReentrantLock lock=new ReentrantLock(true); //参数为true表示为公平锁，请对比输出结果
	//private static ReentrantLock lock=new ReentrantLock(); //无参，默认为非公平锁
    public void run() {
        for(int i=0; i<100; i++) {
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally{
                lock.unlock();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("main start");
        T05_ReentrantLock5 rl=new T05_ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
        System.out.println("main end");
    }
}
