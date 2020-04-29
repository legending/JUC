package com.legend.juc.c_026_01_ThreadPool;

/*
* 保证任务是顺序执行的
*
* 那为什么不用单线程去做？
* 不止一个线程那么简单，需要对线程进行管理，要有阻塞队列
* */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T07_SingleThreadPool {
	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) {
			final int j = i;
			service.execute(()->{
				
				System.out.println(j + " " + Thread.currentThread().getName());
			});
		}
			
	}
}
