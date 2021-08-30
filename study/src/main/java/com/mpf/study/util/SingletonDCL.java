package com.mpf.study.util;

/**
 * @author: MiaoPengfei
 * @date: 2021/8/30 18:10
 * @description: 单例 双重校验锁
 * @since: 1.0.20
 */
public class SingletonDCL {
    // volatile作用，防止指令重排导致双重非空判断失效
    private volatile static SingletonDCL singleton;

    private SingletonDCL() {
    }

    public static SingletonDCL getInstance() {
        if (singleton == null) {
            synchronized (SingletonDCL.class) {
                if (singleton == null) {
                    singleton = new SingletonDCL();
                }
            }
        }
        return singleton;
    }
}
