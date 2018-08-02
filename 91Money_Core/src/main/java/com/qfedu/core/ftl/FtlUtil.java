package com.qfedu.core.ftl;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 *@Author feri
 *@Date Created in 2018/8/1 15:16
 */
public class FtlUtil {
    private String dir;
    public FtlUtil(String dir){
        this.dir=dir;
    }
    public <T> void createHtml(String path,String temFile, T t, String toFile){
        try{
            //1、创建配置对象
            Configuration configuration=new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            //2、设置模板目录
            configuration.setDirectoryForTemplateLoading(new File(path,dir));
            //System.err.println(new File(dir).getAbsolutePath());
            //3、加载模板文件
            Template template=configuration.getTemplate(temFile,"UTF-8");
            //参数
            Map<String,Object> params=new HashMap<>();
            params.put("news",t);
            //5、保存
            FileWriter writer=new FileWriter(toFile);
            template.process(params,writer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
