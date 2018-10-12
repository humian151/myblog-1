/*存放公共方法*/

/**
 * 去除str中的html标签
 * @param str
 * @returns {Node|void|XML|string}
 */
function stringHtml(str) {
    var reTag = /<(?:.|\s)*?>/g;
    return str.replace(reTag,"");
}

//修改——转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    if (cellval != null) {
        var date = new Date(parseInt(cellval));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hh24 = date.getHours();
        var mi = date.getMinutes();
        var ss = date.getSeconds();
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hh24 + ":" + mi + ":" + ss;
    }
}
/**
 * 初始化表格
 * @param table
 * @param _url 地址
 * @param _columns 列数组
 * @param _queryParams 查询参数function
 */
function initTable(table,_url,_columns,_queryParams) {
    //先销毁表格
    $(table).bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $(table).bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: _url,
        striped: true,  //表格显示条纹
        pagination: true, //启动分页
        //toolbar: toolbar,//工具按钮用哪个容器
        pageSize: 8,  //每页显示的记录数
        pageNumber: 1, //当前第几页
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表
        search: false,  //是否启用查询
        showColumns: true,  //显示下拉框勾选要显示的列
        showRefresh: true,  //显示刷新按钮
        sidePagination: "server", //表示服务端请求
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType: "undefined",
        //columns: createCols(params,titles,hasCheckbox),
        columns:_columns,
        queryParams: _queryParams,
        onLoadSuccess: function () {  //加载成功时执行
            //layer.msg("加载成功");
        },
        onLoadError: function () {  //加载失败时执行
            //layer.msg("加载数据失败", {time: 1500, icon: 2});
        }
    });
}
/**
 * 隐藏查询条件按钮事件
 * @param _this
 */
function hideQueryParam(_this) {
    if($('.second-row').is(":hidden")){
        $(_this).html('<span class="glyphicon glyphicon-collapse-down"></span>&nbsp;隐藏');
    }else{
        $(_this).html('<span class="glyphicon glyphicon-expand"></span>&nbsp;查询');
    }
    $('.second-row').slideToggle();
}