package com.qfedu.spider;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 *@Author feri
 *@Date Created in 2018/7/31 14:46
 */
public class WebMagic_Main {
    public static void main(String[] args) {
//        Spider.create(new MySpider()).addUrl("https://www.zhihu.com/").
//                addPipeline(new ConsolePipeline()).thread(10).start();
 Spider.create(new MySpider()).addUrl("https://blog.csdn.net/").
                addPipeline(new ConsolePipeline()).thread(10).start();
    }
}
