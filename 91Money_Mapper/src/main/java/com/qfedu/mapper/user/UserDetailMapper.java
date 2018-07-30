package com.qfedu.mapper.user;

import com.qfedu.domain.user.UserDetail;
import java.util.List;

import org.apache.ibatis.annotations.*;

public interface UserDetailMapper {

    @Update("update t_userdetail set realname=#{realname},sex=#{sex},idNumber=#{idNumber},birthDate=#{birthdate},address=#{address},idimage1=#{idimage1},idimage2=#{idimage2},flag=1 where uid=#{uid} ")
    int updateById(UserDetail detail);
    //@Insert("insert into t_userdetail(uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag ) values(#{uid},#{realname},#{sex} ,#{idNumber} ,#{birthDate},#{address},#{idimage1} ,#{idimage2},now() ,0)")
    @Insert("insert into t_userdetail(uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag ) values(#{uid},null,null ,null ,null,null,null ,null,now() ,0)")
    @Options(useGeneratedKeys = true,keyProperty ="id")
    int insert(int uid);
    @Select("select id ,uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag from t_userdetail where flag=1 and uid=#{uid}")
    @ResultType(UserDetail.class)
    UserDetail selectByUid(int uid);
    @Select("select id ,uid,realname,sex ,idNumber ,birthDate,address,idimage1 ,idimage2,createtime ,flag from t_userdetail where flag=#{flag}")
    @ResultType(UserDetail.class)
    List<UserDetail> selectByFlag(int flag);




}