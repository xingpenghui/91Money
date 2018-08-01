package com.qfedu.core.schedule;

import java.lang.reflect.Method;
import java.util.Date;

import com.qfedu.core.util.SpringContextUtils;
import com.qfedu.domain.job.ScheduleJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ReflectionUtils;

public class QuartzJob extends QuartzJobBean{
	
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("JOB_PARAM_KEY");
		Long jobId = scheduleJob.getJobId();
//		ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService)SpringContextUtils.getBean("scheduleJobLogServiceImpl");
//
//		System.out.println(Thread.currentThread().getName());
		//创建task对象
		String beanName = scheduleJob.getBeanName();
		String methodName = scheduleJob.getMethodName();
		String params = scheduleJob.getParams();
		Object target = SpringContextUtils.getBean(beanName);
		
//		ScheduleJobLog log = new ScheduleJobLog();
//		log.setBeanName(beanName);
//		log.setMethodName(methodName);
//		log.setParams(params);
//		log.setCreateTime(new Date());
//		log.setJobId(jobId);
		long startTime = System.currentTimeMillis();
		try {
			System.out.println("准备执行任务，任务ID：" + jobId);
			Method method = null;
			if(StringUtils.isNotBlank(params)) {
				method = target.getClass().getDeclaredMethod(methodName, String.class);
			}else {
				method = target.getClass().getDeclaredMethod(methodName);
			}
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isNotBlank(params)) {
				method.invoke(target, params);
			}else {
				method.invoke(target);
			}
			
			long times = System.currentTimeMillis() - startTime;
//			log.setTimes(times);
//			log.setStatus((byte)0);
			System.out.println("任务执行完毕，任务ID：" + jobId + "，总耗时：" + times + "毫秒");
		} catch (Exception e) {
			
			e.printStackTrace();
			
			long times = System.currentTimeMillis() - startTime;
//			log.setTimes(times);
//			log.setError(StringUtils.substring(e.toString(), 0, 2000));
//			log.setStatus((byte)1);
			
		} finally {
			
			//scheduleJobLogService.save(log);
		}
	}
}
