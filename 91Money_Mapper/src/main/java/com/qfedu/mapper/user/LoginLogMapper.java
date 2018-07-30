package com.qfedu.mapper.user;

import com.qfedu.domain.user.LoginLog;
import java.util.List;

import org.apache.ibatis.annotations.*;

public interface LoginLogMapper {
    @Insert("insert into t_loginlog (ip, createtime,uid,msg)values(#{ip}, #{createtime},#{uid}, #{msg})")
    @Options(useGeneratedKeys = true,keyProperty ="id")
    int insert(LoginLog record);
    @Select("select id,ip, createtime,uid,msg from t_loginlog where id=#{id}")
    @ResultType(LoginLog.class)
    LoginLog selectById(Integer id);
    @Select("select id,ip, createtime,uid,msg from t_loginlog")
    @ResultType(LoginLog.class)
    List<LoginLog> selctAll();
}