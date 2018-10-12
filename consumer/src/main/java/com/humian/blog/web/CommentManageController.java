package com.humian.blog.web;
import com.humian.blog.dto.DataTable;
import com.humian.blog.dto.JsonResult;
import com.humian.blog.entity.Comment;
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

import static com.humian.blog.utils.Contains.*;

/**
 * 评论控制器
 * Created by DELL on 2018/10/9.
 */
@Controller
@RequestMapping("/comment")
public class CommentManageController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;

    /**
     * 展示评论管理页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/commentManage")
    public ModelAndView showCommentManage(ModelAndView modelAndView){
        modelAndView.setViewName("/admin/commentManage");
        return modelAndView;
    }
    /**
     * 展示评论审核页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/commentReview")
    public ModelAndView showCommentReview(ModelAndView modelAndView){
        modelAndView.setViewName("/admin/commentReview");
        return modelAndView;
    }

    /**
     * 查看评论信息
     * @param content
     * @param title
     * @param state
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/listComment")
    @ResponseBody
    public Object listComment(String content,String title, Long state,Integer pageNumber, Integer pageSize){
        if(StringUtils.isEmpty(pageNumber)|| StringUtils.isEmpty(pageSize)){
            pageNumber=1;
            pageSize=10;
        }
        String param = "?content="+content+"&title="+title+"&state="+state+"&page="+pageNumber+"&limit="+pageSize;
        Object result =this.restTemplate
                .exchange(COMMENT_QUERYCOMMENT+param, HttpMethod.GET,
                        new HttpEntity<Object>(this.headers), DataTable.class).getBody();

        return result;
    }

    /**
     * 保存评论信息主要处理审核功能
     * @param comment
     * @return
     * @throws Exception
     */
    @PostMapping("/saveComment")
    @ResponseBody
    public JsonResult saveComment(@RequestBody Comment comment) throws Exception {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            this.restTemplate
                    .exchange(COMMENT_ADDCOMMENT,HttpMethod.POST,new HttpEntity<Object>(comment,headers), Comment.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }

    /**
     * 删除评论信息
     * @param ids
     * @return
     */
    @PostMapping("/delComment")
    @ResponseBody
    public JsonResult deleteComment(String ids){
        try {
            this.restTemplate
                    .exchange(COMMENT_DELETECOMMENT+"?ids="+ids,HttpMethod.POST,new HttpEntity<Object>(this.headers), String.class)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.errorMsg(e.getMessage());
        }
        return JsonResult.ok();
    }


}
