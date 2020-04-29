package com.legend.juc.c_020;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
* 读写锁：读的时候可以多个线程同时读（多个线程可以同时持有读锁，所以读锁是一个共享锁），写的时候只能一个线程写（只能有一个线层持有写锁）
* 所以在读写操作分离的时候，读写锁效率比ReentrantLock效率要高
* */
public class T10_TestReadWriteLock {
    static Lock lock = new ReentrantLock();//ReentrantLock是互斥锁（排他锁），只能被一个线程持有
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(100);
            int i = value;
            System.out.println("read over!");
            //模拟读取操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(100);
            value = v;
            System.out.println("write over!");
            //模拟写操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        Runnable readR = ()-> read(lock);
        //Runnable readR = ()-> read(readLock);

        Runnable writeR = ()->write(lock, new Random().nextInt());
        //Runnable writeR = ()->write(writeLock, new Random().nextInt());

        for(int i=0; i<18; i++) new Thread(readR).start();
        for(int i=0; i<2; i++) new Thread(writeR).start();
    }
}
