package com.qfedu.service.job;


import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.job.ScheduleJob;

/**
 *@Author feri
 *@Date Created in 2018/7/31 19:21
 */
public interface ScheduleJobService {
	//分页业务方法
	DataGridResult getPageList(Query query);

	void deleteBatch(Long[] ids);

	ScheduleJob getById(Long jobId);

	void save(ScheduleJob scheduleJob);

	void update(ScheduleJob scheduleJob);

	void pauseBatch(Long[] jobIds);

	void runBatch(Long[] jobIds);

	void resumeBatch(Long[] jobIds);
}
