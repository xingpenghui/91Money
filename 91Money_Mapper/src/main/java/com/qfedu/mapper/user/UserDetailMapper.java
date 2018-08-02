package com.qfedu.mapper.user;

import com.qfedu.domain.admin.SysMenu;
import com.qfedu.domain.user.UserDetail;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

public interface UserDetailMapper {

    @Update("update t_userdetail set realname=#{realname},sex=#{sex},idNumber=#{idNumber},birthDate=#{birthdate},address=#{address},idimage1=#{idimage1},idimage2=#{idimage2},flag=1 where uid=#{uid} ")
    int updateById(UserDetail detail);
    @Update("update t_userdetail set flag=#{flag} where id=#{id}")
    int updateId(@Param("flag") int flag,@Param("id") int id);
    //@Insert("insert into t_userdetail(uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag ) values(#{uid},#{realname},#{sex} ,#{idNumber} ,#{birthDate},#{address},#{idimage1} ,#{idimage2},now() ,0)")
    @Insert("insert into t_userdetail(uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag ) values(#{uid},null,null ,null ,null,null,null ,null,now() ,0)")
    @Options(useGeneratedKeys = true,keyProperty ="id")
    int insert(int uid);
    @Select("select id ,uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag from t_userdetail where uid=#{uid}")
    @ResultType(UserDetail.class)
    UserDetail selectByUid(int uid);
    @Select("select id ,uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag from t_userdetail where flag=#{flag}")
    @ResultType(UserDetail.class)
    List<UserDetail> selectByFlag(int flag);

    @Select("select * from t_userdetail limit #{index},#{count}")
    @ResultType(UserDetail.class)
    List<UserDetail> queryByPage(@Param("index") int index, @Param("count") int count);
    @Select("select count(*) from t_userdetail")
    @ResultType(Long.class)
    long queryCount();




}