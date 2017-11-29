package com.bingo.producterAndConsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<PCDate> queue;
    private final static int SLEEPTIME=1000;

    public Consumer(BlockingQueue<PCDate> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        System.out.println("consumer is start id="+Thread.currentThread().getId());

        Random r=new Random();
        try {
            while (true){
                PCDate data=queue.take();
                if (data!=null){
                    int re=data.getIntData()*data.getIntData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",data.getIntData(),data.getIntData(),re));
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Thread.interrupted();
        }
    }
}
