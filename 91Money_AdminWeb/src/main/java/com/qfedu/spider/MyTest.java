package com.qfedu.spider;

import us.codecraft.webmagic.Spider;

/**
 *@Author feri
 *@Date Created in 2018/8/1 10:08
 */
public class MyTest {
    public static void main(String[] args) {
       Spider spider=Spider.create(new FinanceProcessor());
        spider.thread(10).addUrl("http://finance.huanqiu.com/").addPipeline(new RedisSavePepeLine()).run();
    }
}
