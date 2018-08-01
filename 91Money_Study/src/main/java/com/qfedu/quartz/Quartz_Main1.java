package com.qfedu.quartz;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

/**
 *@Author feri
 *@Date Created in 2018/7/31 09:44
 */
public class Quartz_Main1 {
    public static void main(String[] args) throws Exception {
        //1、创建调度器
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        //2、创建触发条件  什么时候
        //new SimpleTriggerImpl()
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("mytrigger","trigroup1").
                startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(3).withRepeatCount(3)).build();
        //3、创建作业详情  干什么
        JobDetail jobDetail=JobBuilder.newJob(MyJobDetail1.class).
                withIdentity("myjob1","jobgroup1").build();
        //4、设置调度器的触发条件和对应的作业
        scheduler.scheduleJob(jobDetail,trigger);
        //5、启动作业
        scheduler.start();//启动
//        scheduler.pauseJob();//暂停
//        scheduler.resumeJob();//继续
//        scheduler.shutdown();//关闭



    }
}
