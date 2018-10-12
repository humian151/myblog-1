$(function(){
    //初始化表格
    initTable("#blog_tab", "/blogger/listBlog", [{
        checkbox: true
    }, {
        field: 'id',
        title: '编号'
    }, {
        field: 'title',
        title: '标题'
    }, {
        field: 'releasedate',
        title: '发布日期',
        class: 'releasedate',
        formatter: function (value, row, index) {
            return changeDateFormat(value)
        }
    }, {
        field: 'typename',
        title: '博客类别'
    }], function queryParams(params) {
        //设置查询参数
        var param = {
            pageNumber: params.pageNumber,
            pageSize: params.pageSize,
            typeid: $(".blogtype option:selected").val(),
            title: $(".title").val()
        };
        return param;
        //return params;
    });
    //查询
    $('#btn-query').click(function () {
        $('#blog_tab').bootstrapTable('refresh');    //刷新表格
    });
    //修改
    $('#btn-edit').click(function () {
        var checkrow = $('#blog_tab').bootstrapTable('getSelections');
        if(checkrow.length==1){
            $('#show-page').attr('src','/blogger/modifyBlog/'+checkrow[0].id);
            $('#editModal').modal('show');//弹出框
        }else if(checkrow.length==0){
            new $.flavr({ content : '请勾选数据',autoclose : false});
        }else {
            new $.flavr({ content : '一次只能勾选一条数据',autoclose : false});
        }
    });
    //删除
    $('#btn-del').click(function () {
        var checkrow = $('#blog_tab').bootstrapTable('getSelections');
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
                                url:"/blogger/delBlog",
                                data:"ids="+ids,
                                success:function (data) {
                                    if(data.status==200){
                                        new $.flavr({ content : '删除成功',autoclose : false});
                                        $('#blog_tab').bootstrapTable('refresh');
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
});