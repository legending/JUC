package com.legend.juc.c_025_Containers;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class T06_ArrayBlockingQueue {

	static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

	static Random r = new Random();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			strs.put("a" + i);
		}
		
		//strs.put("aaa"); //���˾ͻ�ȴ�����������
		//strs.add("aaa");
		strs.offer("aaa");
		//strs.offer("aaa", 1, TimeUnit.SECONDS);
		
		System.out.println(strs);
	}
}