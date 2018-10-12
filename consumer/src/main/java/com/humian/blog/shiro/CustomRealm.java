package com.humian.blog.shiro;
import com.humian.blog.dto.MenuVo;
import com.humian.blog.entity.RoleMenu;
import com.humian.blog.entity.User;
import com.humian.blog.entity.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static com.humian.blog.utils.Contains.*;
/**
 * 自定义realm
 * Created by DELL on 2018/9/14.
 */
public class CustomRealm  extends AuthorizingRealm {


    public static final String SALT = "1qjjfei23@#jfSSjSS";
    public CustomRealm() {
        super.setName("customRealm");
    }

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;
    /**
     * 角色权限配置
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        User user = this.restTemplate
                .exchange(USER_GETUSERBYUSERNAME + "?username=" + userName, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), User.class).getBody();
        List<UserRole> userRoles = this.restTemplate
                .exchange(USER_GETROLESBYUSERID + "?userId=" + user.getId(), HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), List.class).getBody();

        Set<String> menus = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        for(UserRole userRole : userRoles){
            roles.add(userRole.getRoleId()+"");
            List<RoleMenu> menuList = this.restTemplate
                    .exchange(USER_GETMENUBYROLEID + "?roleId=" + userRole.getRoleId(), HttpMethod.GET,
                            new HttpEntity<Object>(this.headers), List.class).getBody();

            for(RoleMenu menu:menuList){
                menus.add(menu.getMenuId()+"");
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(menus);

        return null;
    }

    /**
     * 登录配置
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        User user = this.restTemplate
                .exchange(USER_GETUSERBYUSERNAME + "?username=" + username, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), User.class).getBody();
        if(user !=null){
            List<MenuVo> menu = this.restTemplate
                    .exchange(INDEX_LOADMENU + "?userId=" + user.getId(), HttpMethod.GET,
                            new HttpEntity<Object>(this.headers), List.class).getBody();
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("currUser",user);
            session.setAttribute("menus",menu);
        }
        SimpleAuthenticationInfo info =  new SimpleAuthenticationInfo(username, user.getPassword().toCharArray(), "customRealm");
        info.setCredentialsSalt(ByteSource.Util.bytes(SALT));//加盐
        return info;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1234",SALT);
        System.out.println(md5Hash.toString());//46cc0f3868613d56819b38a36f2ad93e
    }
}
