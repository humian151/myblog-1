package com.humian.blog.dao;

import com.humian.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog,Long>{
    Page<Blog> findBlogByTitleLike(String title, Pageable pageable);

    //@Query("select t from Blog t , BlogType b where t.typeid = b.id  and b.typename=:typename")
    @Query("select t from Blog t where  t.blogType.typename=:typename")
    Page<Blog> queryBlogByTypename(@Param(value = "typename") String typename, Pageable pageable);

    Page<Blog> findBlogByTypeid(Long typeid, Pageable pageable);

    Page<Blog> findBlogByTypeidAndTitleLike(Long typeid, String title, Pageable pageable);
}
