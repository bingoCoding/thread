package com.bingo.countdownlatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class TestDemo  {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cdl =new CountDownLatch(2);
        Semaphore semaphore=new Semaphore(2);
        Map<String,Integer> map=new ConcurrentHashMap<>();
        String abc="123ashfk9430;";
        new Thread(()->{
            try {
                //semaphore.acquire();
                for(int i=0;i<abc.length();i++){
                    char c=abc.charAt(i);
                    if(c>='0'&&c<='9'){
                        map.put("num",map.get("num")==null?1:map.get("num")+1);
                    }
                }
                cdl.countDown();
                System.out.println("num 处理完成");
                //semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                //semaphore.acquire();
                for(int i=0;i<abc.length();i++){
                    char c=abc.charAt(i);
                    if(c>='a'&&c<='z'||c>='A'&&c<='Z'){
                        map.put("en",map.get("en")==null?1:map.get("en")+1);
                    }
                }
                cdl.countDown();
                System.out.println("en 处理完成");
                //semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        cdl.await();
        System.out.println("num="+map.get("num"));
        System.out.println("en="+map.get("en"));
        System.out.println("over!");
    }
}
