package com.qfedu.money;

import com.qfedu.spider.MySpider;
import redis.clients.jedis.RedisPipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 *@Author feri
 *@Date Created in 2018/7/31 16:08
 */
public class Spedir_Main {
    public static void main(String[] args) {
       String url="http://finance.huanqiu.com/";
       //new RedisPipeline()
       // new JsonFilePipeline()
//        Spider.create(new MoneyProcess()).addUrl(url).
//                addPipeline(new ConsolePipeline()).thread(10).runAsync();
        Spider.create(new MoneyProcess()).addUrl(url).setScheduler(new RedisScheduler("")).
                addPipeline(new ConsolePipeline()).thread(10).runAsync();

    }
}
