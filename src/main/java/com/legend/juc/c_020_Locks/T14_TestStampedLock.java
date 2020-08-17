package com.legend.juc.c_020_Locks;

import java.util.concurrent.locks.StampedLock;

/*
* StampedLock是Java 8新增的一个读写锁，它是对ReentrantReadWriteLock的升级版
* StampedLock是不可重入锁
* https://www.liaoxuefeng.com/wiki/1252599548343744/1309138673991714
*
* 和ReadWriteLock相比，写入的加锁是完全一样的，不同的是读取。注意到首先我们通过tryOptimisticRead()获取一个乐观读锁，并返回版本号。接着进行读取，读取完成后，我们通过validate()去验证版本号，如果在读取过程中没有写入，版本号不变，验证成功，我们就可以放心地继续后续操作。如果在读取过程中有写入，版本号会发生变化，验证将失败。在失败的时候，我们再通过获取悲观读锁再次读取。由于写入的概率不高，程序在绝大部分情况下可以通过乐观读锁获取数据，极少数情况下使用悲观读锁获取数据。
* 可见，StampedLock把读锁细分为乐观读和悲观读，能进一步提升并发效率。但这也是有代价的：一是代码更加复杂，二是StampedLock是不可重入锁，不能在一个线程中反复获取同一个锁。
* StampedLock还提供了更复杂的将悲观读锁升级为写锁的功能，它主要使用在if-then-update的场景：即先读，如果读的数据满足条件，就返回，如果读的数据不满足条件，再尝试写。
* */

public class T14_TestStampedLock {
    // 成员变量
    private double x, y;

    // 锁实例
    private final StampedLock sl = new StampedLock();

    // 排它锁-写锁（writeLock）
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 一个只读方法
    // 其中存在乐观读锁到悲观读锁的转换
    double distanceFromOrigin() {

        // 尝试获取乐观读锁
        long stamp = sl.tryOptimisticRead();
        // 将全部变量拷贝到方法体栈内
        double currentX = x, currentY = y;
        // 检查在获取到读锁stamp后，锁有没被其他写线程抢占
        if (!sl.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁（悲观获取）
            stamp = sl.readLock();
            try {
                // 将全部变量拷贝到方法体栈内
                currentX = x;
                currentY = y;
            } finally {
                // 释放共享读锁
                sl.unlockRead(stamp);
            }
        }
        // 返回计算结果
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    // 获取读锁，并尝试转换为写锁
    void moveIfAtOrigin(double newX, double newY) {
        long stamp = sl.tryOptimisticRead();
        try {
            // 如果当前点在原点则移动
            while (x == 0.0 && y == 0.0) {
                // 尝试将获取的读锁升级为写锁
                long ws = sl.tryConvertToWriteLock(stamp);
                // 升级成功，则更新stamp，并设置坐标值，然后退出循环
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 读锁升级写锁失败则释放读锁，显示获取独占写锁，然后循环重试
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }
}