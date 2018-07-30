package com.qfedu.service.admin.impl;

import com.qfedu.core.vo.DataGridResult;
import com.qfedu.core.vo.Query;
import com.qfedu.domain.admin.SysConfig;
import com.qfedu.mapper.admin.SysConfigMapper;
import com.qfedu.service.admin.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigMapper mapper;
	
	@Override
	public DataGridResult getPageList(Query query) {
		Integer offset = (Integer)query.get("offset");
		Integer limit = (Integer)query.get("limit");
		
		//调用Dao查询分页列表数据
		List<SysConfig> rows = mapper.selectByPage(offset, limit);
		
		//调用Dao查询总记录数
		Long total = (Long)mapper.selectCount();
				
		//创建DataGridResult对象
		DataGridResult dataGridResult = new DataGridResult(rows, total);
		return dataGridResult;
	}

	@Override
	public void deleteBatch(Long[] jobIds) {
	
		mapper.deleteBatch(jobIds);
	}

	@Override
	public SysConfig getById(Long jobId) {
		return mapper.selectById(jobId);
	}

	@Override
	public void save(SysConfig sysConfig) {
		sysConfig.setStatus((byte)1);
		mapper.insert(sysConfig);
	}
	@Override
	public void update(SysConfig sysConfig) {
		mapper.update(sysConfig);
	}
	
	@Override
	public List<SysConfig> findByKeyPrefix(String keyPrefix) {
		return mapper.selectByKeyPrefix(keyPrefix);
	}
	
}
