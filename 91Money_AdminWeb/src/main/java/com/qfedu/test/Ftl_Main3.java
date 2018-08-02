package com.qfedu.test;

import com.qfedu.core.ftl.FtlUtil;
import com.qfedu.domain.news.News;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 *@Author feri
 *@Date Created in 2018/8/1 15:55
 */
public class Ftl_Main3 {
    public static void main(String[] args) {
        //FtlUtil ftlUtil=new FtlUtil("91Money_AdminWeb/srv/main/webapp/tem");
        try{
            //1、创建配置对象
            Configuration configuration=new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            //2、设置模板目录
            configuration.setDirectoryForTemplateLoading(new File("91Money_AdminWeb/src/main/webapp/tem"));
            //System.err.println(new File(dir).getAbsolutePath());
            //3、加载模板文件
            Template template=configuration.getTemplate("newstemp.ftl","UTF-8");
            //参数
            Map<String,Object> params=new HashMap<>();
            News news=new News();
            news.setId(1);
            news.setTitle("模板");
            news.setSourceurl("http://www.baidu.com");
            //news.setSourcename("百度");
            news.setLastTime("2018-08-01");
            news.setContentHtml("<div><h1>八一建军节</h1></div>");
            params.put("news",news);
            //5、保存
            FileWriter writer=new FileWriter("1.html");
            template.process(params,writer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
