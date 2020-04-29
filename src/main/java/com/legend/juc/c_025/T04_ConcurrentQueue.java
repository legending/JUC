package com.legend.juc.c_025;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class T04_ConcurrentQueue {
	public static void main(String[] args) {
		Queue<String> strs = new ConcurrentLinkedQueue<>();
		
		for(int i=0; i<10; i++) {
			strs.offer("a" + i);  //������add����list��add����֮����׳��쳣����offer��ֱ�ӷ�������Ƿ�ɹ��Ĳ���ֵ
		}
		
		System.out.println(strs);
		
		System.out.println(strs.size());
		
		System.out.println(strs.poll());//getȻ��remove
		System.out.println(strs.size());
		
		System.out.println(strs.peek());//peek��ȥget������remove
		System.out.println(strs.size());
		
		//˫�˶���Deque
	}
}
