package com.humian.blog.dao;

import com.humian.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by DELL on 2018/8/28.
 */
public interface CommentRepository extends JpaRepository<Comment,Long>{
    Page<Comment> findCommentByState(Long state, Pageable pageable);

    @Query(value = "select c from Comment c where  c.state=:state and c.content like %:content% and c.blog.title like %:title% ",
            countQuery = "select count(c) from Comment c where  c.state=:state and c.content like %:content% and c.blog.title like %:title% "
    )
    Page<Comment> findCommentByConetentAndTitle(@Param("content")String content, @Param("title") String title,@Param("state") Long state, Pageable pageable);



    @Query(value = "select c from Comment c where c.state=:state and c.blog.title like %:title% ",
            countQuery = "select count(c) from Comment c where c.state=:state and c.blog.title like %:title% "
    )
    Page<Comment> findCommentByTitle(@Param("title") String title,@Param("state") Long state, Pageable pageable);

    Page<Comment> findCommentByContentLikeAndState(String content, Long state, Pageable pageable);
}
