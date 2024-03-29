package com.xiao.nettydemo.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * 3.3 Future & Promise
 *
 * setSuccess   设置成功结果
 * setFailure   设置失败结果
 * DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);
 */

@Slf4j
public class TestNettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. 准备 EventLoop 对象
        EventLoop eventLoop = new NioEventLoopGroup().next();

        // 2. 可以主动创建 Promise, 结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(()->{
            // 3. 任意一个线程执行计算, 计算完毕后向 promise 填充结果
            log.debug("开始计算");
            try {
                int i = 1 / 0;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
            promise.setSuccess(80);
        }).start();


        // 4. 接收结果
        log.debug("等待结果");
        log.debug("结果是:{}",promise.get());

    }

}
