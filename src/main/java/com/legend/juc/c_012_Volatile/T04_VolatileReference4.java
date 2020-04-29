/**
 * 测试volatile修饰引用类型
 * 测试方法先开启一个线程开启一个while(flag)循环，然后再开启一个线程去修flag去终止第一个线程
 *
 * 1. volatile修饰类 -> 加了volatile才监测到
 * 2. volatile修饰数组 -> 加不加volatile都可以检测到
 *
 * 以上实验只能证明volatile对于引用类型有可见性作用，但至于隔多久可见是不确定的
 * T02_WithVolatile.java
 */
package com.legend.juc.c_012_Volatile;

public class T04_VolatileReference4 {
    volatile ReferenceClass referenceClass  = new ReferenceClass();

    int arrLen = 100000;
    volatile int[] arr = new int[arrLen];

    void testReferenceClass () {
        new Thread(() -> {
            while (referenceClass.flag) {}
            System.out.println("reference class detected");
        }).start();

        new Thread(() -> {
            referenceClass.flag = false;
        }).start();
    }

    void testReferenceArray () {
        new Thread(() -> {
            while (arr[arrLen-1]==0) {}
            System.out.println("reference array detected");
        }).start();

        new Thread(() -> {
            arr[arrLen-1] = 1;
        }).start();
    }

    public static void main(String[] args) {
        T04_VolatileReference4 t = new T04_VolatileReference4();
        t.testReferenceClass();
        t.testReferenceArray();
    }
}

class ReferenceClass {
    boolean flag = true;
}