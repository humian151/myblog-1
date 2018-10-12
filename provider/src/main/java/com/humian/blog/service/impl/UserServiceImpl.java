package com.humian.blog.service.impl;

import com.humian.blog.dao.MenuRepository;
import com.humian.blog.dao.RoleMenuRepository;
import com.humian.blog.dao.UserRepository;
import com.humian.blog.dao.UserRoleRepository;
import com.humian.blog.entity.Menu;
import com.humian.blog.entity.RoleMenu;
import com.humian.blog.entity.User;
import com.humian.blog.entity.UserRole;
import com.humian.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/9/13.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleMenuRepository roleMenuRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserRole> getRolesByUserId(Long userId) {
        return userRoleRepository.findUserRoleByUserId(userId);
    }

    @Override
    public List<RoleMenu> getMenuByRoles(Long roleId) {
        return roleMenuRepository.findRoleMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> getMenuByUserId(Long userid) {
        return menuRepository.findMenuByUserId(userid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Page<User> listUser(String name, Pageable pageable) {
        if(!StringUtils.isEmpty(name)&&!"null".equals(name)){
            name = "%"+name+"%";
            return userRepository.findUserByNameLike(name,pageable);
        }else {
            return userRepository.findAll(pageable);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserBatch(String ids) {
        List<Long> list = new ArrayList<Long>();
        String[] split = ids.split(",");
        for(int i=0;i<split.length;i++){
            list.add(Long.parseLong(split[i]));
        }
        List<User> allById = userRepository.findAll((Iterable<Long>) list);
        userRepository.deleteInBatch(allById);
    }
}
