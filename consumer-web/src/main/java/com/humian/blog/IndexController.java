package com.humian.blog;

import com.humian.blog.dto.BlogTypeVo;
import com.humian.blog.dto.BlogVO;
import com.humian.blog.dto.DataTable;
import com.humian.blog.entity.Blog;
import com.humian.blog.entity.BlogType;
import com.humian.blog.lucene.BlogIndex;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by DELL on 2018/10/10.
 */
@Controller
public class IndexController {
    //查询博客类别
    public final static String BLOG_QUERYBLOGTYPE ="http://localhost:8181/api/blog/queryBlogType";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;
    BlogIndex blogIndex = new BlogIndex();
    /**
     * 显示主页
     * @return
     */
    @GetMapping(value = {"/index",""})
    public ModelAndView showIndex() throws Exception {
        ModelAndView mv = new ModelAndView();
        Integer count = blogIndex.searchAllBlogCount();
        mv.addObject("count",count);
        String param = "?page="+1+"&limit="+1000;
        DataTable<List<BlogType>> blogTypes =this.restTemplate
                .exchange(BLOG_QUERYBLOGTYPE+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();
        mv.addObject("blogTypes",blogTypes.getRows());
        mv.setViewName("/index");
        return mv;
    }

    /**
     * 主页博文页
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = {"/front/menu"})
    public ModelAndView showMenu(Integer page ,Integer limit,@RequestParam(value = "typename",required = false,defaultValue = "11") String typename) throws Exception {
       /* if(page ==null || limit ==null){
            page=0;
            limit=10;
        }
        Pageable pageable = new PageRequest(page,limit,new Sort(Sort.Direction.DESC,"id"));
        List<Blog> blogs =null;
        if("11".equals(typename)){
            blogs = bloggerService.queryBlog("",pageable).getContent();
        }else {
            blogs = bloggerService.queryBlogByTypename(typename,pageable).getContent();
        }*/

        List<BlogVO> blogs = null;
        if("11".equals(typename)){
            blogs = blogIndex.searchBlog();
        }else {
            blogs = blogIndex.searchBlogByTypename(typename);
        }
        //0 0~9 1 10~19 2 20~29
        List<BlogVO> result = new ArrayList<>();
        for (int i=0;i<limit;i++){
            //循环limit次数，如10，则循环10次
            BlogVO blog = null;
            try {
                blog=blogs.get(page*limit+i);
            } catch (Exception e) {
            }
            if(blog !=null){
                result.add(blog);
            }else {
                break;
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("blogs",result);

        mv.setViewName("/front/menu");
        return mv;
    }

    /**
     * 博客类别页
     * @return
     */
    @GetMapping(value = {"/front/blogtype"})
    public ModelAndView showBlogType(){

        List<BlogTypeVo> blogtypes = null;
        try {
            List<BlogVO> blogs = blogIndex.searchBlog();
            String[] typenames = new String[blogs.size()];
            for(int i=0;i<blogs.size();i++){
                typenames[i] = blogs.get(i).getTypename();
            }
            //统计元素出现次数
            Map<String, Integer> map = new HashMap<>();
            for (String str : typenames) {
                Integer num = map.get(str);
                map.put(str, num == null ? 1 : num + 1);
            }
            blogtypes = new ArrayList<>();
            Iterator it01 = map.keySet().iterator();
            while (it01.hasNext()) {
                Object key = it01.next();
                //System.out.println("单词 " + key + " 出现次数 : " + map.get(key));
                BlogTypeVo vo = new BlogTypeVo();
                vo.setTypename(key+"");
                vo.setCount(map.get(key));
                blogtypes.add(vo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("blogtypes",blogtypes);

        mv.setViewName("/front/blogtype");
        return mv;
    }

    /**
     * 查询页
     * @param q
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/front/result")
    public ModelAndView showResult(String q,Integer page ,Integer limit) throws Exception {
        if(page ==null || limit ==null){
            page=0;
            limit=10;
        }
       // Pageable pageable = new PageRequest(page,limit,new Sort(Sort.Direction.DESC,"id"));
        // List<Blog> blogs = bloggerService.queryBlog(q, pageable).getContent();
        List<BlogVO> blogs = blogIndex.searchBlog(q.trim());
        ModelAndView mv = new ModelAndView();
        mv.addObject("count",blogs.size());
        mv.addObject("blogs",blogs);
        mv.addObject("q",q);
        mv.setViewName("/front/result");
        return mv;
    }

    /**
     * 显示博文详情页
     * @return
     * @throws Exception
     */
    @GetMapping("/front/view/{id}")
    public ModelAndView showView(@PathVariable Long id) throws Exception {
        BlogVO blog=blogIndex.searchBlog(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("blog",blog);
        mv.setViewName("/front/view");
        return mv;
    }


}
