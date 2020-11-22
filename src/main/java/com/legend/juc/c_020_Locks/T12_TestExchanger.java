package com.legend.juc.c_020_Locks;

import java.util.concurrent.Exchanger;

/*
* Exchanger: 交换数据，exchange方法是阻塞的
* exchange只能两两交换，如果来了第三个线程，那它只能阻塞，等待第四个线程来了之后再交换数据
* exchange只能是字符串
* 引用场景：两个线程协作干一件事情，然后中途需要交换数据然后继续执行，如游戏中交换设备
* */

public class T12_TestExchanger {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t1").start();


        new Thread(()->{
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + s);

        }, "t2").start();


    }
}
