package com.legend.juc.c_025_Containers;

/*
* 容量为0，不能往里面装东西，只有当线程来take时才会装东西
* 用途：两个线程直接传递数据->线程相互调度
* */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T08_SynchronusQueue { //容量为0
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> strs = new SynchronousQueue<>();
		
		new Thread(()->{
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		strs.put("aaa"); //阻塞等待消费者消费
		//strs.put("bbb");
		//strs.add("aaa");
		System.out.println(strs.size());
	}
}
