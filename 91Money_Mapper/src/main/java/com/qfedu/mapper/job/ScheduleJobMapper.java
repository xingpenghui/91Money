package com.qfedu.mapper.job;


import com.qfedu.domain.job.ScheduleJob;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ScheduleJobMapper{

    @Select("select * from schedule_job limit #{index},#{count}")
    @ResultType(ScheduleJob.class)
    List<ScheduleJob> selectByPage(@Param("index") int index, @Param("count") int count);
    @Select("select COUNT(*) from schedule_job")
    @ResultType(Long.class)
    Long selectCount();
    @Delete("delete from schedule_job where job_id in #{ids}")
    int deleteBatch(Long[] ids);
    @Select("select * from schedule_job where job_id=#{jid}")
    @ResultType(ScheduleJob.class)
    ScheduleJob selectById(long jid);
    @Insert("insert into schedule_job(job_id,bean_name,method_name,params,cron_expression,status,remark,create_time) values(#{bean_name},#{method_name},#{params},#{cron_expression},#{status},#{remark},now())")
    int insert(ScheduleJob job);
    @Update("update schedule_job set bean_name=#{bean_name} ,method_name=#{method_name},remark=#{remark}  where job_id=#{job_id}")
    int update(ScheduleJob job);
}
