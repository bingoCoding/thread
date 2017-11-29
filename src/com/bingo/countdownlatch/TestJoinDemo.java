package com.bingo.countdownlatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestJoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Map<String,Integer> map=new ConcurrentHashMap<>();
        String abc="123ashfk9430;";
        Thread t1=new Thread(()->{
            try {
                for(int i=0;i<abc.length();i++){
                    char c=abc.charAt(i);
                    if(c>='0'&&c<='9'){
                        map.put("num",map.get("num")==null?1:map.get("num")+1);
                    }
                }
                System.out.println("num 处理完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t1.join();
        Thread t2 =new Thread(()->{
            try {
                for(int i=0;i<abc.length();i++){
                    char c=abc.charAt(i);
                    if(c>='a'&&c<='z'||c>='A'&&c<='Z'){
                        map.put("en",map.get("en")==null?1:map.get("en")+1);
                    }
                }
                System.out.println("en 处理完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();
        t2.join();
        System.out.println("num="+map.get("num"));
        System.out.println("en="+map.get("en"));
        System.out.println("over!");
    }
}
