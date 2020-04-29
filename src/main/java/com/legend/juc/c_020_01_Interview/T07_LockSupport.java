/**
 * 使用Latch（门闩）替代wait notify来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 *
 * LockSupport带有sleep的情况下可以实现该功能，如果不带sleep同样可以实现，t1，t2调换执行顺序同样可以实现
 * LockSupport自带可见性 => V
 */
package com.legend.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class T07_LockSupport {

	List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		T07_LockSupport c = new T07_LockSupport();

		Thread t2 = new Thread(() -> {
			if (c.size() != 5) {
				LockSupport.park();
			}
		}, "t2");

		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					System.out.println("5 bingo");
					LockSupport.unpark(t2);
				}

				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}
		}, "t1");

		t1.start();
		/*try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		t2.start();
	}
}
