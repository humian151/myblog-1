//初始化表格
initTable("#data_tab", "/user/listModifyInfo", [{
    checkbox: true
}, {
    field: 'id',
    title: '编号'
}, {
    field: 'username',
    title: '登录名'
}, {
    field: 'name',
    title: '用户名称'
}, {
    field: 'password',
    title: '密码'
}, {
    field: 'avatar',
    title: '头像'
}, {
    field: 'nickname',
    title: '昵称'
}, {
    field: 'sign',
    title: '用户签名'
}, {
    field: 'prifile',
    title: '用户说明'
}], function queryParams(params) {
    //设置查询参数
    var param = {
        pageNumber: params.pageNumber,
        pageSize: params.pageSize,
        name: $(".name").val()
    };
    return param;
});
//隐藏列
$('#data_tab').bootstrapTable('hideColumn', 'avatar');
$('#data_tab').bootstrapTable('hideColumn', 'password');

//查询
$('#btn-query').click(function () {
    $('#data_tab').bootstrapTable('refresh');    //刷新表格
});
//新增
$('#btn-add').click(function () {
    $('.edit-password').val('');
    $('.edit-avatar').val('');
    $('.edit-username').val('');
    $('.edit-name').val('');
    $('.edit-nickname').val('');
    $('.edit-sign').val('');
    $('.edit-prifile').val('');
    $('.edit-id').val('');
    $('#editModal').modal('show');//弹出框
});
//修改
$('#btn-edit').click(function () {
    var checkrow = $('#data_tab').bootstrapTable('getSelections');
    if(checkrow.length==1){
        $('.edit-password').val(checkrow[0].password);
        $('.edit-avatar').val(checkrow[0].avatar);
        $('.edit-username').val(checkrow[0].username);
        $('.edit-name').val(checkrow[0].name);
        $('.edit-nickname').val(checkrow[0].nickname);
        $('.edit-sign').val(checkrow[0].sign);
        $('.edit-prifile').val(checkrow[0].prifile);
        $('.edit-id').val(checkrow[0].id);
        $('#editModal').modal('show');//弹出框
    }else if(checkrow.length==0){
        new $.flavr({ content : '请勾选数据',autoclose : false});
    }else {
        new $.flavr({ content : '一次只能勾选一条数据',autoclose : false});
    }
});
//提交
$('#btn-submit').click(function () {
    new $.flavr({
        content: "确认保存吗？",
        buttons: {
            danger: {
                text: '确定',
                style: 'danger',
                action: function () {
                        var User = {};
                        User.id = $('.edit-id').val();
                        User.password =  $('.edit-password').val();
                        User.avatar =$('.edit-avatar').val();
                        User.username =  $('.edit-username').val();
                        User.name = $('.edit-name').val();
                        User.nickname = $('.edit-nickname').val();
                        User.sign = $('.edit-sign').val();
                        User.prifile = $('.edit-prifile').val();

                        $.ajax({
                            type:"POST",
                            url:"/user/saveUser",
                            data:JSON.stringify(User),
                            contentType:"application/json;charset=utf-8",
                            success:function (data) {
                                if(data.status==200){
                                    new $.flavr({ content : '保存成功',autoclose : false});
                                    $('#data_tab').bootstrapTable('refresh');
                                    $('#editModal').modal('hide');
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
                            url:"/user/deleteUser",
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