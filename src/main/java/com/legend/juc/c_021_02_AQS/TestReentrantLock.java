package com.legend.juc.c_021_02_AQS;

/*
 * 读源码
 * 先读骨架
 * debug lock()方法
 * 由于java的多态特性（继承，实现->子类具体实现与父类的默认实现不一样）静态跟踪到的代码跟debug跟踪到的代码可能不一致
 *
 * 画时序图来辅助理解 -> 有道图，PlantUML
 *
 * AQS的底层=CAS+volatile
 * 找一个入口点，如：ReentrantLock的lock方法
 * 线性去读 -> 画调用图：时序图，或者用idea自带的diagram
 * 理清继承关系 -> 画继承图，如：NonfairSync -> Sync -> AQS
 *
 * AQS核心点：往线程队列（双向链表）尾部添加新的线程节点
 *
 * CountDownLatch 课后作业
 * ConcurrentHashMap
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

    }
}
