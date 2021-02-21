package com.mpf.study.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author: MiaoPengfei
 * @date: 2021/1/29 13:49
 * @description:
 */
@Slf4j
public class ThreadUtil {

    private final static ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * 设置超时请求，超时
     *
     * @param call    请求方法
     * @param timeout 超时时间
     * @param <T>     请求类型/返回类型
     * @return 返回结果
     */
    public static <T> T threadFuture(Callable<T> call, long timeout) throws TimeoutException, InterruptedException, ExecutionException {
        try {
            Future<T> future = EXEC.submit(call);
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            log.error("Thread Future TimeOut : {}", timeout);
            throw e;
        }
    }
}
