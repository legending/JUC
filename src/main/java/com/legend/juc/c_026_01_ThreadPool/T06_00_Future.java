/**
 * Callable��һ������ִ�н����Ҫ�洢��Future��
 * ��TaskFuture����Callable,Ҳ��Future,�����Ǹ����õ�һ�ַ�ʽ
 */
package com.legend.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T06_00_Future {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		FutureTask<Integer> task = new FutureTask<>(()->{
			TimeUnit.MILLISECONDS.sleep(500);
			return 1000;
		}); //new Callable () { Integer call();}
		
		new Thread(task).start();
		
		System.out.println(task.get()); //����


	}
}
