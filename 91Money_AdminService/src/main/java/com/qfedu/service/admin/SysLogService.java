package com.qfedu.service.admin;


import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysLog;

public interface SysLogService {

	//分页业务方法
	DataGridResult getPageList(Query query);

	void save(SysLog sysLog);
}
