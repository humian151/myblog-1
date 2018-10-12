package com.humian.blog.dao;

import com.humian.blog.entity.BlogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by DELL on 2018/8/28.
 */
public interface BlogTypeRepository extends JpaRepository<BlogType,Long> {
    @Query(nativeQuery=true,value = "SELECT   t.typeId typeId,  (SELECT b.typename FROM t_blogtype b WHERE b.id = t.typeId) AS typename,CAST(COUNT(1) AS SIGNED INTEGER) count FROM  t_blog t WHERE 1=1 GROUP BY t.typeId ")
    List<Object[]> queryBlogTypeVo();
    Page<BlogType> findBlogTypeByTypenameLike(String typename, Pageable pageable);
}
