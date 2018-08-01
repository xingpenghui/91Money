package com.qfedu.core.spider;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *@Author feri
 *@Date Created in 2018/7/31 18:36
 */
public class SpiderUtil {
    private String rootUrl;
    private String regexP;
    private Spider spider;
    private PageProcessor pageProcessor;
    private Pipeline pipeline;
    public SpiderUtil(String rootUrl,String regexP){
        this.regexP=regexP;
        this.rootUrl=rootUrl;
        spider=Spider.create(pageProcessor).addUrl(rootUrl).addPipeline(pipeline);
    }
    //启动
    public void start(){
        spider.start();
    }
    //停止
    public void stop(){
        spider.stop();
    }
}
