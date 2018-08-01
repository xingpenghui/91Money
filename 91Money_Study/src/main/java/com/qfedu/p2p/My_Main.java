package com.qfedu.p2p;

import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/31 17:11
 */
public class My_Main {
    public static void main(String[] args) {
        List<String> strList=new ArrayList<String>();
        strList.add("http://www.pushbt.com");
        Spider.create(new MyProcessor()).addUrl("http://www.pushbt.com").run();
//        for(String str:strList){
//            Spider.create(new MyProcessor()).addUrl(str).thread(1).run();
//        }
    }
}
