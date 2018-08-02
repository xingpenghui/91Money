package com.qfedu.uicontroller.user;

import com.alibaba.fastjson.JSON;
import com.qfedu.core.redis.JedisUtil;
import com.qfedu.core.util.CookieUtil;
import com.qfedu.core.vo.R;
import com.qfedu.domain.user.User;
import com.qfedu.domain.user.UserDetail;
import com.qfedu.service.user.UserDetailService;
import com.qfedu.uicontroller.login.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/27 16:47
 */
@RestController
public class UserDetailController {

    @Autowired
    private UserDetailService service;

    @Autowired
    private JedisUtil jedisUtil;
    //初始化
    @GetMapping("detailinit")
    public R init(HttpServletRequest request){
        User user=Login.getU(jedisUtil,request);
        return service.realNameAuth(user.getId());
    }
    //修改
    @PostMapping("detailupdate")
    public R update(UserDetail detail,HttpServletRequest request){
        User user=Login.getU(jedisUtil,request);
        detail.setUid(user.getId());
        return service.update(detail);
    }
    //查询
    @GetMapping("detailall")
    public List<UserDetail> queryAll(int flag){
        return service.queryByFlag(flag);
    }


}
