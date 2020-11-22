package com.legend.juc.c_020_Locks;

/*
* ����դ���������Ƶ�
* �ȴ��̴߳չ�ָ����������ִ����һ���̣߳��е���� �ͳ���Ա�ŷ���
* ����������һ���߳���ʼ��û�дﵽָ�������������һֱ�ȴ�
* ע��await�����������߳�
* ʹ�ó�����һ�����ӵĲ�����Ҫ����̲߳���ȥ����ֻ�е������̶߳�ִ����֮�����ִ�к����Ĳ���
* */
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class T07_TestCyclicBarrier {
    public static void main(String[] args) {
        System.out.println("main start");

        //CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("����"));

        /*CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("���ˣ�����");
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
