package com.qfedu.service;

import com.qfedu.core.vo.R;
import com.qfedu.pojo.NewsES;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/9 09:34
 */
public interface NewsESService {

    //添加
    R save(NewsES newsES);
    R saveBatch(List<NewsES> list);

    //查询 模糊
    List<NewsES> queryByTitle(String title);

    //删除
    R delBatch(List<Integer> ids);
    R del(int id);
}
