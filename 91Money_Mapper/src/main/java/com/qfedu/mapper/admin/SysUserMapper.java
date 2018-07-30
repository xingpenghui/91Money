package com.qfedu.mapper.admin;

import com.qfedu.domain.admin.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/29 23:41
 */
public interface SysUserMapper {
    int insert(SysUser record);
    SysUser selectByPrimaryKey(Long userId);
    int updateByPrimaryKey(SysUser record);
    SysUser queryByUserName(String username);
    List<SysUser> selectByPage(@Param("index") int index, @Param("count") int count);
    long selectCount();
    int deleteBatch(Long[] uids);
    String queryPassword(long uid);

}
