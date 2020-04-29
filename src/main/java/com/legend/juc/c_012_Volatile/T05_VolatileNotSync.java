/**
 * volatile�����ܱ�֤����̹߳�ͬ�޸�running����ʱ�������Ĳ�һ�����⣬Ҳ����˵volatile�������synchronized
 * ��������ĳ��򣬲��������
 *
 */
package com.legend.juc.c_012_Volatile;

import java.util.ArrayList;
import java.util.List;

public class T05_VolatileNotSync {
	volatile int count = 0;
	void m() {
		for(int i=0; i<10000; i++) count++;
	}
	
	public static void main(String[] args) {
		T05_VolatileNotSync t = new T05_VolatileNotSync();
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i=0; i<10; i++) {
			threads.add(new Thread(t::m, "thread-"+i));
		}
		
		threads.forEach((o)->o.start());
		
		threads.forEach((o)->{
			try {
				o.join(); //�����join�Ǹ������̵߳ȴ�ÿһ���߳̽������ټ���ִ�У������ǰ�10�����̴߳��л�Ϊһ���̣߳�ע�����join������
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(t.count);
		
		
	}
	
}


