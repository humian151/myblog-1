package com.humian.blog.lucene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humian.blog.dto.BlogVO;
import com.humian.blog.dto.DataTable;
import com.humian.blog.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IndexTask {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;
    //查询博客
    public final static String BLOG_QUERYBLOG ="http://localhost:8181/api/blog/queryBlog";

    BlogIndex blogIndex = new BlogIndex();

    @Scheduled(fixedDelay = 1000*60*60) //一小时执行一次
    public void addIndex() throws IOException {
        System.out.println("刷新查询index。。。");
        blogIndex.clear();
      String param = "?typeid=&title=&page="+1+"&limit="+10000;
       /*   DataTable<List<BlogVO>> result =this.restTemplate
                .exchange(BLOG_QUERYBLOG+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();*/
//      java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.humian.blog.dto.BlogVO

       /* Map<String, Object> map = new HashMap<>();
        map.put("typeid",null);
        map.put("title",null);
        map.put("page",1);
        map.put("limit",1000);*/
        DataTable forObject = restTemplate.getForObject(BLOG_QUERYBLOG+param, DataTable.class);


        List<BlogVO> list= (List<BlogVO>) forObject.getRows();
        try {
            ObjectMapper mapper=new ObjectMapper();
            for(int i=0;i<list.size();i++){
                BlogVO blog = mapper.convertValue(list.get(i),BlogVO.class);
                blogIndex.addIndex(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
