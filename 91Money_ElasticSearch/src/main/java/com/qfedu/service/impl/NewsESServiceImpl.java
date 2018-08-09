package com.qfedu.service.impl;

import com.qfedu.core.vo.R;
import com.qfedu.pojo.NewsES;
import com.qfedu.repository.NewsESRepository;
import com.qfedu.service.NewsESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/8/9 09:34
 */
@Service
public class NewsESServiceImpl implements NewsESService {
    @Autowired
    private NewsESRepository repository;


    @Override
    public R save(NewsES newsES) {
       if(repository.save(newsES)!=null)
       {
           return R.setOK("索引新增成功");
       }else {
           return R.setError("索引新增失败");
       }
    }

    @Override
    public R saveBatch(List<NewsES> list) {
        if(repository.saveAll(list)!=null){
            return R.setOK("批量更新索引成功");
        }else {
            return R.setError("批量更新索引失败");
        }
    }

    @Override
    public List<NewsES> queryByTitle(String title) {

        return repository.findByTitleLike(title);
    }

    @Override
    public R delBatch(List<Integer> ids) {
        List<NewsES> newsES=new ArrayList<>();
        for(Integer id:ids){
            newsES.add(new NewsES(id));
        }
        repository.deleteAll(newsES);
        return R.setOK("批量删除索引成功");
    }

    @Override
    public R del(int id) {
        repository.deleteById(id);
        return R.setOK("删除索引成功");
    }
}
