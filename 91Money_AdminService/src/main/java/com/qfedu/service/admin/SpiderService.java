package com.qfedu.service.admin;

import com.qfedu.domain.news.News;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/31 19:21
 */
public interface SpiderService {
    boolean saveNews(News news);
    List<News> queryAllNews(int flag);
}
