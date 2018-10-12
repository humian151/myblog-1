package com.humian.blog.controller;

import com.humian.blog.dto.BlogVO;
import com.humian.blog.dto.DataTable;
import com.humian.blog.entity.Blog;
import com.humian.blog.entity.BlogType;
import com.humian.blog.service.BlogService;
import com.humian.blog.utils.ConverObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 对外公布接口博客类
 * Created by DELL on 2018/9/21.
 */
@RestController
@RequestMapping("/api/blog")
public class RestBlogManagerController {
    @Autowired
    private BlogService blogService;

    @PostMapping(value = "/addBlog", consumes = "application/json")
    public void addBlog(@RequestBody Blog blog) {
        blog.setContent(filterEmoji(blog.getContent()));
        blogService.addBlog(blog);
    }

    @GetMapping("/queryBlog")
    public DataTable<List<BlogVO>> queryBlog(Long typeid, String title, Integer page, Integer limit) {
        Pageable pageable = new PageRequest(page - 1, limit, new Sort(Sort.Direction.DESC,"id"));
        Page<Blog> blogs = blogService.queryBlog(typeid, title, pageable);
        List<Blog> data = blogs.getContent();
        List<BlogVO> vodata = new ArrayList<BlogVO>();
        for (Blog blog : data) {
           /* BlogVO vo = new BlogVO();
            vo.setId(blog.getId());
            vo.setReleasedate(blog.getReleasedate());
            vo.setTitle(blog.getTitle());
            vo.setTypename(blog.getBlogType().getTypename());
            vo.setTypeid(blog.getTypeid());
            vo.setContent(blog.getContent());
            vo.setKeyword(blog.getKeyword());
            vo.setSummary(blog.getSummary());
            vo.setChickhit(blog.getChickhit());
            vo.setReplyhit(blog.getReplyhit());*/
            vodata.add(ConverObject.converBlogToBlogVo(blog));
        }
        DataTable<List<BlogVO>> result = new DataTable<List<BlogVO>>((int) blogs.getTotalElements(), vodata);
        return result;
    }
    @GetMapping("/queryBlog/{id}")
    public Blog queryBlog(@PathVariable Long id){
        return blogService.queryBlog(id);
    }

    @PostMapping("/deleteBlog")
    public void deleteBlogBatch(String ids) {
        blogService.deleteBlogBatch(ids);
    }

    @GetMapping("/queryBlogType")
    public DataTable<List<BlogType>> queryBlogType( String typename,Integer page, Integer limit) {
        Pageable pageable = new PageRequest(page - 1, limit, new Sort("orderno"));
        Page<BlogType> blogTypes = blogService.queryBlogType(typename,pageable);
        DataTable<List<BlogType>> result = new DataTable<List<BlogType>>((int)blogTypes.getTotalElements(),blogTypes.getContent());
        return result;
    }
    @PostMapping(value = "/addBlogType", consumes = "application/json")
    public void addBlogType(@RequestBody BlogType blogType) {
        blogService.addBlogType(blogType);
    }
    @PostMapping("/deleteBlogType")
    public void deleteBlogTypeBatch(String ids) {
        blogService.deleteBlogTypeBatch(ids);
    }




    /**
     * 将emoji表情替换成空串  *    * @param source  * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;
        }
    }

}
