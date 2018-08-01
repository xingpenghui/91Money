package com.qfedu.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.regex.Pattern;

/**
 *@Author feri
 *@Date Created in 2018/7/31 14:50
 */
public class MySpider implements PageProcessor {
    public static final String urlPre="https://www.zhihu.com/";
    @Override
    public void process(Page page) {
        page.putField("作者：",page.getHtml().xpath("a[@class='UserLink-link']/text()").all());
        page.putField("标题：",page.getHtml().xpath("a[@data-za-detail-view-element_name='title']/text()").all());
        page.putField("链接：",page.getHtml().xpath("a[@data-za-detail-view-element_name='title']/@href").all());

//        Pattern  pattern
        //System.out.println("p---->"+page.getUrl());
       // System.out.println(page.getHtml());
        //System.out.println("my-"+page.getHtml().xpath("/html/body/div[@id='root']").all());
       // System.out.println("my-"+page.getHtml().xpath("/html/body/div/div/div[@class='LoadingBar']").all());

//        System.out.println("my-"+page.getHtml().xpath("[@itemProp='url']").all());//图片
//        System.out.println("my-"+page.getHtml().xpath("meta[@itemProp='image']").all());//图片
//        System.out.println("my-"+page.getHtml().xpath("meta[@itemProp='name']").all());//抓取名称和标题
//        System.out.println("my-"+page.getHtml().xpath("meta[@itemProp='name']/@content").all());//抓取名称和标题
//        System.out.println("a--"+page.getHtml().xpath("a/@href").all());
        //System.out.println("my-"+page.getHtml().xpath("div[last()]").all());//抓取名称和标题

        //System.out.println(page.getHtml().xpath("/html/body/div/div/div/main/div/div/div/div[1]/div/div/div[1]/h2/text()").all());
//        if(page.getUrl().regex("/question/[0-9]{9}/answer/[0-9]{9}").match())
//        {
//            page.addTargetRequests(page.getHtml().xpath("/html/body/div/div/div/main/div/div/div/div[1]/div/div/div[1]/h2/text()").all());
//        }else{
//            System.out.println("p--->"+page.getJson());
//        }
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(10).setSleepTime(200);
    }
}
