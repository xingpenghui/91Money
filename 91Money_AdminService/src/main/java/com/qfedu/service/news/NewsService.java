package com.qfedu.service.news;

import com.qfedu.core.vo.R;
import com.qfedu.domain.news.News;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/1 15:10
 */
public interface NewsService {
    //新增
    R save(News news);
    //查询
    List<News> queryAll(int flag);


}
