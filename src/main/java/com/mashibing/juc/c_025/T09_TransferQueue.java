package com.mashibing.juc.c_025;

/*
* 线程put完之后，会等在那里，直到消息被取走，线程才会继续执行
* 应用：一件事儿分两部分，必须等待第一部分完成的结果，再去执行第二部分，如网购东西=订单已下+货物已发
* */

import java.util.concurrent.LinkedTransferQueue;

public class T09_TransferQueue {
	public static void main(String[] args) throws InterruptedException {
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
		
		//strs.transfer("aaa"); //把消息放入队列后阻塞在这里，直到有人取走才解除阻塞
		
		strs.put("aaa"); //把消息放入队列后立即返回

		System.out.println("hello");

		new Thread(() -> {
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

	}
}
