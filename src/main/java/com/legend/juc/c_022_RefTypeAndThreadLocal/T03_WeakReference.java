/**
 * 弱引用遭到gc就会回收
 *
 * 作用：一般用在容器里
 *
 * 课后作业
 * WeakHashMap是干嘛的？
 */
package com.legend.juc.c_022_RefTypeAndThreadLocal;

import java.lang.ref.WeakReference;

public class T03_WeakReference {
    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        //WeakReference的典型应用ThreadLocal
        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.get();
        tl.remove();//如果ThreadLocal里面的值不用了要清空，否则会造成内存泄漏

    }
}