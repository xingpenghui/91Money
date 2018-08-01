package com.qfedu.mapper.news;

import com.qfedu.domain.news.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2018/7/31 19:08
 */
public interface NewsMapper {
    @Insert("insert into t_news(title,lastTime,sourcename,sourceurl,contentHtml,refHtml,createtime,flag) values(#{title},#{lastTime},#{sourcename},#{sourceurl},#{contentHtml},#{refHtml},now(),#{flag})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(News news);

    @Select("select * from t_news where flag=#{flag} order by lastTime desc")
    @ResultType(News.class)
    List<News> selectAll(int flag);



}
