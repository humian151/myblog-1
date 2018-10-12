package com.humian.blog.controller;

import com.humian.blog.dto.MenuVo;
import com.humian.blog.entity.Menu;
import com.humian.blog.entity.User;
import com.humian.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台首页初始化管理
 * Created by DELL on 2018/9/14.
 */
@RestController
@RequestMapping("/api/load")
public class RestIndexController {
    @Autowired
    private UserService userService;
    /**
     * 载入菜单
     * @return
     */
    @GetMapping("/loadMenu")
    public List<MenuVo> loadMenu(Long userId){
        List<Menu> menus = userService.getMenuByUserId(userId);
        List<MenuVo> list = new ArrayList<>();
        for(Menu menu : menus){
            if(menu.getParentId()==0){
                //一级菜单
                MenuVo parentMenu = new MenuVo();
                parentMenu.setTitle(menu.getMenuName());
                parentMenu.setIcon(menu.getIcon());
                parentMenu.setSpread(false);
                parentMenu.setHref(menu.getHref());
                List<MenuVo> childList = new ArrayList<>();
                for(Menu child : menus){
                    if(child.getParentId() == menu.getId()){
                        MenuVo childMenu = new MenuVo();
                        childMenu.setTitle(child.getMenuName());
                        childMenu.setIcon(child.getIcon());
                        childMenu.setSpread(false);
                        childMenu.setHref(child.getHref());
                        childList.add(childMenu);
                    }
                }
                parentMenu.setChildren(childList);
                list.add(parentMenu);
            }
        }
        return list;
    }
}
