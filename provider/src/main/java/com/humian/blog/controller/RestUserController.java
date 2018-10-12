package com.humian.blog.controller;

import com.humian.blog.dto.DataTable;
import com.humian.blog.entity.Menu;
import com.humian.blog.entity.RoleMenu;
import com.humian.blog.entity.User;
import com.humian.blog.entity.UserRole;
import com.humian.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息
 * Created by DELL on 2018/9/13.
 */
@RestController
@RequestMapping("/api/user")
public class RestUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam(name = "username",required = true) String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/listUser")
    public DataTable<List<User>> listUser(String name,Integer page, Integer limit){
        Pageable pageable = new PageRequest(page - 1, limit, new Sort(Sort.Direction.ASC,"id"));
        Page<User> users =userService.listUser(name,pageable);
        DataTable<List<User>> result = new DataTable<List<User>>((int) users.getTotalElements(), users.getContent());
        return result;
    }

    @PostMapping(value = "/updateUser", consumes = "application/json")
    public void updateUser(@RequestBody User user){
        try {
            userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/deleteUser")
    public void deleteUserBatch(String ids) {
        userService.deleteUserBatch(ids);
    }

    @GetMapping("/getRolesByUserId")
    public List<UserRole> getRolesByUserId(Long userId){
        return userService.getRolesByUserId(userId);
    }

    @GetMapping("/getMenuByUserId")
    public List<Menu> getMenuByUserId(Long userId){
        return userService.getMenuByUserId(userId);
    }

    @GetMapping("/getMenuByRoleId")
    public List<RoleMenu> getMenuByRoles(Long roleId){
        return userService.getMenuByRoles(roleId);
    }


}
