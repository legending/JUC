package com.mashibing.juc.c_021_02_AQS;

/*
 * 读源码
 * debug lock()方法
 * 由于java的多态特性（继承，实现->子类具体实现与父类的默认实现不一样）静态跟踪到的代码跟debug跟踪到的代码可能不一致
 *
 * 画时序图来辅助理解 ->
 * */

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    private static volatile int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        //synchronized (TestReentrantLock.class) {
            i++;
        //}

        lock.unlock();

        //synchronized 程序员的丽春院 JUC
    }
}
