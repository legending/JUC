/**
 * volatile �ؼ��֣�ʹһ�������ڶ���̼߳�ɼ� -> ��Ҫ���ɻ���һ����Э������֤����ҪָMESI
 * A B�̶߳��õ�һ��������javaĬ����A�߳��б���һ��copy���������B�߳��޸��˸ñ�������A�߳�δ��֪��
 * ʹ��volatile�ؼ��֣����������̶߳�������������޸�ֵ
 * 
 * ������Ĵ����У�running�Ǵ����ڶ��ڴ��t������
 * ���߳�t1��ʼ���е�ʱ�򣬻��runningֵ���ڴ��ж���t1�̵߳Ĺ������������й�����ֱ��ʹ�����copy��������ÿ�ζ�ȥ
 * ��ȡ���ڴ棬�����������߳��޸�running��ֵ֮��t1�̸߳�֪���������Բ���ֹͣ����
 * 
 * ʹ��volatile������ǿ�������̶߳�ȥ���ڴ��ж�ȡrunning��ֵ
 * 
 * �����Ķ���ƪ���½��и���������
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 * 
 * volatile�����ܱ�֤����̹߳�ͬ�޸�running����ʱ�������Ĳ�һ�����⣬Ҳ����˵volatile�������synchronized
 * ���˵����߳�ͬʱ�޸�running�п��ܳ�������
 *
 * ��һ����˵��һ�����⣺ͨ��synchronized�������Խ���ɼ������⣨ͨ��ǿ��ˢ�¹����ڴ������ڴ��ֵ��ʵ�֣������JMM�й���synchronized�Ĺ涨�йأ�
 * �����https://www.cnblogs.com/xuwenjin/p/9044230.html
 *
 */
package com.mashibing.juc.c_012_Volatile;

import java.util.concurrent.TimeUnit;

public class T01_HelloVolatile {
	/*volatile*/ boolean running = true; //�Ա�һ������volatile������£������������н��������
	void m1() {
		System.out.println("m1 start");
		while(running) {
		}
		System.out.println("m1 end!");
	}

	synchronized boolean getStatus() { //
		return running;
	}

	synchronized void setStatus (boolean flag) {
		System.out.println("setRunning start");
		this.running = flag;
		System.out.println("setRunning end");
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("main start");
		T01_HelloVolatile t = new T01_HelloVolatile();

		//ʵ��һ������volatile���߳̿ɼ���
		//ֻ��running����volatile��m1�Ż�ֹͣ
		/*new Thread(t::m1, "t1").start();
		TimeUnit.SECONDS.sleep(2);
		t.running = false;*/

		//ʵ��������� ��ͬ�������޸�runningֵ������ʹ�߳�ֹͣ
		//��Ϊ��running���޸Ķ�����ͬ�������Բ����ڶ���߳�ͬʱ��д�����⣬����Ҳ�Ͳ��ü�volatile
		new Thread(() -> {
			while(true) {
				//if (!t.running) {
				if (!t.getStatus()) {
					System.out.println("running = false");
					break;
				}
			}
		}).start();
		TimeUnit.SECONDS.sleep(2);
		//t.running = false;
		t.setStatus(false);

		System.out.println("main end");
	}
	
}


