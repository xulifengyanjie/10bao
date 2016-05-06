// JavaScript Document
$(function(){
	//管理员
	var userName  = $(".user_name");
	userName.click(function(){
		var attrClass = $(this).parent().attr("class");
		if(attrClass == "user_box"){
			$(this).parent().addClass("cur");	
		}else{
			$(this).parent().removeClass("cur");	
		}
	});
	//左侧度高
	var screenH = $(window).height();
	$(".left_menu").css("minHeight",screenH-66 + 'px');//最小高度
	$(".weibo_content").css("minHeight",screenH-72 + 'px'); //最小高度
	//左侧菜单伸缩
	var leftListTitle = $(".left_menu_list h3");
	leftListTitle.click(function(){
		var thisAttr = $(this).children('a').attr("class");
		if(thisAttr == 'cur'){
			$(this).parent().children(".sub_list").hide();
			$(this).children('a').removeClass('cur');
		}else{
			$(this).parent().children(".sub_list").show();
			$(this).children('a').addClass('cur');
		}
	});
	//表格
	$(".right_border table tr th:last").addClass("last_border");
	$(".right_border table tr").each(function(){
		$(this).children("td:last").addClass("last_border");
	});
	$(".right_border table tr").hover(function(){
		$(this).addClass("table_hover");
	},function(){
		$(this).removeClass("table_hover");
	});
	//报道内容统计
	//var timer = null;
//	function removePop(){
//		$(".content_count_pop").hide();
//		$(".content_count_btn").removeClass("cur");
//	}
//	$(".content_count_btn").hover(function(){
//		$(".content_count_pop").show();
//		$(this).addClass('cur');
//		clearTimeout(timer);
//	},function(){
//		timer = setTimeout(removePop,500);
//	});
//	$(".content_count_pop").hover(function(){
//		$(this).show();
//		clearTimeout(timer);
//	},function(){
//		timer = setTimeout(removePop,400);
//	});
	//tab切换
	var tabMenu = $(".weibo_tab span");
	$(".weibo_item:gt(0)").hide();
	tabMenu.click(function(){
		$(".weibo_tab span").removeClass("cur");
		$(this).addClass("cur");
		$(".weibo_item").eq(tabMenu.index(this)).show()
									.siblings().hide();	
	});
	//select
	$(".select_title").click(function(){
		var selectList = $(this).parent().children(".select_list").css("display");
		if(selectList == "none"){
			$(this).addClass('cur');
			$(this).parent().children(".select_list").show();	
		}else{
			$(this).removeClass('cur');
			$(this).parent().children(".select_list").hide();		
		}
	});
	$(".select_list a").click(function(){
		var thisValue = $(this).html();
		if(thisValue !=""){
			$(this).parent().parent().children().children("font").html(thisValue);	
			$(this).parent(".select_list").hide();
			$(".select_title").removeClass('cur');
		}
	});
	//综合排序/发布时间切换
	$(".tabe_atten_btn span").click(function(){
		$(".tabe_atten_btn span").removeClass("cur");
		$(this).addClass("cur");	
	});
	
	//报道内容统计弹出层
	var countPop = $(".content_count_btn a");
	/*var trendPop = $("a[name='trend']");
	var sourcePop = $("a[name='source']");*/
	countPop.click(function(){
		$(this).addClass("cur");
		$(".pop_bg").show();
		$("#sum_content").show();	
	});
	/*trendPop.click(function(){
		$(".pop_bg").show();
		$("#trend_content").show();	
	});
	sourcePop.click(function(){
		$(".pop_bg").show();
		$("#source_content").show();	
	});*/
	//弹出层关闭
	var sumCloseIcon = $("a[name='sum_close']");
	var sourceCloseIcon = $("a[name='source_close']");
	var trendCloseIcon = $("a[name='trend_close']");
	sumCloseIcon.click(function(){
		$(".pop_bg").hide();
		$("#sum_content").hide();	
		countPop.removeClass("cur");
	});
	sourceCloseIcon.click(function(){
		$(".pop_bg").hide();
		$("#source_content").hide();
	});
	trendCloseIcon.click(function(){
		$(".pop_bg").hide();
		$("#trend_content").hide();
	});
	
//	$("#checkAll").click(function(){
//	    if(this.checked){
//	        $("input[name='checkBoy']").prop("checked",true);
//	    }else{
//	        $("input[name='checkBoy']").prop("checked",false);
//	    }
//	});
	$(document).ready(function(){  
        var count = 0;
        $('input[name=checkbox]').each(function(){
            $(this).click(function(){
                if($(this).prop('checked')){
                    count++;
                    if(count == $("input[name='checkbox']").length){
                        $('#checkAll').prop('checked','true');    
                    }
                }else{
                    count--;
                    $('#checkAll').removeAttr('checked');   
                }
            });
        }); 
        $('#checkAll').click(function(){
            if($(this).prop('checked')){
                $('input[name=checkbox]').each(function(){
                    $(this).prop('checked','true');
                    count =  $("input[name='checkbox']").length;    
                });    
            }else{
                $('input[name=checkbox]').each(function(){
                    $(this).removeAttr('checked');
                    count = 0;    
                });     
            }  
        });
	}); 
});