package com.qfedu.service.admin.impl;

import com.qfedu.domain.news.News;
import com.qfedu.mapper.news.NewsMapper;
import com.qfedu.service.admin.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/31 19:24
 */
@Service
public class SpiderServiceImpl implements SpiderService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public boolean saveNews(News news) {
        return newsMapper.insert(news)>0;
    }

    @Override
    public List<News> queryAllNews(int flag) {
        return newsMapper.selectAll(flag);
    }
}
