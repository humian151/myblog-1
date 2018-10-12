package com.humian.blog.web;

import com.humian.blog.dto.BlogVO;
import com.humian.blog.dto.DataTable;
import com.humian.blog.dto.JsonResult;
import com.humian.blog.entity.Blog;
import com.humian.blog.entity.BlogType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.List;
import static com.humian.blog.utils.Contains.*;

/**
 * 博客管理类
 */
@Controller
@RequestMapping("/blogger")
public class BlogManagerController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;

    /**
     * 进入显示添加博文页面
     * @return
     */
    @GetMapping("/writeBlog")
    public ModelAndView showWriteBlog(ModelAndView modelAndView){
        String param = "?page="+1+"&limit="+1000;
        DataTable<List<BlogType>> blogTypes =this.restTemplate
                .exchange(BLOG_QUERYBLOGTYPE+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();
        modelAndView.addObject("blogTypes",blogTypes.getRows());
        modelAndView.setViewName("/admin/write");
        return modelAndView;
    }

    /**
     * 保存博文
     * @param blog
     * @return
     * @throws Exception
     */
    @PostMapping("/saveBlog")
    @ResponseBody
    public JsonResult saveBlog(@RequestBody Blog blog) throws Exception {
        if(blog.getId()==null){
            blog.setReleasedate(new java.util.Date());

        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            this.restTemplate
                    .exchange(BLOG_ADDBLOG,HttpMethod.POST,new HttpEntity<Object>(blog,headers), Blog.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }

    /**
     * 进入博文管理页面
     * @return
     */
    @GetMapping("/blogManage")
    public ModelAndView showBlogManage(ModelAndView modelAndView){
        String param = "?page="+1+"&limit="+1000;
        DataTable<List<BlogType>> blogTypes =this.restTemplate
                .exchange(BLOG_QUERYBLOGTYPE+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();
        modelAndView.addObject("blogTypes",blogTypes.getRows());
        modelAndView.setViewName("/admin/blogManage");
        return modelAndView;
    }
    /**
     * 查询博文
     * @param typeid
     * @param title
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/listBlog")
    @ResponseBody
    public Object listBlog(Long typeid,String title,Integer pageNumber, Integer pageSize){
//        String typename,String title,Integer page, Integer limit
        if(StringUtils.isEmpty(pageNumber)|| StringUtils.isEmpty(pageSize)){
            pageNumber=1;
            pageSize=10;
        }
        String param = "?typeid="+typeid+"&title="+title+"&page="+pageNumber+"&limit="+pageSize;
        Object result =this.restTemplate
                .exchange(BLOG_QUERYBLOG+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();

        return result;
    }
    /**
     * 进入显示修改博文页面
     * @return
     */
    @GetMapping("/modifyBlog/{id}")
    public ModelAndView showModifyBlog(@PathVariable Long id,ModelAndView modelAndView){
        String param = "?page="+1+"&limit="+1000;
        DataTable<List<BlogType>> blogTypes =this.restTemplate
                .exchange(BLOG_QUERYBLOGTYPE+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();


        Blog blog =this.restTemplate
                .exchange(BLOG_QUERYBLOG+"/"+id, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), Blog.class).getBody();

        modelAndView.addObject("blogTypes",blogTypes.getRows());
        modelAndView.addObject("blog",blog);
        modelAndView.setViewName("/admin/modifyBlog");
        return modelAndView;
    }

    /**
     * 删除博客
     * @param ids
     * @return
     */
    @PostMapping("/delBlog")
    @ResponseBody
    public JsonResult deleteBlog(String ids){
        try {
            this.restTemplate
                    .exchange(BLOG_DELETEBLOG+"?ids="+ids,HttpMethod.POST,new HttpEntity<Object>(this.headers), String.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }

    /**
     * 展示博客类别页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/showBlogTypeManage")
    public ModelAndView showBlogTypePage(ModelAndView modelAndView){
        modelAndView.setViewName("/admin/blogTypeManage");
        return modelAndView;
    }
    /**
     * 查询博客类别
     * @param typename
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/listBlogType")
    @ResponseBody
    public Object listBlogType(String typename,Integer pageNumber, Integer pageSize){
        if(StringUtils.isEmpty(pageNumber)|| StringUtils.isEmpty(pageSize)){
            pageNumber=1;
            pageSize=10;
        }
        String param = "?typename="+typename+"&page="+pageNumber+"&limit="+pageSize;
        Object result =this.restTemplate
                .exchange(BLOG_QUERYBLOGTYPE+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();

        return result;
    }
    /**
     * 保存博客类别
     * @param blogType
     * @return
     * @throws Exception
     */
    @PostMapping("/saveBlogType")
    @ResponseBody
    public JsonResult saveBlogType(@RequestBody BlogType blogType) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            this.restTemplate
                    .exchange(BLOG_ADDBLOGTYPE,HttpMethod.POST,new HttpEntity<Object>(blogType,headers), Blog.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }

    /**
     * 删除博客类别
     * @param ids
     * @return
     */
    @PostMapping("/delBlogType")
    @ResponseBody
    public JsonResult deleteBlogType(String ids){
        try {
            this.restTemplate
                    .exchange(BLOG_DELETEBLOGTYPE+"?ids="+ids,HttpMethod.POST,new HttpEntity<Object>(this.headers), String.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }


}
