package com.humian.reptile;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import com.humian.blog.entity.Blog;
import com.humian.dao.BlogRepository;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/10/15.
 */
@Crawler(name = "jianshu")
public class JianShu extends BaseSeimiCrawler {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public String[] startUrls() {
        //两个是测试去重的
        // return new String[]{"https://www.jianshu.com/"};
        return new String[]{"https://www.jianshu.com/c/V2CqjW?utm_medium=index-collections&utm_source=desktop/"};
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            List<Object> urls = doc.sel("//a[@class='title']/@href");
            logger.info("{}", urls.size());
            for (Object s:urls){
                push(Request.build("https://www.jianshu.com/"+s.toString(), JianShu::getTitle));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
            //logger.info("url:{} {}", response.getUrl(), doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()"));
            //System.out.println("-----------resurl:"+response.getUrl());
            List<Object> content = doc.sel("//div[@class='article']/div[@class='show-content']/html()");
            List<Object> title = doc.sel("//div[@class='article']/h1[@class='title']/html()");
            List<Object> keyword = doc.sel("//meta[@name='description']/@content");
            //System.out.println("---"+sel.get(0).toString());
            String ncontent = content.get(0).toString();
            ncontent=ncontent.replaceAll("data-original-src","src")
                             .replaceAll("=\"//","=\"http://");


            Blog blog = new Blog();
            blog.setContent(ncontent);
            blog.setSummary("摘要："+ncontent.replaceAll("\\<.*?>","").substring(0,200));
            blog.setReleasedate(new Date());
            blog.setTypeid(2L);
            blog.setKeyword(keyword.get(0).toString().substring(0,100));
            blog.setTitle(title.get(0).toString());
            blogRepository.save(blog);
           // String content = response.getContent();

            //System.out.println("---------"+content);

            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}