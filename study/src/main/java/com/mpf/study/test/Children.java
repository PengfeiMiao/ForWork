package com.mpf.study.test;

/**
 * @author: MiaoPengfei
 * @date: 2021/8/30 18:26
 * @description:
 * @since: 1.0.20
 */
public class Children extends Parent {

    Children() {
//        super();
        System.out.println("C-构造块");
    }

    {
        System.out.println("C-非静态块");
    }

    static {
        System.out.println("C-静态块");
    }

    public static void main(String[] args) {
        new Children();
    }
}

class Parent {

    {
        System.out.println("P-非静态块");
    }

    Parent(){
        System.out.println("P-构造块");
    }

    static {
        System.out.println("P-静态块");
    }
}
