package com.qfedu.pojo;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 *@Author feri
 *@Date Created in 2018/8/9 09:30
 */
@Document(indexName = "91moneynews",type = "news")
public class NewsES {

    private int id;

    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NewsES() {
    }

    public NewsES(int id) {
        this.id = id;
    }
}
