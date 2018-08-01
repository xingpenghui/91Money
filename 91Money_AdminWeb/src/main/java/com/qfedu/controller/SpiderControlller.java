package com.qfedu.controller;

import com.qfedu.core.vo.R;
import com.qfedu.spider.FinanceProcessor;
import com.qfedu.spider.RedisSavePepeLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 *@Author feri
 *@Date Created in 2018/8/1 09:54
 */
//@Controller
@RestController
public class SpiderControlller {
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

}
