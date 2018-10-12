package com.humian.blog.dao;

import com.humian.blog.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by DELL on 2018/8/28.
 */
public interface UserRoleRepository extends JpaRepository<UserRole,Long>{
    List<UserRole> findUserRoleByUserId(Long userId);
}
