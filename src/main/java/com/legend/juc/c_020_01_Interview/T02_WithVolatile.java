/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * 
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ����
 * ���ң������if �� break֮�䱻����̴߳�ϣ��õ��Ľ��Ҳ����ȷ��
 * ����ô���أ�
 *
 * ����һ��������˼�����󣬵�sleep 1���ʱ��lists��size�ᱻͬ�����߳̿ɼ�ֻ�ܱ�֤���ñ���ɼ����������ܱ�֤���ö����ڵ����Կɼ���
 * ���Կ����ƶϣ������㹻ʱ�䣬volatile���ε����ö�Ӧ�Ķ��������Ҳ���ÿɼ� --> ���ڲ�ȷ���ԣ��೤ʱ����ܱ�֤�� --> ��Ҫ��volatile��������������
 *
 * ʹ��sleep����֤�ɼ��ԣ����ڲ�ȷ���� => X
 */
package com.legend.juc.c_020_01_Interview;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class T02_WithVolatile {

	//���volatile��ʹt2�ܹ��õ�֪ͨ
	//volatile List lists = new LinkedList();
	volatile List lists = Collections.synchronizedList(new LinkedList<>());

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {

		T02_WithVolatile c = new T02_WithVolatile();
		new Thread(() -> {
			for(int i=0; i<10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				//��Ϊsleep��ʱ��ܳ������д֮�������ͻ�Ŀ����Բ���ȥ��sleep֮��������Ȼ�޷����
				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}
		}, "t1").start();
		
		new Thread(() -> {
			while(true) {
				if(c.size() == 5) {
					System.out.println("5 bingo");
					break;
				}
			}
		}, "t2").start();
	}
}
