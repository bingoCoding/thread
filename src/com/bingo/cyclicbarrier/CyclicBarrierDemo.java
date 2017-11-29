package com.bingo.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 * countdownlatch 的增强
 * 也是倒计数执行的一种
 * 当达到一定的线程数后 就会执行一次，只要达到规定的线程数就会执行，循环等待执行
 *
 * eg: 士兵执行任务的例子。
 * 先让10个士兵集合，等待十个士兵集合完毕。让士兵去做任务，等待10个士兵都完成任务，任务完成。
 *
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{
        private final CyclicBarrier cb;
        private String soldier;
        public Soldier(CyclicBarrier cb,String soldierName) {
            this.cb = cb;
            this.soldier=soldierName;
        }

        @Override
        public void run() {
            try {
                cb.await();//等待士兵集合
                Thread.sleep(new Random().nextInt(10)*1000);//模拟士兵dowork
                System.out.println(soldier+" 任务完成");
                cb.await();//等待士兵完成任务
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int num;
        public BarrierRun(boolean flag,int num){
            this.flag=flag;
            this.num=num;
        }

        @Override
        public void run() {
            if(flag){
                System.out.println("士兵 "+num+" 个，任务完成");//士兵都完成任务后打印
            }else {
                System.out.println("士兵 "+num+" 个，集合完毕");//士兵集合完毕后打印
                flag=true;
            }
        }
    }

    public static void main(String[] args) {
        final int num=10;
        Thread[] soldiers=new Thread[num];
        boolean flag=false;
        CyclicBarrier cb=new CyclicBarrier(num,new BarrierRun(flag,num));
        System.out.println("集合");
        for (int i=0;i<num;++i){
            System.out.println("士兵 "+i+" 报道");
            soldiers[i]=new Thread(new Soldier(cb,i+""));
            soldiers[i].start();
        }
    }

}
