/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */
package com.legend.juc.c_012_Volatile;

public class T03_VolatileReference2 {

    private static class Data {
        int a = 0;
        int b = 0;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    volatile static Data data;
    volatile static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread writer = new Thread(()->{
            for (int i = 0; i < 100000; i++) {
                data = new Data(i, i);
                count++;
            }
        });

        Thread reader = new Thread(()->{
            while (count<1) {} //即使保证了data!=null

            int x = data.a;
            int y = data.b;
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
