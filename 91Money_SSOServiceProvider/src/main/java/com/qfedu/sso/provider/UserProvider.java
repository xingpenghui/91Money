package com.qfedu.sso.provider;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.qfedu.core.redis.JedisUtil;
import com.qfedu.core.util.CookieUtil;
import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.util.IdGenerator;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.mapper.user.UserDetailMapper;
import com.qfedu.mapper.user.UserMapper;
import com.qfedu.sso.service.UserService;
import org.omg.CORBA.portable.IDLEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *@Author feri
 *@Date Created in 2018/7/24 14:45
 */
@Service
public class UserProvider implements UserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserDetailMapper detailMapper;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private JedisUtil jedisUtil;
    @Override
    public R save(User user) {

        R r= ExecuteUtils.getR(mapper.insert(user),"新增用户");
        if(r.getCode()==0){
            detailMapper.insert(user.getId());
        }
        return r;
    }

    @Override
    public List<User> query() {
        return mapper.queryPage(0,20);
    }

    @Override
    public User queryBy(String name) {
        User user=mapper.queryBy(name);
        return user;
    }

    @Override
    public R ssoLogin(String name, String password) {
        //没有Token  第一次登录
        User user=mapper.queryBy(name);
        System.out.println(user.getUsername()+"--->"+user.getPassword());
        if(user!=null){
            System.out.println(password+"---->"+user.getPassword());
            if(Objects.equals(password,user.getPassword())) {
                //登录成功
                //生成唯一令牌
                long tk=idGenerator.nextId();
                //令牌存储到Redis  key:usertoken+令牌值 value:登录用户信息的json
                jedisUtil.addStr("usertoken:"+tk,JSON.toJSONString(user));
                //设置有效期  默认30分钟
                jedisUtil.expire("usertoken:"+tk,TimeUnit.MINUTES,30);
                //返回结果
                return new R(0,tk+"",user);
            }else {
                //密码不正常
                return R.setError("密码不正确");
            }
        }else{
            //用户名不存在
            return R.setError("用户名不存在");
        }
    }

    @Override
    public R ssoCheck(String token) {
        if(jedisUtil.isKey("usertoken:"+token)){
            //存在就刷新时间
            jedisUtil.expire("usertoken:"+token,TimeUnit.MINUTES,30);
            return new R(0,"存在",JSON.parseObject(jedisUtil.getStr("usertoken:"+token),User.class));
        }else{
            return R.setError("不存在");
        }
    }

   @Override
    public R loginOut(String token) {
        //移除Redis
        jedisUtil.delKey("usertoken:"+token);
        return R.setOK("注销成功");
    }
}
