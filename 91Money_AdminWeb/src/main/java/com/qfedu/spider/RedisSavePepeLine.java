package com.qfedu.spider;


import com.qfedu.domain.news.News;
import com.qfedu.service.admin.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 *@Author feri
 *@Date Created in 2018/7/31 18:35
 * Console
 */
@Service
public class RedisSavePepeLine implements Pipeline {

    @Autowired
    private SpiderService spiderService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.err.println("s---->"+resultItems.getAll());
        if(resultItems.get("news")!=null){
            News news=resultItems.get("news");
            if(news.getTitle()!=null && news.getTitle().length()>0) {
                spiderService.saveNews(resultItems.get("news"));
            }
        }
    }
}
