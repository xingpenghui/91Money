package com.qfedu.controller;

import com.qfedu.core.ftl.FtlUtil;
import com.qfedu.core.util.DateUtil;
import com.qfedu.core.vo.R;
import com.qfedu.domain.news.News;
import com.qfedu.service.news.NewsService;
import com.qfedu.spider.FinanceProcessor;
import com.qfedu.spider.RedisSavePepeLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/1 09:54
 */
//@Controller
@RestController
public class SpiderControlller {

//    private String toDir="news";
    @Autowired
    private NewsService service;
    @Autowired
    private FtlUtil ftlUtil;
    Spider spider;
    @Autowired
    private RedisSavePepeLine redisSavePepeLine;
    @GetMapping("/spiderstart")
    public R start(){
        spider=Spider.create(new FinanceProcessor());
        spider.thread(10).addUrl("http://finance.huanqiu.com/").addPipeline(redisSavePepeLine).start();
        return R.setOK("爬虫已经启动");
    }
    @GetMapping("/spiderstop")
    public R stop(){
        spider.stop();
        spider.close();
        return R.setOK("爬虫已经停止");
    }
    @GetMapping("/createhtml")
    public R create(HttpServletRequest request){
        String path=new File(request.getServletContext().getRealPath("/")).getAbsolutePath();
        //Lambda表达式
        new Thread(()->{
            List<News> list=service.queryAll(0);
            File dir=new File(path,DateUtil.getDate());
            if(!dir.exists()){
                dir.mkdirs();
            }
            System.err.println(dir.getAbsolutePath());
            for(News n:list){
                System.err.println(n);
                ftlUtil.createHtml(path,"newstemp.ftl",n,dir.getAbsolutePath()+File.separator +n.getId()+".html");
            }
        }).start();
        return R.setOK("创建成功");
    }

}
