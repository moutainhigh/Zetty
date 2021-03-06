package com.zrzhen.zetty.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author chenanlian
 * 线程池工具类
 */
public class ExecutorUtil {

    private static final Logger log = LoggerFactory.getLogger(ExecutorUtil.class);


    public static ThreadPoolExecutor channelExcutor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() + 1,
            Runtime.getRuntime().availableProcessors() * 2 + 1,
            1,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue(1000),
            new ThreadFactory() {
                private AtomicInteger count = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    String threadName = "channelExcutor-" + count.addAndGet(1);
                    t.setName(threadName);
                    return t;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.warn("触发日志线程池拒绝策略");
                    try {
                        executor.getQueue().put(r);
                        /*
                         * 如果任务量不大，可以用无界队列，如果任务量非常大，要用有界队列，防止OOM
                         * 如果任务量很大，还要求每个任务都处理成功，要对提交的任务进行阻塞提交，重写拒绝机制，改为阻塞提交。保证不抛弃一个任务
                         */
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }

    );

    public static ThreadPoolExecutor processorExcutor = new ThreadPoolExecutor(
            10,
            200,
            1,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue(100),
            new ThreadFactory() {
                private AtomicInteger count = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    String threadName = "processorExcutor-" + count.addAndGet(1);
                    t.setName(threadName);
                    return t;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.warn("触发日志线程池拒绝策略");
                    try {
                        executor.getQueue().put(r);
                        /*
                         * 如果任务量不大，可以用无界队列，如果任务量非常大，要用有界队列，防止OOM
                         * 如果任务量很大，还要求每个任务都处理成功，要对提交的任务进行阻塞提交，重写拒绝机制，改为阻塞提交。保证不抛弃一个任务
                         */
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }

    );

}
