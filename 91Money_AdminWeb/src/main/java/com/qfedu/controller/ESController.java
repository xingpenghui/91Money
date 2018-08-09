package com.qfedu.controller;

import com.alibaba.fastjson.JSON;
import com.qfedu.core.util.HttpUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.news.News;
import com.qfedu.service.news.NewsService;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.jgss.HttpCaller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/9 10:17
 */
@Controller
public class ESController {

    @Autowired
    private NewsService service;

    @RequestMapping("sys/es/{page}")
    public String index(@PathVariable String page){
        return "sys/es/"+page;
    }

    //HttpClient  Apache 的网络请求工具 JAVA HttpURLConnection
    @RequestMapping("newsindexadd")
    public R save() throws IOException {
        List<News>list= service.queryAll(0);
        String json= JSON.toJSONString(list);
        //创建请对象
        HttpPost post=new HttpPost("http://localhost:8089/newsessave.do");
        //设置请求参数
        List<NameValuePair> pairs=new ArrayList<>();
        pairs.add(new BasicNameValuePair("json",json));
        post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
        //3、创建客户端
        HttpClient client=HttpClientBuilder.create().build();
        //4、发起请求
        HttpResponse response=client.execute(post);
        //5、验证状态码
        if(response.getStatusLine().getStatusCode()==200){
            //获取内容
           String rjson= EntityUtils.toString(response.getEntity(),"UTF-8");
           return JSON.parseObject(rjson,R.class);
        }else {
            return R.setError("异常");
        }
    }
}
