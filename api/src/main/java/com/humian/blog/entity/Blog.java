package com.humian.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/7/30.
 */
@Entity
@Table(name = "t_blog")
public class Blog implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String summary;
    @Temporal(TemporalType.TIMESTAMP)
    private Date releasedate;
    private Long chickhit;
    private Long replyhit;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeid",insertable = false,updatable = false)
    private BlogType blogType;

   // @OneToMany(fetch = FetchType.EAGER, cascade = {/*CascadeType.REMOVE,*/CascadeType.PERSIST},mappedBy = "blog")
    //级联删除
   @OneToMany(fetch = FetchType.LAZY,mappedBy="blog",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<Comment>();

    //@Transient
    private Long typeid;

    private String keyword;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogType getBlogType() {
        return blogType;
    }

    public void setBlogType(BlogType blogType) {
        this.blogType = blogType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
