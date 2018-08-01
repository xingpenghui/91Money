package com.qfedu.controller;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.core.vo.RM;
import com.qfedu.domain.job.ScheduleJob;
import com.qfedu.service.job.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/7/31 19:30
 */
@Controller
@RequestMapping("/schedule/job")
public class QuartzController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @RequestMapping("/{page}")
    public String index(@PathVariable String page) {
        return "schedule/job/" +  page;
    }

    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions({"schedule:job:list"})
    public DataGridResult getPage(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return scheduleJobService.getPageList(query);
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions({"schedule:job:delete"})
    public RM deleteBatch(@RequestBody Long[] ids) {
        scheduleJobService.deleteBatch(ids);
        return RM.ok();
    }

    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions({"schedule:job:save"})
    public RM save(@RequestBody ScheduleJob scheduleJob) {
        scheduleJobService.save(scheduleJob);
        return RM.ok();
    }

    @RequestMapping("/info/{jobId}")
    @ResponseBody
    @RequiresPermissions({"schedule:job:info"})
    public RM save(@PathVariable Long jobId) {
        ScheduleJob scheduleJob = scheduleJobService.getById(jobId);
        return RM.ok().put("job", scheduleJob);
    }

    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions({"schedule:job:update"})
    public RM update(@RequestBody ScheduleJob scheduleJob) {
        scheduleJobService.update(scheduleJob);
        return RM.ok();
    }


    @RequestMapping("/pause")
    @ResponseBody
    @RequiresPermissions({"schedule:job:pause"})
    public RM pauseBatch(@RequestBody Long[] ids) {
        scheduleJobService.pauseBatch(ids);
        return RM.ok();
    }

    @RequestMapping("/resume")
    @ResponseBody
    @RequiresPermissions({"schedule:job:resume"})
    public RM resumeBatch(@RequestBody Long[] ids) {
        scheduleJobService.resumeBatch(ids);
        return RM.ok();
    }

    @RequestMapping("/run")
    @ResponseBody
    @RequiresPermissions({"schedule:job:run"})
    public RM runBatch(@RequestBody Long[] ids) {
        scheduleJobService.runBatch(ids);
        return RM.ok();
    }

}
