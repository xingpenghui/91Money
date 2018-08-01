package com.qfedu.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *@Author feri
 *@Date Created in 2018/7/31 09:53
 */
public class MyJobDetail1 implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("天气炎热，请注意中暑！"+System.currentTimeMillis()/1000);
    }
}
