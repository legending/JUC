/**
 * ��N�Ż�Ʊ��ÿ��Ʊ����һ�����
 * ͬʱ��10�����ڶ�����Ʊ
 * ��дһ��ģ�����
 * 
 * ��������ĳ�����ܻ������Щ���⣿
 * �ظ����ۣ��������ۣ�
 * 
 * 
 * @author ��ʿ��
 */
package com.legend.juc.c_024_FromVectorToQueue;

import java.util.ArrayList;
import java.util.List;

public class TicketSeller1 {
	static List<String> tickets = new ArrayList<>();
	
	static {
		for(int i=0; i<10000; i++) tickets.add("Ʊ��ţ�" + i);
	}
	
	
	
	public static void main(String[] args) {
		for(int i=0; i<10; i++) {
			new Thread(()->{
				while(tickets.size() > 0) { //�˴����ڲ�������
					System.out.println("������--" + tickets.remove(0));//remove����ԭ�Ӳ���
				}
			}).start();
		}
	}
}
