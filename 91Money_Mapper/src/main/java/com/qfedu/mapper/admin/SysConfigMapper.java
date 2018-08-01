package com.qfedu.mapper.admin;


import com.qfedu.domain.admin.SysConfig;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SysConfigMapper {
	List<SysConfig> selectByKeyPrefix(String keyPrefix);
	int insert(SysConfig config);
	SysConfig selectById(Long id);
	List<SysConfig> selectByPage(@Param("index") int limit, @Param("size") int size);
	Long selectCount();
	int deleteBatch(Long[]ids);
	int update(SysConfig config);
}
