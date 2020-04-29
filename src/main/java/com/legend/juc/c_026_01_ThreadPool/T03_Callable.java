/**
 * 认识Callable，对Runnable进行了扩展
 * 对Callable的调用，可以有返回值（Runnable的run方法没有返回值）
 * commit方法：将任务直接人给线程池
 */
package com.legend.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> c = new Callable() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(c); //异步（主线程继续运行，不收影响）

        System.out.println(future.get());//阻塞

        service.shutdown();
    }

}
