package com.qfedu.domain.news;

import java.util.Date;

/**
 *@Author feri
 *@Date Created in 2018/7/31 18:24
 */
public class News {
    private int id;
    private String title;
    private String lastTime;
    private String sourcename;
    private String sourceurl;
    private String contentHtml;
    private String refHtml;
    private Date createTime;
    private int flag;//0未生成 1生成

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    //create table t_news(id int primary key auto_increment,title varchar(50),lastTime varchar(30),sourcename varchar(30),sourceurl varchar(50),contentHtml text,refHtml text);

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

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getRefHtml() {
        return refHtml;
    }

    public void setRefHtml(String refHtml) {
        if(refHtml==null){
            refHtml="<h1>暂无关联</h1>";
        }
        this.refHtml = refHtml;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", sourcename='" + sourcename + '\'' +
                ", sourceurl='" + sourceurl + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                ", refHtml='" + refHtml + '\'' +
                ", createTime=" + createTime +
                ", flag=" + flag +
                '}';
    }
}
