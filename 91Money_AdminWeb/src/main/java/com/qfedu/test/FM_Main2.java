package com.qfedu.test;


import com.qfedu.domain.admin.SysUser;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 *@Author feri
 *@Date Created in 2018/8/1 14:23
 * Freemarker 模板引擎  主要用作动态页面静态化
 * 用处：
 * 1、模板，生成静态页面
 * 2、模板，输出静态页面
 */
public class FM_Main2 {

    public static void main(String[] args) throws Exception {
        //1、创建配置对象
        Configuration configuration=new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        //2、设置模板目录
        configuration.setDirectoryForTemplateLoading(new File("91Money_AdminWeb/src/main/resources/tem"));

        //3、加载模板文件
        Template template=configuration.getTemplate("all.ftl","UTF-8");
        //4、准备数据
        Map<String,Object> params=new HashMap<>();
        //字符串
        params.put("name","香蕉");
        //整型
        params.put("version",12);
        //时间格式
        params.put("mtime",new Date());
        //对象
        SysUser user=new SysUser();
        user.setEmail("qfjava@163.com");
        user.setMobile("110");
        user.setUsername("千锋");
        user.setPassword("qianfeng");
        params.put("user",user);
        //集合
        List<String> list=new ArrayList<>();
        for(int i=1;i<10;i++){
            list.add("天气越来越凉+"+i);
        }
        params.put("weathers",list);

        //Map集合
        Map<String,String> map=new HashMap<>();
        for(char i=65;i<70;i++){
            map.put(i+"",i+"字母真好看");
        }
        params.put("zm",map);

        //5、保存
        FileWriter writer=new FileWriter("all.html");
        template.process(params,writer);
        System.out.println("OK");
    }
}
