package com.legend.juc.c_000;

/*
* 创建线程的两种方式
* 1. 继承Thread(实际上Thread也是Runnable的一个实现类)
* 2. 实现Runnable接口
* 3. lambda表达式(因为Runnable是一个函数式接口，所以可以用一个lambda表达式创建匿名内部类)
* */

public class T02_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(()->{
            System.out.println("Hello Lambda!");
        }).start();
    }

}

//请你告诉我启动线程的三种方式 1：Thread 2: Runnable 3:Executors.newCachedThread
