package com.qfedu.quartz;


import java.util.Timer;
import java.util.TimerTask;

/**
 *@Author feri
 *@Date Created in 2018/7/31 10:10
 */
public class Trimer_Main {
    public static void main(String[] args) {
        Timer mytim= new Timer("mycount");
        //三个参数：1、定时执行的内容 2、延迟时间 3、间隔时间   单位都是毫秒
        mytim.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("老师说：重要事情说三遍！！！");
                }
        },2000,1000);

    }
}
