package com.mashibing.juc.c_018_01_Unsafe;

/*
* Unsafe类让程序员拥有了像C语言一样直接操作内存的能力
* allocateMemory, putXxx, freeMemory, pageSize
* */
//import sun.misc.*;

import sun.misc.Unsafe;

public class HelloUnsafe {
    static class M {
        private M() {}

        int i =0;
    }

   public static void main(String[] args) throws InstantiationException {
        Unsafe unsafe = Unsafe.getUnsafe();
        M m = (M)unsafe.allocateInstance(M.class);
        m.i = 9;
        System.out.println(m.i);
    }
}


