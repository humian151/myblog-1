package com.humian.blog.service;

import com.humian.blog.entity.Blog;
import com.humian.blog.entity.BlogType;
import com.humian.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 博客Service 包含
 * 1.博客增删改查
 * 2.博客类别增删改查
 * 3.博客评论增删改查
 * Created by DELL on 2018/9/21.
 */
public interface BlogService {
    /**
     * 新增博客
     * @param blog
     * @return
     */
    public void addBlog(Blog blog);

    /**
     * 删除博客
     * @param ids
     */
    public void deleteBlogBatch(String ids);

    /**
     * 查询博客
     * @param typeid
     * @param title
     * @param pageable
     * @return
     */
    public Page<Blog> queryBlog(Long typeid,String title, Pageable pageable);

    public Blog queryBlog(Long id);

    /**
     * 新增博客类别
     * @param blogType
     * @return
     */
    public BlogType addBlogType(BlogType blogType);

    /**
     * 删除博客类别
     * @param ids
     */
    public void deleteBlogTypeBatch(String ids);


    /**
     * 查询博客类别
     * @return
     */
    public Page<BlogType> queryBlogType(String typename,Pageable pageable);

    /**
     * 新增评论
     * @param comment
     * @return
     */
    public Comment addComment(Comment comment);


    /**
     * 删除评论
     * @param ids
     */
    public void deleteCommentBatch(String ids);

    /**
     * 查询评论
     * @param comment
     * @param pageable
     * @return
     */
    public Page<Comment> queryComment(Comment comment,Pageable pageable);


    Page<Comment> queryComment(String content, String title, Long state, Pageable pageable);
}
