package com.humian.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/8/1.
 */

@Entity
@Table(name = "t_blogtype")
public class BlogType  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typename;
    private Long orderno;
    //添加级联删除博客功能
   /* @JsonIgnoreProperties(value = { "blogtype" })
    @OneToMany(fetch = FetchType.LAZY,mappedBy="blogType",cascade = CascadeType.ALL)
    private List<Blog> blogs = new ArrayList<Blog>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Long getOrderno() {
        return orderno;
    }

    public void setOrderno(Long orderno) {
        this.orderno = orderno;
    }

   /* public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }*/
}
