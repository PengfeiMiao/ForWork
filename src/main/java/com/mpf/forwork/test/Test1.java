package com.mpf.forwork.test;

import java.io.PrintStream;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/6/1 14:50
 */
public class Test1 {

    public static void main(String[] args) {
        int a=10;
//        int b=10;
//        method(a,b);
        System.out.println(2*a);
    }
    static void method(int a,int b)  {
        PrintStream myStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                if("a".equals(x.substring(0,1))) {
                    super.println("a=100");
                }
                if("b".equals(x.substring(0,1))) {
                    super.println("b=200");
                }
            }
        };
        System.setOut(myStream);
    }
}
