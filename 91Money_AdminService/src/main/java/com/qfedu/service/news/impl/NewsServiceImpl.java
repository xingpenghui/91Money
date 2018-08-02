package com.qfedu.service.news.impl;

import com.qfedu.core.util.ExcelUtils;
import com.qfedu.core.util.ExecuteUtils;
import com.qfedu.core.vo.R;
import com.qfedu.domain.news.News;
import com.qfedu.mapper.news.NewsMapper;
import com.qfedu.service.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/1 15:11
 */
@Service
//@WebService
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper mapper;
    @Override
    public R save(News news) {
        return ExecuteUtils.getR(mapper.insert(news),"新增成功");
    }

    @Override
    public List<News> queryAll(int flag) {
        return mapper.selectAll(flag);
    }
}
