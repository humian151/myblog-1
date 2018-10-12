package com.humian.blog.service;

import com.humian.blog.entity.Menu;
import com.humian.blog.entity.RoleMenu;
import com.humian.blog.entity.User;
import com.humian.blog.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户管理service
 * Created by DELL on 2018/9/13.
 */
public interface UserService {
    User getUserByUsername(String username);

    List<UserRole> getRolesByUserId(Long userId);

    List<RoleMenu> getMenuByRoles(Long roleId);

    List<Menu> getMenuByUserId(Long id);

    void updateUser(User user);

    Page<User> listUser(String name, Pageable pageable);

    void deleteUserBatch(String ids);
}
