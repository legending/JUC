package com.mashibing.juc.c_020;

/*
* 单个栅栏，人满推倒
* 等待线程凑够指定数量后再执行这一批线程，有点儿像 客车满员才发车
* 所以如果最后一次线程数始终没有达到指定数量，程序会一直等待
* 注意await不会阻塞主线程
* 使用场景：一个复杂的操作需要多个线程并发去做，只有当所有线程都执行完之后才能执行后续的操作
* */
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class T07_TestCyclicBarrier {
    public static void main(String[] args) {
        System.out.println("main start");

        //CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人"));

        /*CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("满人，发车");
            }
        });*/

        for(int i=0; i<99; i++) {
                new Thread(()->{
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }).start();
            
        }
        System.out.println("main end");
    }
}
