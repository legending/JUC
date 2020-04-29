package com.legend.juc.c_022_RefTypeAndThreadLocal;

/*
* java中的四种引用：强、软、弱、虚引用
* 我们正常使用的是强引用
* */

import java.io.IOException;

public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        //m = null;
        System.gc(); //DisableExplicitGC

        System.in.read();
    }
}
