package com.qfedu.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *@Author feri
 *@Date Created in 2018/7/31 15:55
 */
public class CSDNSpider implements PageProcessor {
    @Override
    public void process(Page page) {
        System.out.println(page.getHtml());
        page.putField("title",page.getHtml().xpath("div[@class='title']/h2/a/text()").toString());
        page.putField("url",page.getHtml().xpath("div[@class='title']/h2/a/@href").toString());
    }

    @Override
    public Site getSite() {
        return  Site.me().setRetryTimes(5).
                addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36").
                setSleepTime(100);
    }
}
