package com.qfedu.repository;

import com.qfedu.pojo.NewsES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/9 09:33
 */

public interface NewsESRepository extends ElasticsearchRepository<NewsES,Integer> {

    //模糊查询
    List<NewsES> findByTitleLike(String title);
}
