package com.humian.blog.service.impl;

import com.humian.blog.dao.BlogRepository;
import com.humian.blog.dao.BlogTypeRepository;
import com.humian.blog.dao.CommentRepository;
import com.humian.blog.dto.BlogVO;
import com.humian.blog.entity.Blog;
import com.humian.blog.entity.BlogType;
import com.humian.blog.entity.Comment;
import com.humian.blog.lucene.BlogIndex;
import com.humian.blog.service.BlogService;
import com.humian.blog.utils.ConverObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/9/21.
 */
@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogTypeRepository blogTypeRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CommentRepository commentRepository;

    //lucene支持
    BlogIndex blogIndex = new BlogIndex();


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBlog(Blog blog) {
        BlogType blogType = blogTypeRepository.getOne(blog.getTypeid());
        blog.setReleasedate(new Date());
        blog.setBlogType(blogType);
        blogRepository.save(blog);
        try {
            blogIndex.addIndex( ConverObject.converBlogToBlogVo(blog));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlogBatch(String ids) {
        List<Long> list = new ArrayList<Long>();
        String[] split = ids.split(",");
        for(int i=0;i<split.length;i++){
            list.add(Long.parseLong(split[i]));
        }
        //deleteInBatch 无法触发级联删除评论，这里没办法只能采取delete方式
        List<Blog> allById = blogRepository.findAll((Iterable<Long>) list);
        for(Blog blog:allById){
            blogRepository.delete(blog);
            try {
                blogIndex.deleteIndex(blog.getId().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       // blogRepository.deleteInBatch(allById);
    }

    @Override
    public Page<Blog> queryBlog(Long typeid, String title,Pageable pageable) {
        if(!StringUtils.isEmpty(typeid) && typeid !=0 && !StringUtils.isEmpty(title) && !"null".equals(title)){
            title = "%"+title+"%";
            return blogRepository.findBlogByTypeidAndTitleLike(typeid,title,pageable);
        }else if(!StringUtils.isEmpty(typeid) && typeid !=0){
            //return blogRepository.queryBlogByTypename(typename,pageable);
            return blogRepository.findBlogByTypeid(typeid,pageable);
        }else if(!StringUtils.isEmpty(title) && !"null".equals(title)){
            title = "%"+title+"%";
            return blogRepository.findBlogByTitleLike(title,pageable);
        }else {
            return blogRepository.findAll(pageable);
        }
    }

    @Override
    public Blog queryBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BlogType addBlogType(BlogType blogType) {
        return blogTypeRepository.save(blogType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlogTypeBatch(String ids) {
        List<Long> list = new ArrayList<Long>();
        String[] split = ids.split(",");
        for(int i=0;i<split.length;i++){
            list.add(Long.parseLong(split[i]));
        }
        List<BlogType> allById = blogTypeRepository.findAll((Iterable<Long>) list);
        for (BlogType blogType:allById){
          blogTypeRepository.delete(blogType);
        }
        //blogTypeRepository.deleteInBatch(allById);
    }

    @Override
    public Page<BlogType> queryBlogType(String typename,Pageable pageable) {
        if( !StringUtils.isEmpty(typename) && !"null".equals(typename)){
            typename = "%"+typename+"%";
            return blogTypeRepository.findBlogTypeByTypenameLike(typename,pageable);
        }else {
            return blogTypeRepository.findAll(pageable);
        }

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCommentBatch(String ids) {
        List<Long> list = new ArrayList<Long>();
        String[] split = ids.split(",");
        for(int i=0;i<split.length;i++){
            list.add(Long.parseLong(split[i]));
        }
        List<Comment> allById = commentRepository.findAll((Iterable<Long>) list);
        commentRepository.deleteInBatch(allById);
    }

    @Override
    public Page<Comment> queryComment(Comment comment, Pageable pageable) {
        if(!StringUtils.isEmpty(comment.getState())){
            if(comment.getState() !=-1){
                return commentRepository.findCommentByState(comment.getState(),pageable);
            }else {
                return commentRepository.findAll(pageable);
            }
        }
        return null;
    }

    @Override
    public Page<Comment> queryComment(String content, String title, Long state, Pageable pageable) {
        if(!StringUtils.isEmpty(content)&& !"null".equals(content) && !StringUtils.isEmpty(title)&& !"null".equals(title)){
            return commentRepository.findCommentByConetentAndTitle(content,title,state,pageable);
        }else if(!StringUtils.isEmpty(content)&& !"null".equals(content)){
            content = "%"+content+"%";
            return commentRepository.findCommentByContentLikeAndState(content,state,pageable);
        }else if(!StringUtils.isEmpty(title)&& !"null".equals(title)){
            return commentRepository.findCommentByTitle(title,state,pageable);
        }else {
            return commentRepository.findCommentByState(state,pageable);
        }
    }

}
