package com.humian.blog.utils;

import com.humian.blog.dto.BlogVO;
import com.humian.blog.entity.Blog;

/**
 * 类转换
 * Created by DELL on 2018/10/10.
 */
public final class ConverObject {
    /**
     * blog 转BlogVo
     * @param blog
     * @return
     */
    public static BlogVO converBlogToBlogVo(Blog blog){
        BlogVO vo = new BlogVO();
        vo.setId(blog.getId());
        vo.setReleasedate(blog.getReleasedate());
        vo.setTitle(blog.getTitle());
        vo.setTypename(blog.getBlogType().getTypename());
        vo.setTypeid(blog.getTypeid());
        vo.setContent(blog.getContent());
        vo.setKeyword(blog.getKeyword());
        vo.setSummary(blog.getSummary());
        vo.setChickhit(blog.getChickhit());
        vo.setReplyhit(blog.getReplyhit());
        return vo;
    }
    /**
     * blogvo 转Blog
     * @param blog
     * @return
     */
    public static Blog converBlogVoToBlog(BlogVO blog){
        Blog vo = new Blog();
        vo.setId(blog.getId());
        vo.setReleasedate(blog.getReleasedate());
        vo.setTitle(blog.getTitle());
        vo.setTypeid(blog.getTypeid());
        vo.setContent(blog.getContent());
        vo.setKeyword(blog.getKeyword());
        vo.setSummary(blog.getSummary());
        vo.setChickhit(blog.getChickhit());
        vo.setReplyhit(blog.getReplyhit());
        return vo;
    }
}
