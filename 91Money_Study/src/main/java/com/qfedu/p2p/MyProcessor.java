package com.qfedu.p2p;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/31 16:56
 */
public class MyProcessor implements PageProcessor {
    //设置网站源
    private static String netSite="PushBT";

    private Site site = Site.me().setDomain("http://www.pushbt.com")
            .setCharset("UTF-8").setSleepTime(1000)//编码
            .setRetryTimes(3);//重试次数
    private static String  BASE_URL="http://www.pushbt.com";
    private List<String> targetUrlList;
    private int count=0;
    @Override
    public void process(Page page) {
        //定义如何抽取页面信息，并保存下来
        List<String> links = page.getHtml().xpath("//table[@class='items']//tr[@class='odd']/td[2]/a/@href").all();
        //将需要待爬的网页地址都存下来，以待后续从中取出
        targetUrlList=linkURL(BASE_URL, links);
        page.addTargetRequests(targetUrlList);
       // Seeds seed = new Seeds();
        // 获取名称
        String name = page.getHtml().xpath("//ul[@id='filelist']//li/span/@title").toString();
        if (name==null||"".equals(name)) {//名称为空，则跳过;已存在(true),则跳过
            page.setSkip(true);
            count++;
            System.err.println("skip the "+count+" ,title : "+name);
            return;
        }
        page.putField("name", name);
        //seed.setSeedName(name);
        // 获取hash值(无hash值，默认为null)
//      String hash = page.getHtml().xpath("//p[@class='dd desc']//b[2]/text()").toString();
        page.putField("hash", null);
        //seed.setSeedHash(null);
        // 描述(没有描述信息,则默认为名称)
//      String desc = page.getHtml().xpath("//div[@class='dd filelist']/p/text()").toString();
        page.putField("desc", name);
       // seed.setSeedDes(name);
        // 文件个数
        String number = page.getHtml().xpath("//ul[@class='params-cover']/li[4]/div[@class='value']/text()").toString();
        page.putField("number", number);
        //seed.setNumber(number);
        // 文件大小
        String size = page.getHtml().xpath("//ul[@class='params-cover']/li[5]/div[@class='value']/text()").toString();
        page.putField("size", size);
        //seed.setSize(size);
        // 获取收录时间
        String includeDate = page.getHtml().xpath("//ul[@class='params-cover']/li[2]/div[@class='value']/text()").toString();
        page.putField("includeDate", includeDate);
        //seed.setIncludeTime(includeDate);
        //最近下载时间
        String recentlyDown = page.getHtml().xpath("//ul[@class='params-cover']/li[3]/div[@class='value']/text()").toString();
        page.putField("recentlyDown", recentlyDown);
        //seed.setRecentlyDown(recentlyDown);
        // 人气
        String popularity = page.getHtml().xpath("//ul[@class='params-cover']/li[6]/div[@class='value']/text()").toString();
        page.putField("popularity", popularity);
        //seed.setPopularity(popularity);
        // 下载速度
//      String speed = page.getHtml().xpath("//p[@class='dd desc']//b[7]/text()").toString();
        page.putField("speed", page.getHtml().xpath("//p[@class='dd desc']//b[7]/text()").toString());
        //seed.setSpeed(SpiderUtil.getSpeed(popularity));
        // 获取磁力链接
        String magnet = page.getHtml().xpath("//ul[@class='params-cover']/li[9]/div[@class='value']/a/@href").toString();
        page.putField("magnet", magnet);
        //seed.setMagnet(magnet);
        // 标签(在详情页面没有tag，暂时以热门搜索为tag)
        List<String> tags = page.getHtml().xpath("//div[@class='block oh']/a/span/text()").all();
        page.putField("tags", tags);
        //seed.setTag(tags.toString());
       // seed.setCreateTime(new Date());
        //seed.setUpdateTime(new Date());
        //seed.setSource(netSite);
        //seed.setCategory("movies");
        //保存到数据库
        //service.insert(seed);
    }

    @Override
    public Site getSite() {
        return site;
    }
    private List<String> linkURL(String url,List<String> urls){
        List<String> l1=new ArrayList<>();
        for(String r:urls){
            if(r.startsWith(url)){
                l1.add(r);
            }
        }
            return l1;
    }
}
