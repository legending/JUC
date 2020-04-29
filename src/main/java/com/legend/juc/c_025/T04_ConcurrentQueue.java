package com.legend.juc.c_025;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T04_ConcurrentQueue {
	public static void main(String[] args) {
		Queue<String> strs = new ConcurrentLinkedQueue<>();
		
		for(int i=0; i<10; i++) {
			strs.offer("a" + i);  //类似于add，但list的add满了之后会抛出异常，但offer会直接返回添加是否成功的布尔值
		}
		
		System.out.println(strs);
		
		System.out.println(strs.size());
		
		System.out.println(strs.poll());//get然后remove
		System.out.println(strs.size());
		
		System.out.println(strs.peek());//peek会去get但不会remove
		System.out.println(strs.size());
		
		//双端队列Deque
	}
}
