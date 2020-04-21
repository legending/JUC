package com.mashibing.juc.c_025;

/*
* �߳�put��֮�󣬻�������ֱ����Ϣ��ȡ�ߣ��̲߳Ż����ִ��
* Ӧ�ã�һ���¶��������֣�����ȴ���һ������ɵĽ������ȥִ�еڶ����֣�����������=��������+�����ѷ�
* */

import java.util.concurrent.LinkedTransferQueue;

public class T09_TransferQueue {
	public static void main(String[] args) throws InterruptedException {
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
		
		//strs.transfer("aaa"); //����Ϣ������к����������ֱ������ȡ�߲Ž������
		
		strs.put("aaa"); //����Ϣ������к���������

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
