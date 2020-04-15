/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 * 解决办法，使用AtomicReference
 */
package com.mashibing.juc.c_012_Volatile;

import java.util.concurrent.atomic.AtomicReference;

public class T03_VolatileReference3 {

    private static class Data {
        int a = 0;
        int b = 0;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    volatile static AtomicReference<Data> data = new AtomicReference<>();
    volatile static int count = 0;//用于确认data已完成首次初始化

    public static void main(String[] args) throws InterruptedException {
        Thread writer = new Thread(()->{
            for (int i = 0; i < 100000; i++) {
                data.compareAndSet(null, new Data(i, i));
                count++;
            }
        });

        Thread reader = new Thread(()->{
            while (count<1) {} //确保data!=null

            int x = data.get().a;
            int y = data.get().b;
            if(x != y) {
                System.out.printf("a = %s, b=%s%n", x, y);
            }
        });

        writer.start();
        reader.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
