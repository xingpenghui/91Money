package com.qfedu.spider;

import com.qfedu.domain.news.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/31 18:43
 * 财经新闻
 */
public class FinanceProcessor implements PageProcessor {
    //setRetryTimes 重试次数  setSleepTime停顿时间  setTimeOut超时时间
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000)
            .addHeader("Accept-Encoding", "/").setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
    Logger logger=LoggerFactory.getLogger(FinanceProcessor.class);
    private int count=0;
    @Override
    public void process(Page page) {
        count++;
        System.err.println("s---->"+page.getUrl());
        //只爬取环球网的财经新闻
        if(page.getUrl().get().contains("huanqiu.com")) {
            //判断是否为新闻详情页面
            if (page.getUrl().get().endsWith(".html")) {
                News news = new News();
                news.setTitle(page.getHtml().xpath("h1[@class='tle']/text()").toString());
                news.setContentHtml(page.getHtml().xpath("div[@class='la_con']").toString());
                news.setLastTime(page.getHtml().xpath("span[@class='la_t_a']/text()").toString());
                //如果获取的是带有html的内容 不要使用text
                news.setRefHtml(page.getHtml().xpath("div[@class='la_xiangguan_news']").toString());
                news.setSourcename(page.getHtml().xpath("span[@class='la_t_b']/a/text()").toString());
                news.setSourceurl(page.getHtml().xpath("span[@class='la_t_b']/a/@href").toString());
                page.putField("news", news);
                System.err.println("s---->"+news);
            }
            List<String> links = page.getHtml().links().all();
            //继续深挖  设置后面的目标页  可以继续爬取
            page.addTargetRequests(links);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
