package com.bingo.producterAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producter implements Runnable {

    private volatile boolean isrun=true;
    private BlockingQueue<PCDate> queue;
    private static AtomicInteger count=new AtomicInteger();
    private final static int SLEEPTIME=1000;

    public Producter(BlockingQueue<PCDate> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        PCDate data=null;
        Random r=new Random();

        System.out.println("product is starting id="+Thread.currentThread().getId());
        try {
            while (isrun){
                Thread.sleep(r.nextInt(SLEEPTIME));
                data=new PCDate(count.incrementAndGet());
                System.out.println(data+"is put to queue");
                if (!queue.offer(data,2, TimeUnit.SECONDS)){//提交数据到缓冲区
                    System.out.println("fail to put data "+data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.interrupted();
        }
    }

    public void stop(){
        this.isrun=false;
    }
}
