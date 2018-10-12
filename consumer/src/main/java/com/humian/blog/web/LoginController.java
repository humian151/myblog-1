package com.humian.blog.web;

import com.humian.blog.dto.DataTable;
import com.humian.blog.dto.JsonResult;
import com.humian.blog.entity.User;
import com.humian.blog.shiro.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static com.humian.blog.utils.Contains.*;

/**
 * 登录控制器
 * Created by DELL on 2018/9/14.
 */
@Controller
public class LoginController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;
    /**
     * 显示登录页
     * @return
     */
    @GetMapping("/login")
    public String showLogin(){
        return "/login";
    }
    /**
     * 进入主页
     * @return
     */
    @GetMapping("/home")
    public String showHome(){
        return "/home";
    }

    @GetMapping("/index")
    public String showIndex(){
        return "/index";
    }

    /**
     * 后台登录程序
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping("/subLogin")
    @ResponseBody
    public JsonResult subLogin(String username, String password, @RequestParam(name = "rememberMe",required = false) String rememberMe){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            if("on".equals(rememberMe)){
                token.setRememberMe(true);
            }else {
                token.setRememberMe(false);
            }
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return JsonResult.errorMsg("用户名或密码错误！");
        }
        return JsonResult.ok();
    }

    /**
     * 后台登出程序
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    /**
     * 显示修改个人信息
     * @param modelAndView
     * @return
     */
    @GetMapping("/user/showModifyInfo")
    public ModelAndView showModifyInfo(ModelAndView modelAndView){
        modelAndView.setViewName("/showModifyInfo");
        return modelAndView;
    }
    /**
     * 查看用户信息
     * @param name
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/user/listModifyInfo")
    @ResponseBody
    public Object listModifyInfo(String name,Integer pageNumber, Integer pageSize){
        if(StringUtils.isEmpty(pageNumber)|| StringUtils.isEmpty(pageSize)){
            pageNumber=1;
            pageSize=10;
        }
        String param = "?name="+name+"&page="+pageNumber+"&limit="+pageSize;
        Object result =this.restTemplate
                .exchange(USER_LISTUSER+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();

        return result;
    }
    /**
     * 保存用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/user/saveUser")
    @ResponseBody
    public JsonResult saveUser(@RequestBody User user) throws Exception {
        if(user.getId()==null){
            //新增,头像初始化，密码初始化1234
            user.setAvatar("image/face.jpg");
            user.setPassword("46cc0f3868613d56819b38a36f2ad93e");
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            this.restTemplate
                    .exchange(USER_UPDATEUSER,HttpMethod.POST,new HttpEntity<Object>(user,headers), User.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }

    /**
     * 删除用户信息
     * @param ids
     * @return
     */
    @PostMapping("/user/deleteUser")
    @ResponseBody
    public JsonResult deleteUser(String ids){
        try {
            this.restTemplate
                    .exchange(USER_DELETEUSER+"?ids="+ids,HttpMethod.POST,new HttpEntity<Object>(this.headers), String.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }
    /**
     * 显示修改密码
     * @param modelAndView
     * @return
     */
    @GetMapping("/changePwd")
    public ModelAndView changePwd(ModelAndView modelAndView){
        modelAndView.setViewName("/changePwd");
        return modelAndView;
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public JsonResult updatePwd(String oldpassword,String password){
         oldpassword =new Md5Hash(oldpassword, CustomRealm.SALT).toString();
         password =new Md5Hash(password, CustomRealm.SALT).toString();
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute("currUser");
        if(!oldpassword.equals(user.getPassword())){
            return JsonResult.errorMsg("输入旧密码错误");
        }
        user.setPassword(password);
        try {
            this.restTemplate
                    .exchange(USER_UPDATEUSER,HttpMethod.POST,new HttpEntity<Object>(user,headers), User.class)
                    .getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        //重新更新session中的密码
        session.setAttribute("currUser",user);

        return JsonResult.ok();

    }


}
