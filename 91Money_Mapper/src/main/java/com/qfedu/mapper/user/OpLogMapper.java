package com.qfedu.mapper.user;

import com.qfedu.domain.user.LoginLog;
import com.qfedu.domain.user.OpLog;
import java.util.List;

import org.apache.ibatis.annotations.*;

public interface OpLogMapper {
    @Insert("insert into t_loginlog (ip, createtime,uid,msg) values(#{ip}, now(),#{uid},#{msg})")
    @Options(useGeneratedKeys = true,keyProperty ="id")
    int insert(OpLog record);
    @Select("select id,ip, createtime,uid,msg from t_loginlog where id=#{id}")
    @ResultType(LoginLog.class)
    List<OpLog> selectByType(Integer type);

}