package com.qfedu.money;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *@Author feri
 *@Date Created in 2018/7/31 16:07
 */
public class MoneyProcess implements PageProcessor {
    Site site=Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(300).addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
    private int pageNum=0;
    @Override
    public void process(Page page) {
        System.out.println(page.getUrl());
        if(page.getUrl().regex("^http://finance\\.huanqiu\\.com/[\\w]{2,}").match()){
            page.addTargetRequests(page.getHtml().xpath("//li/span/a[@target='_blank']/@href").all());
            pageNum++;
        }else{
            page.putField("title",page.getHtml().xpath("a/text()").toString());
            page.putField("url",page.getHtml().xpath("a/@href").toString());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
