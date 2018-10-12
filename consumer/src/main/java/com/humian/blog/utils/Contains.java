package com.humian.blog.utils;

/**
 * 公共配置类
 * Created by DELL on 2018/9/14.
 */
public final class Contains {
    //通过用户名获取用户
    public final static String USER_GETUSERBYUSERNAME = "http://localhost:8181/api/user/getUserByUsername";
    //通过用户ID获取角色
    public final static String USER_GETROLESBYUSERID = "http://localhost:8181/api/user/getRolesByUserId";
    //通过用户id获取菜单
    public final static String USER_GETMENUBYUSERID = "http://localhost:8181/api/user/getMenuByUserId";
    //通过角色id获取菜单
    public final static String USER_GETMENUBYROLEID = "http://localhost:8181/api/user/getMenuByRoleId";

    //查询用户
    public final static String USER_LISTUSER = "http://localhost:8181/api/user/listUser";
    //删除用户
    public final static String USER_DELETEUSER = "http://localhost:8181/api/user/deleteUser";

    //更新用户
    public final static String USER_UPDATEUSER = "http://localhost:8181/api/user/updateUser";
    //获取菜单
    public final static String INDEX_LOADMENU = "http://localhost:8181/api/load/loadMenu";


    //新增博客
    public final static String BLOG_ADDBLOG ="http://localhost:8181/api/blog/addBlog";
    //删除博客
    public final static String BLOG_DELETEBLOG ="http://localhost:8181/api/blog/deleteBlog";
    //查询博客
    public final static String BLOG_QUERYBLOG ="http://localhost:8181/api/blog/queryBlog";
    //查询博客类别
    public final static String BLOG_QUERYBLOGTYPE ="http://localhost:8181/api/blog/queryBlogType";
    //新增博客类别
    public final static String BLOG_ADDBLOGTYPE ="http://localhost:8181/api/blog/addBlogType";
    //删除博客类别
    public final static String BLOG_DELETEBLOGTYPE ="http://localhost:8181/api/blog/deleteBlogType";

    //修改评论
    public final static String COMMENT_ADDCOMMENT ="http://localhost:8181/api/comment/addComment";
    //查询评论
    public final static String COMMENT_QUERYCOMMENT ="http://localhost:8181/api/comment/queryComment";
    //删除评论
    public final static String COMMENT_DELETECOMMENT ="http://localhost:8181/api/comment/deleteComment";





}
