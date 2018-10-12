//初始化表格
initTable("#blogtype_tab", "/blogger/listBlogType", [{
    checkbox: true
}, {
    field: 'id',
    title: '编号'
}, {
    field: 'typename',
    title: '类型名称'
}, {
    field: 'orderno',
    title: '排序'
}], function queryParams(params) {
    //设置查询参数
    var param = {
        pageNumber: params.pageNumber,
        pageSize: params.pageSize,
        typename: $(".typename").val()
    };
    return param;
});

//查询
$('#btn-query').click(function () {
    $('#blogtype_tab').bootstrapTable('refresh');    //刷新表格
});
$('#btn-add').click(function () {
    $('.edit-typename').val('');
    $('.edit-orderno').val('');
    $('.edit-id').val('');
    $('#editModal').modal('show');//弹出框
});
//修改
$('#btn-edit').click(function () {
    var checkrow = $('#blogtype_tab').bootstrapTable('getSelections');
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
//提交修改
$('#btn-submit').click(function () {
    var BlogType = {};
    if($('.edit-id').val()!="" && $('.edit-id').val() !=null){
        BlogType.id = $('.edit-id').val();
    }
    //flavr弹出层比遮挡问题修改flavr.css 将flavr-container.modal添加上：z-index:99999　即可。
    if($('.edit-typename').val()=='' || $('.edit-typename').val()==null){
        new $.flavr({ content : '请输入类型名称',autoclose : false});
        return;
    }
    BlogType.typename = $('.edit-typename').val();
    BlogType.orderno = $('.edit-orderno').val();

    new $.flavr({
        content: '确定保存吗？',
        buttons: {
            danger: {
                text: '确定',
                style: 'danger',
                action: function () {
                    $.ajax({
                        type:"POST",
                        url:"/blogger/saveBlogType",
                        data:JSON.stringify(BlogType),
                        contentType:"application/json;charset=utf-8",
                        success:function (data) {
                            if(data.status==200){
                                new $.flavr({ content : '保存成功',autoclose : false});
                                $('#editModal').modal('hide');
                                $('#blogtype_tab').bootstrapTable('refresh');
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
});
//删除
$('#btn-del').click(function () {
    var checkrow = $('#blogtype_tab').bootstrapTable('getSelections');
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
                            url:"/blogger/delBlogType",
                            data:"ids="+ids,
                            success:function (data) {
                                if(data.status==200){
                                    new $.flavr({ content : '删除成功',autoclose : false});
                                    $('#blogtype_tab').bootstrapTable('refresh');
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