$(document).ready(function() {
    $('#blog-content').summernote({
        height: 100,
        tabsize: 2
    });
    $('#blog-content').summernote('code',_blogcontent);
    $('#btn-save').click(function(){

        var Blog = {};
        Blog.id = _blogid;
        Blog.title = $(".title").val();
        Blog.typeid =$(".typeId option:selected").val();
        Blog.content =$('#blog-content').summernote('code');
        Blog.summary = "摘要："+stringHtml(Blog.content).substr(0,100);
        Blog.keyword=$(".keyword").val();

        $.ajax({
            async:false,
            url:"/blogger/saveBlog",
            data:JSON.stringify(Blog),
            contentType:"application/json;charset=utf-8",
            type:"POST",
            success:function (data) {
                if(data.status==200){
                    new $.flavr({
                        content: '保存成功！',
                        buttons: {
                            danger: {
                                text: '确定',
                                style: 'danger',
                                action: function () {
                                    window.parent.$('#editModal').modal('hide');
                                    window.parent.$('#blog_tab').bootstrapTable('refresh');
                                }
                            }
                        }
                    });
                }else{
                    new $.flavr({ content :"保存失败，失败原因："+data.msg,autoclose : false});
                }

            }
        });
        return false;
    });
});