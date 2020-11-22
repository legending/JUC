/**
 * ReentrantLock������ָ��Ϊ��ƽ�����Ǹ��߳�������ִ���ĸ�
 * synchronizedֻ�в���ƽ����˭������˭��
 *
 */
package com.legend.juc.c_020_Locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class T05_ReentrantLock5 extends Thread {
		
	private static ReentrantLock lock=new ReentrantLock(true); //����Ϊtrue��ʾΪ��ƽ������Ա�������
    public void run() {
        for(int i=0; i<100; i++) {
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+"�����");
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
