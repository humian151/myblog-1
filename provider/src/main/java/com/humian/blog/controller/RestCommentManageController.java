package com.humian.blog.controller;

import com.humian.blog.dto.CommentVO;
import com.humian.blog.dto.DataTable;
import com.humian.blog.entity.Comment;
import com.humian.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论管理对外公布接口
 * Created by DELL on 2018/10/9.
 */
@RestController
@RequestMapping("/api/comment")
public class RestCommentManageController {
    @Autowired
    private BlogService blogService;

    @PostMapping(value = "/addComment", consumes = "application/json")
    public void addComment(@RequestBody Comment comment) {
        blogService.addComment(comment);
    }

    @GetMapping("/queryComment")
    public DataTable<List<CommentVO>> queryBlog(String content, String title, Long state,Integer page, Integer limit) {
        Pageable pageable = new PageRequest(page - 1, limit, new Sort(Sort.Direction.DESC,"commentdate"));
        Page<Comment> pages =blogService.queryComment(content,title,state,pageable);
        List<Comment> data = pages.getContent();
        List<CommentVO> vodata = new ArrayList<CommentVO>();
        for (Comment comment : data) {
            CommentVO vo = new CommentVO();
            vo.setId(comment.getId());
            vo.setTitle(comment.getBlog().getTitle());
            vo.setContent(comment.getContent());
            vo.setBlogid(comment.getBlog().getId());
            vo.setCommentdate(comment.getCommentdate());
            vo.setUserip(comment.getUserip());
            vo.setState(comment.getState());
            vodata.add(vo);
        }
        DataTable<List<CommentVO>> result = new DataTable<List<CommentVO>>((int) pages.getTotalElements(), vodata);
        return result;
    }

    @PostMapping("/deleteComment")
    public void deleteCommentBatch(String ids) {
        blogService.deleteCommentBatch(ids);
    }

}
