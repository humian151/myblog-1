//初始化表格
initTable("#data_tab", "/comment/listComment", [{
    checkbox: true
}, {
    field: 'id',
    title: '编号'
}, {
    field: 'userip',
    title: '用户登录ip'
}, {
    field: 'title',
    title: '博客标题'
}, {
    field: 'blogid',
    title: '博客id'
}, {
    field: 'content',
    title: '评论内容'
}, {
    field: 'commentdate',
    title: '评论时间',
    formatter: function (value, row, index) {
        return changeDateFormat(value)
    }
}, {
    field: 'state',
    title: '审核状态',
    formatter: function (value, row, index) {
        if(value =="0"){
            return "未审核";
        }else if(value =="1"){
            return "审核通过";
        }else if(value =="2"){
            return "审核不通过";
        }
    }
}], function queryParams(params) {
    //设置查询参数
    var param = {
        pageNumber: params.pageNumber,
        pageSize: params.pageSize,
        content: $(".content").val(),
        title: $(".title").val(),
        state: $(".state option:selected").val()
    };
    return param;
});
//隐藏列
$('#data_tab').bootstrapTable('hideColumn', 'blogid');

//查询
$('#btn-query').click(function () {
    $('#data_tab').bootstrapTable('refresh');    //刷新表格
});
//修改
$('#btn-edit').click(function () {
    var checkrow = $('#data_tab').bootstrapTable('getSelections');
    if(checkrow.length==1){
        $('.edit-typename').val(checkrow[0].typename);
        $('.edit-id').val(checkrow[0].id);
        $('.edit-orderno').val(checkrow[0].orderno);
        $('#editModal').modal('show');//弹出框
    }else if(checkrow.length==0){
        new $.flavr({ content : '请勾选数据',autoclose : false});
    }else {
        new $.flavr({ content : '一次只能勾选一条数据',autoclose : false});
    }
});
//审核
function  auditComment(_state) {
    var checkrow = $('#data_tab').bootstrapTable('getSelections');
   if(checkrow.length==0){
        new $.flavr({ content : '请勾选数据',autoclose : false});
    }else {
       var msg = _state==1?'确定审核通过吗？':'确定审核拒绝吗？';

       new $.flavr({
           content: msg,
           buttons: {
               danger: {
                   text: '确定',
                   style: 'danger',
                   action: function () {
                       for(var i=0;i<checkrow.length;i++){
                           var Comment = {};
                           var row = checkrow[i];
                           Comment.id = row.id;
                           Comment.userip = row.userip;
                           Comment.content = row.content;
                           Comment.commentdate = row.commentdate;
                           Comment.state = _state;
                           Comment.blogid = row.blogid;
                           $.ajax({
                               type:"POST",
                               url:"/comment/saveComment",
                               data:JSON.stringify(Comment),
                               contentType:"application/json;charset=utf-8",
                               success:function (data) {
                                   if(data.status==200){
                                       new $.flavr({ content : '保存成功',autoclose : false});
                                       $('#data_tab').bootstrapTable('refresh');
                                   }else{
                                       new $.flavr({ content : data.msg,autoclose : false});
                                   }
                               }
                           });

                       }

                   }
               },
               cancel: {
                   text: '取消',
                   style: 'default'
               }
           }
       });


    }
}
//删除
$('#btn-del').click(function () {
    var checkrow = $('#data_tab').bootstrapTable('getSelections');
    if(checkrow.length==0){
        new $.flavr({ content : '请勾选数据',autoclose : false});
    }else {
        var ids = "";
        for(var i=0;i<checkrow.length;i++){
            ids += checkrow[i].id+",";
        }
        ids = ids.substring(0,ids.length-1);
        new $.flavr({
            content: '确定删除'+ids+'吗？',
            buttons: {
                danger: {
                    text: '确定',
                    style: 'danger',
                    action: function () {
                        $.ajax({
                            type:"POST",
                            url:"/comment/delComment",
                            data:"ids="+ids,
                            success:function (data) {
                                if(data.status==200){
                                    new $.flavr({ content : '删除成功',autoclose : false});
                                    $('#data_tab').bootstrapTable('refresh');
                                }else{
                                    new $.flavr({ content : data.msg,autoclose : false});
                                }
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    style: 'default'
                }
            }
        });
    }
});