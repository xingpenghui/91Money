package com.qfedu.test;

import freemarker.template.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 *@Author feri
 *@Date Created in 2018/8/1 10:40
 */
public class Freemarker_Main1 {
    public static void main(String[] args) throws IOException, TemplateException {
        Configuration configuration=new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        File file=new File("91Money_AdminWeb/src/main/resources/tem");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
        configuration.setDirectoryForTemplateLoading(file);
        configuration.setDefaultEncoding("utf-8");
        configuration.setObjectWrapper( new DefaultObjectWrapper());
        Template template=configuration.getTemplate("first.ftl");
        Map<String,Object> map=new HashMap<>();
        map.put("msg","休息一下！");
        Writer writer=new FileWriter("frist.html");
        template.process(map,writer);
        System.out.println("OK");
    }
}
