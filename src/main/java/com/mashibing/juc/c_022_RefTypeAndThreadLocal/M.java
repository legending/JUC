package com.mashibing.juc.c_022_RefTypeAndThreadLocal;

public class M {

    //重写这个方法的目的在于监测垃圾回收事件，因为垃圾回收的时候回调用此方法
    //实际上这个方法不需要重写，也不应该重写
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
