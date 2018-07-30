package com.qfedu.mapper.oss;


import com.qfedu.domain.oss.OSSPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OSSPoMapper {
    @Delete("delete from t_oss where ourl=#{url}")
    int deleteByUrl(String url);
    @Insert("insert into t_oss( objname, period, createtime,ourl)values(#{objname},#{period},now(),#{ourl}) ")
    int insert(OSSPo record);
    @Select("select id,objname, period, createtime from t_oss where ourl=#{url} ")
    @ResultType(OSSPo.class)
    OSSPo selectByUrl(String url);
    @Select("select id,objname, period, createtime from t_oss ")
    @ResultType(OSSPo.class)
    List<OSSPo> selectAll();
}