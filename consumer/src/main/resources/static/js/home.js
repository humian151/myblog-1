$(function(){
	//主菜单关闭页面,由于click是新加的，需要动态绑定
	$(document).on('click','.close',function(){
		if($(this).parent().hasClass('active')){ 
			//处于当前页面应该关闭当前主页
			var prevLi = $(this).parent().prev(); //上一级li
			if(!prevLi.hasClass('yibiao')){
				prevLi.addClass('active');
			}
			$('#show-page').attr('src',prevLi.children('a').attr('index'));
		}
		$(this).parent().remove();
	});

	//收缩菜单
	$(document).on('click','.menu-show',function(){
		$(this).children('span').children('i').attr('class','');
		if($(this).next().is(":hidden")){//处于隐藏状态 即本次操作为打开
			$(this).children('span').children('i').attr('class','fa fa-caret-down');
		}else{
			$(this).children('span').children('i').attr('class','fa fa-caret-left');
		}
		$(this).next().slideToggle();
	});

	//隐藏左侧菜单
	$('#hide-left-menu').click(function(){
		//$('.left-menu').slideToggle();
		
		if(parseInt($(".left-menu").css('left')) ==0){
			$(".left-menu").animate({"left":-200},500); 
			$(".midden-content").animate({"left":0,"width":1355},500); 
			// midden-content
		}else{
			$(".left-menu").animate({"left":0},500); 
			$(".midden-content").animate({"left":200,"width":1155},500); 
		}
		
	});
	

});
/**
 * 打开页面（顶部菜单）
 */
function showPage(_this){
	//防止多次刷新
	if(!$(_this).parent().hasClass('active')){
		$('#show-page').attr('src',$(_this).attr('index'));
		var arr = $(_this).parent().parent().children('li');
		for(var i=0;i<arr.length;i++){
			$(arr[i]).removeClass('active');
		}
		$(_this).parent().addClass('active');
	}
}

/**
 * 打开页面（右侧菜单）
 */
function showPageMenu(_this){
	//防止多次刷新
	if(!$(_this).parent().hasClass('active') || $('#show-page').attr('src') != $(_this).attr('index')){
		$('#show-page').attr('src',$(_this).attr('index'));
		var arr = $(_this).parent().parent().children('li');
		for(var i=0;i<arr.length;i++){
			$(arr[i]).removeClass('active');
		}
		$(_this).parent().addClass('active');

		//处理顶部菜单
		var topArr = $('.midden-head-li');
		var count=0;
		for(var i=0;i<topArr.size();i++){
			$(topArr[i]).removeClass('active');
			var indexHtml = $(topArr[i]).children('a').attr('index');
			if(indexHtml == $(_this).attr('index')){
				$(topArr[i]).addClass('active');
				count++;
			}
		}
		if(count==0){
			$('<li class="midden-head-li active"><a href="javascript:void(0)" index="'+
				$(_this).attr('index')+'" onclick="showPage(this)">'+
				stringHtml($(_this).html())+'</a><div class="close"><i class="fa fa-remove"></i></div></li>')
					.appendTo('#top-menu');
		}
		

	}
}
//登出方法
function fnLogout() {
	/*if(confirm("确定要退出吗")){
		window.location.href="/logout";
	}*/

    new $.flavr({
        content: '确定注销吗？',
        buttons: {
            danger: {
                text: '确定',
                style: 'danger',
                action: function () {
                    window.location.href="/logout";
                }
            },
            cancel: {
                text: '取消',
                style: 'default'
            }
        }
    });

}
//移出html标签
 function stringHtml(str) {
    var reTag = /<(?:.|\s)*?>/g;
    str = str.replace(reTag,"");
    str = str.replace("&nbsp;","");
    return str;
}
