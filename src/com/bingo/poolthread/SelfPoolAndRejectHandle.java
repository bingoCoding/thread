package com.bingo.poolthread;

import java.util.concurrent.*;

/**
 * 自定义线程池和拒绝策略
 *
 *
 */
public class SelfPoolAndRejectHandle {

    public static void main(String[] args) {
        ExecutorService exec=new ThreadPoolExecutor(5,
                5,
                0L, //当线程数量超过corePoolSize时，空闲线程存活时间
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(10),
                //Executors.defaultThreadFactory(),//任务队列
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t=new Thread(r);
                        t.setDaemon(true);
                        System.out.println("create "+t);
                        return t;
                    }
                },
                new RejectedExecutionHandler() {//拒绝策略
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString()+" is discard");
                    }
                }){ //扩展   ThreadPoolExecutor的内部类Worker
                        @Override
                        protected void beforeExecute(Thread t, Runnable r) {
                            System.out.println("准备执行 "+r);
                        }

                        @Override
                        protected void afterExecute(Runnable r, Throwable t) {
                            System.out.println(r+"执行完成 ");
                        }

                        @Override
                        protected void terminated() {
                            System.out.println("线程池退出 ");
                        }
                    };
    }
}
