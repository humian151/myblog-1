package com.humian.blog.dao;

import com.humian.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by DELL on 2018/8/28.
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);


    Page<User> findUserByNameLike(String name, Pageable pageable);
}
