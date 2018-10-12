package com.humian.blog.dto;

import com.humian.blog.entity.Blog;

import java.util.Date;

/**
 * Created by DELL on 2018/9/26.
 */
public class BlogVO {
    private Long id;
    private String title;
    private Date releasedate;
    private String typename;
    private String content;
    private String summary;
    private String keyword;
    private Long chickhit;
    private Long replyhit;
    private Long typeid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getChickhit() {
        return chickhit;
    }

    public void setChickhit(Long chickhit) {
        this.chickhit = chickhit;
    }

    public Long getReplyhit() {
        return replyhit;
    }

    public void setReplyhit(Long replyhit) {
        this.replyhit = replyhit;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }
}
