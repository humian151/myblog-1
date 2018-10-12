package com.humian.blog.dao;

import com.humian.blog.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by DELL on 2018/8/28.
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu ,Long>{
    List<RoleMenu> findRoleMenuByRoleId(Long roleId);
}
