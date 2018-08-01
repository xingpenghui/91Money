package com.qfedu.service.job.impl;

import java.util.Date;
import java.util.List;

import com.qfedu.core.schedule.ScheduleUtils;
import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.job.ScheduleJob;
import com.qfedu.mapper.job.ScheduleJobMapper;
import com.qfedu.service.job.ScheduleJobService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	
	@Autowired
	private Scheduler scheduler;
	
	@Override
	public DataGridResult getPageList(Query query) {
		Integer offset = (Integer)query.get("offset");
		Integer limit = (Integer)query.get("limit");
		
		//调用Dao查询分页列表数据
		List<ScheduleJob> rows = scheduleJobMapper.selectByPage(offset, limit);
		
		//调用Dao查询总记录数
		Long total = (Long)scheduleJobMapper.selectCount();
				
		//创建DataGridResult对象
		DataGridResult dataGridResult = new DataGridResult(rows, total);
		return dataGridResult;
	}

	@Override
	public void deleteBatch(Long[] jobIds) {
		//删除任务
		for(Long jobId : jobIds){
    		ScheduleUtils.deleteJob(scheduler, jobId);
    	}
		scheduleJobMapper.deleteBatch(jobIds);
	}

	@Override
	public ScheduleJob getById(Long jobId) {
		return scheduleJobMapper.selectById(jobId);
	}

	@Override
	public void save(ScheduleJob scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(Byte.valueOf("1"));
		scheduleJobMapper.insert(scheduleJob);
		
		//创建任务
		ScheduleUtils.createJob(scheduler, scheduleJob);
	}

	@Override
	public void update(ScheduleJob scheduleJob) {
		
		ScheduleUtils.updateJob(scheduler, scheduleJob);
		scheduleJobMapper.update(scheduleJob);
	}
	
	@Override
	public void pauseBatch(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.pauseJob(scheduler, jobId);
    	}
		for (Long jobId : jobIds) {
			ScheduleJob job = scheduleJobMapper.selectById(jobId);
			job.setStatus(Byte.valueOf("2"));
			scheduleJobMapper.update(job);
		}
	}
	
	@Override
	public void resumeBatch(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.resumeJob(scheduler, jobId);
    	}
		
		for (Long jobId : jobIds) {
			ScheduleJob job = scheduleJobMapper.selectById(jobId);
			job.setStatus(Byte.valueOf("3"));
			scheduleJobMapper.update(job);
		}
	}
	
	@Override
	public void runBatch(Long[] jobIds) {
		for(Long jobId : jobIds){
			ScheduleUtils.run(scheduler, jobId);
    	}
	}
}
