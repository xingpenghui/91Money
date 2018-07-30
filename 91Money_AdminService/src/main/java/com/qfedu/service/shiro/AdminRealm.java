package com.qfedu.service.shiro;

import com.qfedu.core.shiro.ShiroUtil;
import com.qfedu.domain.admin.SysUser;
import com.qfedu.mapper.admin.SysMenuMapper;
import com.qfedu.mapper.admin.SysUserMapper;
import com.qfedu.service.admin.SysMenuService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Set;

/**
 *@Author feri
 *@Date Created in 2018/7/30 09:34
 */
@Service
public class AdminRealm extends AuthorizingRealm {
    @Autowired
    private SysUserMapper mapper;
    @Autowired
    private SysMenuService menuService;
    //实现用户权限的数据提供
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //获取登录用户
        SysUser user= (SysUser) principalCollection.getPrimaryPrincipal();
//        SysUser user1=(SysUser) ShiroUtil.getSession().getAttribute("sysuser");
        long id=user.getUserId();
        //获取当前用户的所有权限
        Set<String>userperms=menuService.getUserPermissions(id);
        System.out.println("用户权限："+userperms);
        info.setStringPermissions(userperms);
        return info;
    }

    //实现用户登录的验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取输入的用户名和密码
//        String username= (String) authenticationToken.getPrincipal();
//        String password=(String) authenticationToken.getCredentials();
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String username= token.getUsername();
        String password=new String(token.getPassword());
        System.out.println("用户登录信息："+username+"--->"+password);
        //获取数据库的用户信息
        SysUser user=mapper.queryByUserName(username);
        if(user==null){
            //用户名不存在
            throw new UnknownAccountException("用户不存在");
        }else if(!Objects.equals(password,user.getPassword())){
            throw new IncorrectCredentialsException("密码不正确");
        }else {
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,password,getName());
            return info;
        }
    }
}
