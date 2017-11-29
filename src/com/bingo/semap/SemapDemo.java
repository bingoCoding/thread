package com.bingo.semap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量：同时允许几个线程同时访问同一个资源
 *
 */
public class SemapDemo implements Runnable {

    Semaphore semaphore=new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();//获取访问许可
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+" work ");
            semaphore.release();//释放访问许可
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(20);
        SemapDemo semapDemo=new SemapDemo();
        for (int i=0;i<20;i++){
            service.execute(semapDemo);
        }
    }
}
