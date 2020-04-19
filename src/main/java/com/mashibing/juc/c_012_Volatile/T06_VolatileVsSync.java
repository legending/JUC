/**
 * 对比上一个程序，可以用synchronized解决，synchronized可以保证可见性和原子性，volatile只能保证可见性
 * 另外注意锁细化与粗化问题，所比较少的情况下，竟可能锁最小的范围，如果一个代码块里有多个锁，要试着考虑锁粗化的问题
 *
 */
package com.mashibing.juc.c_012_Volatile;

import java.util.ArrayList;
import java.util.List;


public class T06_VolatileVsSync {
	/*volatile*/ int count = 0;

	/*synchronized */void m() {
		for (int i = 0; i < 10000; i++)
		    synchronized (this) {
                count++;
            }
	}

	public static void main(String[] args) {
		T06_VolatileVsSync t = new T06_VolatileVsSync();

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(t::m, "thread-" + i));
		}

		threads.forEach((o) -> o.start());

		threads.forEach((o) -> {
			try {
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		System.out.println(t.count);

	}

}
