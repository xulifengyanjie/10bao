// JavaScript Document
$(function() {
	var sWidth = 710; //（显示面积）
	var len = $(".index_hot_list ul li").length; //个数
	var index = 0;
	var picTimer;
	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$(".index_hot_span span").mouseenter(function() {
		index = $(".index_hot_span span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$(".index_hot_list ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$(".index_hot_word").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},4000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$(".index_hot_list ul").stop(true,false).animate({"left":nowLeft},500); //通过animate()调整ul元素滚动到计算出的position
		$(".index_hot_span span").removeClass("cur").eq(index).addClass("cur"); //为当前的按钮切换到选中的效果
	}
});
