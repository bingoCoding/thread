package com.bingo.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 模拟投票场景
 *
 * AtomicIntegerFieldUpdater保证了字段的原子性，
 * 但是，应注意
 * 1.只能修改它可见范围内的变量，因为是通过反射获取的，如果score为private，则不可见
 * 2.变量需申明为volatile类型
 * 3.cas操作会通过对象实例中的偏移量直接赋值，因此，不支持static字段（unsafe.objectFiledOffset()不支持static）
 */
public class AtomicIntegerFiledUpdaterDemo {
    public static class Candidate{
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater
            =AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");
    public static AtomicInteger allScore=new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu=new Candidate();
        Thread[] threads=new Thread[10000];
        for (int i=0;i<10000;i++){
            threads[i]=new Thread(()->{
                if (Math.random()>0.4){
                    scoreUpdater.incrementAndGet(stu);
                    allScore.incrementAndGet();
                }
            });
            threads[i].start();
        }
        for (int i=0;i<10000;i++){
            threads[i].join();
        }
        System.out.println(stu.score+"::"+allScore);
    }
}
