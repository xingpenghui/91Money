package com.qfedu.service.shiro;

import com.qfedu.core.shiro.ShiroUtil;
import com.qfedu.domain.admin.SysUser;
import com.qfedu.service.admin.SysMenuService;
import com.qfedu.service.admin.SysRoleService;
import com.qfedu.service.admin.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *@Author feri
 *@Date Created in 2018/7/30 09:34
 */
@Service
public class AdminRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    //实现用户权限的数据提供  授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser)principalCollection.getPrimaryPrincipal();
        Long userId = user.getUserId();
        //分配权限
        //用户角色列表
        List<String> roleList = Arrays.asList("admin","user");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //分配角色权限
        info.addRoles(roleList);
        List<String> permissions = sysMenuService.getUserPermsList(userId);
        for (String perm : permissions) {
            System.err.println("授权："+perm);
        }
        //分配资源权限
        info.addStringPermissions(permissions);
        return info;
    }

    //实现用户登录的验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取输入的用户名和密码
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String username= token.getUsername();
        String password=new String(token.getPassword());
        System.err.println("用户登录信息："+username+"--->"+password);
        //获取数据库的用户信息
        SysUser user=sysUserService.getByUsername(username);
        System.out.println("用户："+user);
        if(user==null){
            //用户名不存在
            throw new UnknownAccountException("用户不存在");
        }else if(!Objects.equals(password,user.getPassword())){
            throw new IncorrectCredentialsException("密码不正确");
        }else {
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,password,getName());

            ShiroUtil.getSubject().getSession().setAttribute("sysuser",user);
            return info;
        }
    }
}
