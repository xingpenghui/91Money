package com.qfedu.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

import java.util.GregorianCalendar;


/**
 *@Author feri
 *@Date Created in 2018/7/31 10:16
 */
public class Quartz_Schedule1 {
    public static void main(String[] args) throws Exception {
        //calendar();
        cron1();
    }
    //间隔时间
    private static void time()throws Exception{
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();

        JobDetail jobDetail=JobBuilder.newJob(MyJobDetail1.class).
                withIdentity("myjob1","jobgroup1").build();
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("mytrigger","trigroup1").
                startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10,4)).build();
        scheduler.scheduleJob(jobDetail,trigger);
        //5、启动作业
        scheduler.start();//启动
        //时间范围
        //DailyTimeIntervalScheduleBuilder
    }
    //日历  间隔 年月日、时分秒
    private static void calendar() throws Exception{
        //Calendar
        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        JobDetail jobDetail=JobBuilder.newJob(MyJobDetail1.class).
                withIdentity("myjob1","jobgroup1").build();
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("mytrigger","trigroup1").
                startNow().withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInDays(1)
        ).build();
        scheduler.scheduleJob(jobDetail,trigger);
        //5、启动作业
        scheduler.start();//启动
    }
    //cron
    private static void cron() throws Exception{

        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
//        Calendar calendar=Calendar.;
//        calendar.add(Calendar.DAY_OF_MONTH,-2);
//        AnnualCalendar holidays = new AnnualCalendar();
//        Calendar fourthOfJuly = (Calendar) new GregorianCalendar(2018, 8, 30);
//        holidays.setDayExcluded(fourthOfJuly, true);
//        scheduler.addCalendar("c1",calendar,true,true);
        JobDetail jobDetail=JobBuilder.newJob(MyJobDetail1.class).
                withIdentity("myjob1","jobgroup1").build();
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("mytrigger","trigroup1").
                startNow().withSchedule(
                        CronScheduleBuilder.cronSchedule("0/5 * * ? * TUE,WED ")
        ).build();
        scheduler.scheduleJob(jobDetail,trigger);
        //5、启动作业
        scheduler.start();//启动
    }
    //cron
    private static void cron1() throws Exception{

        Scheduler scheduler=StdSchedulerFactory.getDefaultScheduler();
        //scheduler.addCalendar();
        JobDetail jobDetail=JobBuilder.newJob(MyJobDetail1.class).
                withIdentity("myjob1","jobgroup1").build();
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("mytrigger","trigroup1").
                startNow().withSchedule(
                        CronScheduleBuilder.cronSchedule("0/2 * * 31 * ? ")
        ).build();
        scheduler.scheduleJob(jobDetail,trigger);
        //5、启动作业
        scheduler.start();//启动
    }
}
