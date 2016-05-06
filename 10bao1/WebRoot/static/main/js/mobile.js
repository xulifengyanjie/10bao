// JavaScript Document
$(function(){
	//判断数据是否加载过的标记
	var flagfenghuang="no";
	var flagjinritoutiao="no";
	//每次F5刷新把各个客户端的滚动条置顶
	//腾讯新闻
    var e=document.getElementById("tcontent") ;
    e.scrollTop=0;
    //网易新闻
    var e1=document.getElementById("ncontent") ;
    e1.scrollTop=0;
    //凤凰新闻
    var e2=document.getElementById("fcontent") ;
    e2.scrollTop=0;
    //今日头条
    var e3 = document.getElementById("jrcontent");
    e3.scrollTop=0;
    //经济日报
    var e4 = document.getElementById("jjrcontent");
    e4.scrollTop=0;
    

	var hght=0;//滚动条的总长度
	var top=0;//滚动条的当前位置
	//腾讯
   	var tlen =0;//腾讯要闻的次数
   	var tclen =0;//腾讯财经的次数
   	var tklen =0;//腾讯科技的次数
   	var slen =0;//腾讯社会的次数
   	//网易
   	var ntlen =0;//网易的头条次数
   	var nclen =0;//网易财经的次数
   	var nklen =0;//网易科技的次数
   	var nglen =0;//网易军事的次数
   	//凤凰
   	var flen = 0;//凤凰的头条次数
   	var fclen =0;//凤凰的财经次数
   	var fzlen = 0;//凤凰时政的次数
   	var fglen =0;//凤凰军事的次数

   	//今日头条
   	var jrslen =0;//今日头条的社会次数
   	var jrclen = 0;//今日头条的财经次数
   	var jrjlen = 0;//今日头条的军事次数
   	var jrgjlen = 0;//今日头条的国际次数
   	
  	//经济日报
   	var jjrtlen =0;//经济日报的头条次数
   	var jjrzlen = 0;//经济日报的政务次数
   	var jjrglen = 0;//经济日报的观点次数
   	var jjrslen = 0;//经济日报数据次数



	var sWidth = 270; //获取焦点图的宽度（显示面积）
	var len = $(".mobile_con ul li").length; //获取焦点图个数
	var index = 0;
	//以下代码添加数字按钮
	var btn = "<ul>";
	for(var i=0; i < len+3; i++) {
		btn += "<li><div class='one'>"+$(".mobile_con ul li input:eq("+i+")").val();+"</div></li>";
	}
	btn += "</ul>";
	$(".atten_fot_list").append(btn);
	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$(".atten_fot_list ul li").mouseenter(function() {
		index = $(".atten_fot_list ul li").index(this);
		if(index<=len){
			showPicsHover(index);
		}else{
			showPicsHover(len);	
		}
	}).eq(0).trigger("mouseenter");
	//上一页按钮
	$(".mobile_pre").click(function() {
		index -= 1;
		if(index == -1) {index =0 }
		showPics(index);
	});
	//下一页按钮
	$(".mobile_next").click(function() {
		index += 1;
		if(index >= len+1) {index = len}
		showPics(index);
		if(index==1){
				showAjaxData("凤凰新闻","头条");				
		}else if(index==2){
				showAjaxData("今日头条","社会");				
		}
	});
	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$(".mobile_con ul").css("width",sWidth * (len + 3));
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		//$(".atten_fot_list ul li").removeClass("cur").eq(index).addClass("cur"); //为当前的按钮切换到选中的效果
		$(".mobile_con ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
	}
	function showPicsHover(index) { //普通切换
		if(index>=1 && flagfenghuang=="no"){
			showAjaxData("凤凰新闻","头条");	
		}
		if(index>=2 && flagjinritoutiao=="no"){
			showAjaxData("今日头条","社会");	
		}
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$(".atten_fot_list ul li").removeClass("cur").eq(index).addClass("cur"); //为当前的按钮切换到选中的效果
		$(".mobile_con ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
	}
	
	//客户端首页异步方法
	function showAjaxData(appName,flag){
		var htmlString=''
	    //获取项目路径
	    var curWwwPath=window.document.location.href;
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    var localhostPaht=curWwwPath.substring(0,pos);
	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    var basePath=localhostPaht+projectName;
		$.ajax({
			type:'POST',
			url:basePath+'/mobile/showAjaxData',
			data:"appName="+appName + "&column="+flag,
			success:function(data){
				 var d = eval(data);
				 for(var i=0;i<d.length;i++){
				   if(i==0){
						 htmlString+='<div id="mpub" class="m_pub_banner"><a  href='+d[i].surl+' target="_blank"><img src='+d[i].imgsrc+'> <div class="m_pub_banner_txt">'+d[i].title+'</div></a></div>'; 
				   }else{
					   if(d[i].newsText==null){
						   htmlString+='<div class="m_list_item"><div class="m_list_pic"><a href="javascript:void(0)" target="_blank"><img src='+d[i].imgsrc+'></a></div><div class="m_list_txt"><div class="m_list_txt_name"><a  href='+d[i].surl+' target="_blank">'+d[i].title+'</a></div><p>....<font></font></p></div></div>';
					   }else{
						   htmlString+='<div class="m_list_item"><div class="m_list_pic"><a href="javascript:void(0)" target="_blank"><img src='+d[i].imgsrc+'></a></div><div class="m_list_txt"><div class="m_list_txt_name"><a  href='+d[i].surl+' target="_blank">'+d[i].title+'</a></div><p>'+d[i].newsText+'<font></font></p></div></div>';
					   }
				   }
			     }
				 if(appName=='凤凰新闻'){
					 $("#fmpub").append(htmlString);
					 flagfenghuang="yes";
				 }else if(appName=='今日头条'){
					 $("#jrmpub").append(htmlString);
					 flagjinritoutiao="yes"
				 }					 				 				 
			}
		});
	}
	
	 $("#tcontent").scroll(function(){
     	hght=this.scrollHeight;
     	top=this.scrollTop;
     	if(top==0 || top==1){
     	   var tli = document.getElementById("tengxun").getElementsByTagName("a");
     	   if(tli[0].className==""){
         	   tlen = 0;
     	   }
     	   if(tli[0].className!="" && top ==1){
     		   tlen = 0; 
     	   }
     	   if(tli[1].className==""){
         	   tclen = 0;					
     	   }
     	   if(tli[1].className!="" && top ==1){
     		   tclen = 0; 
     	   }
     	   if(tli[2].className==""){
         	   tklen = 0;
     	   }
     	   if(tli[2].className!="" && top ==1){
         	   tklen = 0;
     	   }
     	   if(tli[3].className==""){
         	   slen = 0;
     	   }
     	   if(tli[3].className!="" && top ==1){
         	   slen = 0;
     	   }
     	}
         if(top == hght - 300){	                	
         	var tli = document.getElementById("tengxun").getElementsByTagName("a");
         	for(var i=0;i<tli.length;i++){
         		if(tli[i].className=="mo_menu_cur"){
         			flag = tli[i].innerHTML;
         			if(flag == '要闻'){

         					showAppNews1(flag,'腾讯新闻',tlen);
         					++tlen;
         					tklen = 0;
         					slen = 0;
         					tclen = 0;                					
         			}
         			else if(flag =='财经'){
         					showAppNews1(flag,'腾讯新闻',tclen);
         					++tclen;
         					tlen = 0;
         					tklen = 0;
         					slen = 0;
         			}
				else if(flag =='科技'){	
	                			showAppNews1(flag,'腾讯新闻',tklen);
						++tklen;
						tlen = 0;
						slen = 0;
	        	        tclen = 0;
				}
				else if(flag =='社会'){

         					showAppNews1(flag,'腾讯新闻',slen);
         					++slen;
         					tlen = 0;
         					tklen = 0;
         					tclen = 0;               					
             			
				}
 			         break;
         		}
         		
         	}
         }

});
$("#ncontent").scroll(function(){
	hght=this.scrollHeight;
	top=this.scrollTop;
	if(top==0 || top==1){
		var tli = document.getElementById("netesase").getElementsByTagName("a");
 	   if(tli[0].className==""){
     	   ntlen = 0;
 	   }
 	   if(tli[0].className!="" && top ==1){
     	   ntlen = 0;
 	   }
 	   if(tli[1].className==""){
     	   nclen = 0;			
 	   }
 	   if(tli[1].className!="" && top ==1){
     	   nclen = 0;			
 	   }
 	   if(tli[2].className==""){
     	   nklen = 0;
 	   }
 	   if(tli[2].className!="" && top ==1){
     	   nklen = 0;
 	   }
 	   if(tli[3].className==""){
     	   nglen = 0;
 	   }
 	   if(tli[3].className!="" && top ==1){
     	   nglen = 0;
 	   }
  }
 if(top == hght - 300){
 	var tli = document.getElementById("netesase").getElementsByTagName("a");
	    for(var i=0;i<tli.length;i++){
 		if(tli[i].className=="mo_menu_cur"){
 			flag = tli[i].innerHTML;
 			if(flag == '头条'){
 					showAppNews1(flag,'网易新闻',ntlen);
 					++ntlen;
 					nklen = 0;
 					nglen = 0;
 					nclen = 0;        					
 			}
 			else if(flag =='财经'){

 					showAppNews1(flag,'网易新闻',nclen);
 					++nclen;
 					ntlen = 0;
 					nklen = 0;
 					nglen = 0;       					
 			}
			else if(flag =='科技'){	
	
 					showAppNews1(flag,'网易新闻',nklen);
 					++nklen;
 					ntlen = 0;
 					nglen = 0;
 					nclen = 0;        					
     			
			 }
			else if(flag =='军事'){
 					showAppNews1(flag,'网易新闻',nglen);
 					++nglen;
 					ntlen = 0;
 					nklen = 0;
 					nclen = 0;        					
     	
			}
				
 			break;
 		}
	    }
	}	                
});

$("#fcontent").scroll(function(){
	hght=this.scrollHeight;
	top=this.scrollTop;
	if(top==0 || top==1){
		var tli = document.getElementById("fenghuang").getElementsByTagName("a");
 	   if(tli[0].className==""){
     	   flen = 0;
 	   }
 	   if(tli[0].className!="" && top ==1){
     	   flen = 0;
 	   }
 	   if(tli[1].className==""){
     	   fzlen = 0;			
 	   }
 	   if(tli[1].className!="" && top ==1){
     	   fzlen = 0;			
 	   }
 	   if(tli[2].className==""){
     	   fclen = 0;
 	   }
 	   if(tli[2].className!="" && top ==1){
     	   fclen = 0;
 	   }
 	   if(tli[3].className==""){
     	   fglen = 0;
 	   }
 	   if(tli[3].className!="" && top ==1){
     	   fglen = 0;
 	   }
  }
  if(top == hght - 300){
         	var tli = document.getElementById("fenghuang").getElementsByTagName("a");
      	    for(var i=0;i<tli.length;i++){
         		if(tli[i].className=="mo_menu_cur"){
         			flag = tli[i].innerHTML;
         			if(flag == '头条'){

         					showAppNews1(flag,'凤凰新闻',flen);
         					++flen;
         					fzlen = 0;
         					fglen = 0;
         					fclen = 0;               					
         			}
         			else if(flag =='时政'){
         					showAppNews1(flag,'凤凰新闻',fzlen);
         					++fzlen;
         					flen = 0;
         					fclen = 0;
         					fglen = 0;                					
         			}
				else if(flag =='财经'){	

         					showAppNews1(flag,'凤凰新闻',fclen);
         					++fclen;
         					flen = 0;
         					fglen = 0;
         					fzlen = 0;	            					
             			
				}
				else if(flag =='军事'){	
         					showAppNews1(flag,'凤凰新闻',fglen);
         					++fglen;
         					flen = 0;
         					fzlen = 0;
         					fclen = 0;	            					
             			
				}
						
         			break;
         		}
      		
      	}	                
     }                
});

$("#jrcontent").scroll(function(){
	hght=this.scrollHeight;
	top=this.scrollTop;
	if(top==0 || top==1){
		var tli = document.getElementById("jinri").getElementsByTagName("a");
    	   if(tli[0].className==""){
    		   jrslen = 0; 
    	   }
    	   if(tli[0].className!="" && top ==1){
    		   jrslen = 0;
    	   }
    	   if(tli[1].className==""){
    		   jrclen = 0;			
    	   }
    	   if(tli[1].className!="" && top ==1){
    		   jrclen = 0;			
    	   }
    	   if(tli[2].className==""){
    		   jrjlen = 0;
    	   }
    	   if(tli[2].className!="" && top ==1){
    		   jrjlen = 0;
    	   }
    	   if(tli[3].className==""){
    		   jrgjlen = 0;
    	   }
    	   if(tli[3].className!="" && top ==1){
    		   jrgjlen = 0;
    	   }
     }
    
     if(top == hght - 300){
            	var tli = document.getElementById("jinri").getElementsByTagName("a");
         	    for(var i=0;i<tli.length;i++){
            		if(tli[i].className=="mo_menu_cur"){
            			flag = tli[i].innerHTML;
            			if(flag == '社会'){
            					showAppNews1(flag,'今日头条',jrslen);
            					++jrslen;
            					jrclen = 0;
            					jrjlen = 0;
            					jrgjlen = 0;        					
            			}
            			else if(flag =='财经'){
            					showAppNews1(flag,'今日头条',jrclen);
            					++jrclen;
            					jrslen = 0;
            					jrjlen = 0;
            					jrgjlen = 0;              					
            			}
				else if(flag =='军事'){	
            					showAppNews1(flag,'今日头条',jrjlen);
            					++jrjlen;
            					jrslen = 0;
            					jrgjlen = 0;
            					jrclen = 0;               					
                			
				}
				else if(flag =='国际'){
            					showAppNews1(flag,'今日头条',jrgjlen);
            					++jrgjlen;
            					jrslen = 0;
            					jrclen = 0;
            					jrjlen =0;                					
                	
				}
            			break;
            		}		                 		
         	}	                
        }              
                   
});

$("#jjrcontent").scroll(function(){
 	hght=this.scrollHeight;
 	top=this.scrollTop;
 	if(top==0 || top==1){
 	   var tli = document.getElementById("jingjiribao").getElementsByTagName("a");
 	   if(tli[0].className==""){
 		  jjrtlen = 0;
 	   }
 	   if(tli[0].className!="" && top ==1){
 		  jjrtlen = 0; 
 	   }
 	   if(tli[1].className==""){
 		  jjrzlen = 0;					
 	   }
 	   if(tli[1].className!="" && top ==1){
 		  jjrzlen = 0; 
 	   }
 	   if(tli[2].className==""){
 		  jjrglen = 0;
 	   }
 	   if(tli[2].className!="" && top ==1){
 		  jjrglen = 0;
 	   }
 	   if(tli[3].className==""){
 		  jjrslen = 0;
 	   }
 	   if(tli[3].className!="" && top ==1){
 		  jjrslen = 0;
 	   }
 	}
     if(top == hght - 300){	                	
     	var tli = document.getElementById("jingjiribao").getElementsByTagName("a");
     	for(var i=0;i<tli.length;i++){
     		if(tli[i].className=="mo_menu_cur"){
     			flag = tli[i].innerHTML;
     			if(flag == '头条'){

     					showAppNews1(flag,'经济日报',jjrtlen);
     					++jjrtlen;
     					jjrzlen = 0;
     					jjrglen = 0;
     					jjrslen = 0;                					
     			}
     			else if(flag =='政务'){
     					showAppNews1(flag,'经济日报',jjrzlen);
     					++jjrzlen;
     					jjrtlen = 0;
     					jjrglen = 0;
     					jjrslen = 0;
     			}
			else if(flag =='观点'){	
                			showAppNews1(flag,'经济日报',jjrglen);
					++jjrglen;
					jjrtlen  = 0;
					jjrzlen  = 0;
					jjrslen  = 0;
			}
			else if(flag =='数据'){

     					showAppNews1(flag,'经济日报',jjrslen);
     					++jjrslen;
     					jjrtlen = 0;
     					jjrzlen  = 0;
     					jjrglen  = 0;               					
         			
			}
			         break;
     		}
     		
     	}
     }

});
});
function showAppNews(flag,appName){
    var tengxun=document.getElementById("tengxun").getElementsByTagName("a");
	var netease=document.getElementById("netesase").getElementsByTagName("a");
    var fenghuang=document.getElementById("fenghuang").getElementsByTagName("a");
    var jinri=document.getElementById("jinri").getElementsByTagName("a");
    var jingji=document.getElementById("jingjiribao").getElementsByTagName("a");
    //获取项目路径
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var basePath=localhostPaht+projectName;
	$.ajax({
		   type:'POST',
		   async: false,
		   url: basePath+'/mobile/showItemNews/',
		   data: "column="+flag+"&appName="+appName,
		   success: function(data){
			   if(appName =='腾讯新闻'){
    			   var e=document.getElementById("tcontent") ;
    			   e.scrollTop=1;		    				   
			   }else if(appName =='网易新闻'){
    			   var e1=document.getElementById("ncontent") ;
    			   e1.scrollTop=1;		    				   
			   }else if(appName =='凤凰新闻'){
    			   var e3=document.getElementById("fcontent") ;
    			   e3.scrollTop=1;		    				   
			   }			  			 
			   else if(appName =='今日头条'){
    			   var e10=document.getElementById("jrcontent") ;
    			   e10.scrollTop=1;		    				   
			   }
               else if(appName =='经济日报'){
    			   var e19=document.getElementById("jjrcontent") ;
    			   e19.scrollTop=1;		    				   
			   }
			   if(flag=='财经'){
    			   if(appName=='腾讯新闻'){
			    		tengxun[0].className="";
			    		tengxun[1].className="mo_menu_cur";
			    		tengxun[2].className="";
			    		tengxun[3].className="";
	    			    if(data!=''){		    				   
		    			   show(data,flag,appName);					    			   						    			   
			    		}
    			   }
    			   else if(appName=='网易新闻'){
    				   netease[0].className="";
    		    	   netease[1].className="mo_menu_cur";
    		    	   netease[2].className="";
    		    	   netease[3].className="";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagenetesase');					    			   						    			   
			    		}
    			   }
                   else if(appName=='凤凰新闻'){
                	   fenghuang[0].className="";
                	   fenghuang[1].className="";
           			   fenghuang[2].className="mo_menu_cur";
                	   fenghuang[3].className="";			   
		    		   show(data,flag,appName);
    			   }

                   else if(appName=='今日头条'){
                	   jinri[0].className="";
                	   jinri[1].className="mo_menu_cur";
                	   jinri[2].className="";
                	   jinri[3].className="";	    				   
		    		   show(data,flag,appName);						    			   
    			   }
				   else if(appName=='新华炫闻'){
	                	   xinhua[0].className="mo_menu_cur";
	                	   xinhua[1].className="";
	                	   xinhua[2].className="";
	                	   xinhua[3].className="";
						   if(data!=''){
			    				show(data,flag,appName);
						   }
	    		   }
                   else if(appName=='国搜新闻'){
                	   guosou[0].className="";
                	   guosou[1].className="";
                	   guosou[2].className="mo_menu_cur";
                	   guosou[3].className="";
                	   guosou[4].className="";
                	   if(data!='')
                		   show(data,flag,appName);						    			   
    			   }
			   }
			   else if(flag=='科技'){
				   if(appName=='腾讯新闻'){			    					   
				      tengxun[0].className="";
				      tengxun[1].className="";
		     	      tengxun[2].className="mo_menu_cur";
		     	      tengxun[3].className="";
	    			  if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    	  }
    			   }
    			   else if(appName=='网易新闻'){
    				   netease[0].className="";
    		    	   netease[1].className="";
    		    	   netease[2].className="mo_menu_cur";
    		    	   netease[3].className="";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    		}
    			   }
    			   else if(appName=='凤凰新闻'){
    				   fenghuang[0].className="";
    				   fenghuang[1].className="";
    				   fenghuang[2].className="";
    				   fenghuang[3].className="";
    				   fenghuang[4].className="mo_menu_cur";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagefenghuang');
			    			   
			    		}
    			   }
				   
    			   else if(appName=='头版'){
    				   touban[0].className="";
    				   touban[1].className="";
    				   touban[2].className="";
    				   touban[3].className="mo_menu_cur";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagetouban');
			    			   
			    		}
    			   }
    			   else if(appName=='今日头条'){
    				   jinri[0].className="";
    				   jinri[1].className="";
    				   jinri[2].className="";
    				   jinri[3].className="";
    				   jinri[4].className="mo_menu_cur";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName);
			    			   
			    		}
    			   }
				   else if(appName=='新华炫闻'){
	    				   xinhua[0].className="";
	    				   xinhua[1].className="mo_menu_cur";
	    				   xinhua[2].className="";
	    				   xinhua[3].className="";
	    				   xinhua[4].className="";
						   if(data!=''){
			    				show(data,flag,appName);
						   }
	    		   }
    			   else if(appName=='国搜新闻'){
                	   guosou[0].className="";
                	   guosou[1].className="";
                	   guosou[2].className="";
                	   guosou[3].className="";
                	   guosou[4].className="mo_menu_cur";
                	   if(data!='')
                		   show(data,flag,appName);						    			   
    		           }
                              else if(appName=='360新闻'){
	                	   qihu[0].className="";
	                	   qihu[1].className="";
	                	   qihu[2].className="";
	                	   qihu[3].className="";
	                	   qihu[4].className="mo_menu_cur";
                                   if(data!='')	    				
			           	show(data,flag,appName,'imageqihu');
			      }
			   }
			   else if(flag=='军事'){
		
    			   if(appName=='网易新闻'){
    				   netease[0].className="";
    		    	   netease[1].className="";
    		    	   netease[2].className="";
    		    	   netease[3].className="mo_menu_cur";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    	    }
    			   }

                   else if(appName=='凤凰新闻'){
                	   fenghuang[0].className="";
                	   fenghuang[1].className="";
           			   fenghuang[2].className="";
                	   fenghuang[3].className="mo_menu_cur";	    				   
		    		   show(data,flag,appName);						    			   
    			   }

                   else if(appName=='今日头条'){
                	   jinri[0].className="";
                	   jinri[1].className="";
                	   jinri[2].className="mo_menu_cur";
                	   jinri[3].className="";	    				   
		    		   show(data,flag,appName);						    			   
    			   }
                   else if(appName=='新华炫闻'){
                	   xinhua[0].className="";
                	   xinhua[1].className="";
                	   xinhua[2].className="";
                	   xinhua[3].className="mo_menu_cur";
					   if(data!=''){
		    				show(data,flag,appName);
					   }
    		     }
                   else if(appName=='国搜新闻'){
                	   guosou[0].className="";
                	   guosou[1].className="";
                	   guosou[2].className="";
                	   guosou[3].className="mo_menu_cur";
                	   guosou[4].className="";
                	   if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    		}
    			   }
                    	   else if(appName=='360新闻'){
	                	   qihu[0].className="";
	                	   qihu[1].className="";
	                	   qihu[2].className="mo_menu_cur";
	                	   qihu[3].className="";
	                	   qihu[4].className="";
                                   if(data!='')	    				 
			           	show(data,flag,appName,'imageqihu');						    			   
	    		   }

	            }
			   else if(flag=='国际'){

    			   if(appName=='今日头条'){
    				   jinri[0].className="";
    				   jinri[1].className="";
    				   jinri[2].className="";
    				   jinri[3].className="mo_menu_cur";	    				   
		    		   show(data,flag,appName);
    			   } 
    			
    			   else if(appName=='国搜新闻'){
                	   guosou[0].className="";
                	   guosou[1].className="mo_menu_cur";
                	   guosou[2].className="";
                	   guosou[3].className="";
                	   if(data!='')
                		   show(data,flag,appName);						    			   
    		            }
                              else if(appName=='360新闻'){
	                	   qihu[0].className="";
	                	   qihu[1].className="mo_menu_cur";
	                	   qihu[2].className="";
	                	   qihu[3].className="";
	                	   qihu[4].className="";
                                   if(data!='')	    				 
			           	show(data,flag,appName,'imageqihu');						    			   
	    			   } 
			   }
			   else if(flag=='社会'){
				  if(appName=='腾讯新闻'){		    				   
			    		tengxun[0].className="";
			    		tengxun[1].className="";
			    		tengxun[2].className="";
			    		tengxun[3].className="mo_menu_cur";
	    			    if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    		}
    			   }
				  
	
				   else if(appName=='今日头条'){		    				   
					    jinri[0].className="mo_menu_cur";
			    		jinri[1].className="";
			    		jinri[2].className="";
			    		jinri[3].className="";	    				   
		    			show(data,flag,appName);						    			   
			        }
                                   
			   }
			   else if(flag=='国内'){
				  if(appName=='新浪新闻'){		    				   
			    		sina[0].className="";
			    		sina[1].className="";
			    		sina[2].className="mo_menu_cur";
			    		sina[3].className="";
			    		sina[4].className="";
	    			   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagesina');						    			   
			    		}
    			   }
				  else if(appName=='环球时报'){		    				   
			    		huanqiu[0].className="";
			    		huanqiu[1].className="";
			    		huanqiu[2].className="";
			    		huanqiu[3].className="mo_menu_cur";
			    		huanqiu[4].className="";
	    			   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagehuanqiu');						    			   
			    		}
    			   }
				  
				  else if(appName=='中国新闻网'){		    				   
			    		zhongguo[0].className="mo_menu_cur";
			    		zhongguo[1].className="";
			    		zhongguo[2].className="";
			    		zhongguo[3].className="";
			    		zhongguo[4].className="";
	    			   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagechinanews');						    			   
			    		}
  			      }
				  else if(appName=='新华炫闻'){		    				   
			    		xinhua[0].className="";
			    		xinhua[1].className="";
			    		xinhua[2].className="";
			    		xinhua[3].className="";
			    		xinhua[4].className="mo_menu_cur";
	    			   if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    		}
			      }

                              	  else if(appName=='360新闻'){
	                	   qihu[0].className="mo_menu_cur";
	                	   qihu[1].className="";
	                	   qihu[2].className="";
	                	   qihu[3].className="";
	                	   qihu[4].className="";
                                   if(data!='')	    				 
			             	show(data,flag,appName,'imageqihu');						    			   
	    			   } 
				   
			   }
			   
			   else if(flag=="要闻"){
				  if(appName=='腾讯新闻'){		    				   
			    		tengxun[0].className="mo_menu_cur";
			    		tengxun[1].className="";
			    		tengxun[2].className="";
			    		tengxun[3].className="";
	    			    if(data!=''){		    				   
		    			   show(data,flag,appName);						    			   
			    		}
    			   }
				  else if(appName=='环球时报'){		    				   
			    		huanqiu[0].className="mo_menu_cur";
			    		huanqiu[1].className="";
			    		huanqiu[2].className="";
			    		huanqiu[3].className="";
			    		huanqiu[4].className="";
	    			   if(data!=''){		    				   
		    			   show(data,flag,appName,'imagehuanqiu');						    			   
			    		}
    			   }
				  else if(appName=='搜狐新闻'){		    				   
			    		sohu[0].className="mo_menu_cur";
			    		sohu[1].className="";
			    		sohu[2].className="";
			    		sohu[3].className="";
			    		sohu[4].className="";
	    			    if(data!=''){		    				   
		    			   show(data,flag,appName,'imagesohu');						    			   
			    		}
  			       }
				  
				  else if(appName=='央视新闻'){		    				   
			    		yangshi[0].className="mo_menu_cur";
			    		yangshi[1].className="";
			    		yangshi[2].className="";
			    		yangshi[3].className="";
	    			    if(data!=''){		    				   
		    			   show(data,flag,appName,'imageyangshi');						    			   
			    		}
			       }
			   }
			   
			   else if(flag=="头条"){
				  if(appName=='网易新闻'){
    				   netease[0].className="mo_menu_cur";
    		    	   netease[1].className="";
    		    	   netease[2].className="";
    		    	   netease[3].className="";	    				   
		    		   show(data,flag,appName);						    			   
    			   }
                   else if(appName=='凤凰新闻'){
                	   fenghuang[0].className="mo_menu_cur";
                	   fenghuang[1].className="";
           			   fenghuang[2].className="";
                	   fenghuang[3].className="";				   
		    		    show(data,flag,appName);						    			   
    			   }

                   else if(appName=='经济日报'){
                	   jingji[0].className="mo_menu_cur";
                	   jingji[1].className="";
                	   jingji[2].className="";
                	   jingji[3].className="";	    				   
		    		   show(data,flag,appName);

    			   }
			   }
			   
			   else if(flag=='时政'){
	
				  if(appName=='凤凰新闻'){
                	   fenghuang[0].className="";
                	   fenghuang[1].className="mo_menu_cur";
                	   fenghuang[2].className="";
                	   fenghuang[3].className="";	    				   
		    		   show(data,flag,appName,'imagefenghuang');
    			   }
				 
				   else if(appName=='国搜新闻'){
                	   guosou[0].className="";
                	   guosou[1].className="";
                	   guosou[2].className="";
                	   guosou[3].className="mo_menu_cur";
                	   guosou[4].className="";
                	   if(data!='')
                		   show(data,flag,appName);						    			   
    		       }
				 
				   else if(appName=='新华炫闻'){
    				   xinhua[0].className="";
    				   xinhua[1].className="";
    				   xinhua[2].className="mo_menu_cur";
    				   xinhua[3].className="";
    				   if(data!=''){		    				   
		    			   show(data,flag,appName);
			    			   
			    	   }
    			   }
				 
			   }  
			   
			   else if(flag =='国际新闻'){
					 if(appName=='头条新闻网'){
						   toutiaonews[0].className="mo_menu_cur";
						   toutiaonews[1].className="";
						   toutiaonews[2].className="";
	                	   if(data!=''){		    				   
			    			   show(data,flag,appName,'imageworldnews');						    			   
				    		}
	    			   }

                                   else if(appName=='中国经济网'){
		    				  economics[0].className="mo_menu_cur";
		    				  economics[1].className="";
		    				  economics[2].className="";
                                                  if(data!=''){
				    		  		show(data,flag,appName);
							}	
		    		    }
                                   else if(appName=='中安在线'){
		    				  zhongan[0].className="";
		    				  zhongan[1].className="mo_menu_cur";
		    				  zhongan[2].className="";
                                                  if(data!=''){
				    		     show(data,flag,appName);
                                                  }	
		    	           }   
				}
                                else if(flag =='国内新闻'){
						 if(appName=='中安在线'){
							   zhongan[0].className="mo_menu_cur";
							   zhongan[1].className="";
							   zhongan[2].className="";
		                		    	   if(data!=''){			   
				    			    show(data,flag,appName);
                                                          						    			                                   }
					    	
		    			  }
				}
                                else if(flag =='财经新闻'){
						 if(appName=='中安在线'){
							   zhongan[0].className="";
							   zhongan[1].className="";
							   zhongan[2].className="mo_menu_cur";
		                		    	   if(data!=''){			   
				    			    show(data,flag,appName);						    			           }
					    	
		    			  }
			       }
                             else if(flag =='金融新闻'){
					   		if(appName=='中国经济网'){
		    				  economics[0].className="";
		    				  economics[1].className="mo_menu_cur";
		    				  economics[2].className="";
                                                  if(data!=''){
				    		  	show(data,flag,appName);
						}	
		    			  } 
					}
				   
				   
				   else if(flag =='产业新闻'){
					   	 if(appName=='中国经济网'){
		    				  economics[0].className="";
		    				  economics[1].className="";
		    				  economics[2].className="mo_menu_cur";
					          if(data!=''){
				    		  	show(data,flag,appName);
							}	
		    			  } 
					}
			   
			   else if(flag =='美国新闻'){
					 if(appName=='头条新闻网'){
						   toutiaonews[0].className="";
						   toutiaonews[1].className="mo_menu_cur";
						   toutiaonews[2].className="";
	                	   if(data!=''){		    				   
			    			   show(data,flag,appName,'imageworldnews');						    			   
				    		}
	    			   } 
				}
			   
			   else if(flag =='中国新闻'){
					 if(appName=='头条新闻网'){
						   toutiaonews[0].className="";
						   toutiaonews[1].className="";
						   toutiaonews[2].className="mo_menu_cur";
	                	   if(data!=''){		    				   
			    			   show(data,flag,appName,'imageworldnews');						    			   
				    		}
	    			   } 
				}
			   else if(flag =='Top'){
				   times[0].className="mo_menu_cur";
				   times[1].className="";
				   times[2].className="";
				   times[3].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='China'){
				   times[0].className="";
				   times[1].className="mo_menu_cur";
				   times[2].className="";
				   times[3].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='World'){
				   times[0].className="";
				   times[1].className="";
				   times[2].className="mo_menu_cur";
				   times[3].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='Business'){
				   times[0].className="";
				   times[1].className="";
				   times[2].className="";
				   times[3].className="mo_menu_cur";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='关注'){
				   jiefang[0].className="mo_menu_cur";
				   jiefang[1].className="";
				   jiefang[2].className="";
				   jiefang[3].className="";
				   jiefang[4].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='态度'){
				   jiefang[0].className="";
				   jiefang[1].className="mo_menu_cur";
				   jiefang[2].className="";
				   jiefang[3].className="";
				   jiefang[4].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='透视'){
				   jiefang[0].className="";
				   jiefang[1].className="";
				   jiefang[2].className="mo_menu_cur";
				   jiefang[3].className="";
				   jiefang[4].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='岁月'){
				   jiefang[0].className="";
				   jiefang[1].className="";
				   jiefang[2].className="";
				   jiefang[3].className="mo_menu_cur";
				   jiefang[4].className="";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   else if(flag =='影视'){
				   jiefang[0].className="";
				   jiefang[1].className="";
				   jiefang[2].className="";
				   jiefang[3].className="";
				   jiefang[4].className="mo_menu_cur";
            	   if(data!=''){		    				   
	    			   show(data,flag,appName,'imageworldnews');						    			   
		    	   }
				}
			   
			   else if(flag =='政务'){
				   jingji[0].className="";
				   jingji[1].className="mo_menu_cur";
				   jingji[2].className="";
				   jingji[3].className="";
   	    				   
	    		   show(data,flag,appName);						    			   
				}
			   
			   else if(flag =='观点'){
				   jingji[0].className="";
				   jingji[1].className="";
				   jingji[2].className="mo_menu_cur";
				   jingji[3].className="";	    				   
	    		   show(data,flag,appName);						    			   
				}
			   
			   else if(flag =='数据'){
				   jingji[0].className="";
				   jingji[1].className="";
				   jingji[2].className="";
				   jingji[3].className="mo_menu_cur";	    				   
	    		    show(data,flag,appName);						    			   
				}
			   
	           
		   }
	});
}	

function showAppNews1(flag,appName,len){        		
	var htmlString ="";
    //获取项目路径
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var basePath=localhostPaht+projectName;
	$.ajax({
		   type:'POST',
		   async: false,
		   url: basePath+'/mobile/showItemNews/',
		   data: "column="+flag+"&appName="+appName+"&len="+len,
		   success: function(data){
			   if(data!=''){		    				   
    			   var d = eval(data);
    			   for(var i=0;i<d.length;i++){
    				   if(i==0){
    					   htmlString+='<div id="mpub" class="m_pub_banner"><a href='+d[0].surl+' target="_blank"><img src='+d[0].imgsrc+'> <div class="m_pub_banner_txt">'+d[0].title+'</div></a></div>';
    				   }else{
    					   if(d[i].newsText==null){
    							htmlString+='<div class="m_list_item"><div class="m_list_pic"><a href="javascript:void(0)" target="_blank"><img src='+d[i].imgsrc+'></a></div><div class="m_list_txt"><div class="m_list_txt_name"><a  href='+d[i].surl+' target="_blank">'+d[i].title+'</a></div><p>....<font></font></p></div></div>';
    						}else{
    							htmlString+='<div class="m_list_item"><div class="m_list_pic"><a href="javascript:void(0)" target="_blank"><img src='+d[i].imgsrc+'></a></div><div class="m_list_txt"><div class="m_list_txt_name"><a  href='+d[i].surl+' target="_blank">'+d[i].title+'</a></div><p>'+d[i].newsText+'<font></font></p></div></div>';
    						}
    				   }
    			   }
    			   if(appName=='腾讯新闻'){
    			     $("#tmpub").append(htmlString);				    				   
    			   }
    			   else if(appName=='网易新闻'){
    				   $("#nmpub").append(htmlString);  
    			   }
                   else if(appName=='凤凰新闻'){
                	   $("#fmpub").append(htmlString);
                   }

                   else if(appName=='今日头条'){
                	   $("#jrmpub").append(htmlString); 
                   }

			   }
	        }
       });

 }
function show(data,flag,appName){
var htmlString="";
if(data!="")
{
  var d = eval(data);
  for(var i = 0;i<d.length;i++){
		if(i==0){
				    	 
			htmlString+='<div id="mpub" class="m_pub_banner"><a  href='+d[i].surl+' target="_blank"><img src='+d[0].imgsrc+'> <div 	class="m_pub_banner_txt">'+d[0].title+'</div></a></div>'; 
		}else{
			if(d[i].newsText==null){
				htmlString+='<div class="m_list_item"><div class="m_list_pic"><a href="javascript:void(0)" target="_blank"><img src='+d[i].imgsrc+'></a></div><div class="m_list_txt"><div class="m_list_txt_name"><a  href='+d[i].surl+' target="_blank">'+d[i].title+'</a></div><p>....<font></font></p></div></div>';
			}else{
				htmlString+='<div class="m_list_item"><div class="m_list_pic"><a href="javascript:void(0)" target="_blank"><img src='+d[i].imgsrc+'></a></div><div class="m_list_txt"><div class="m_list_txt_name"><a  href='+d[i].surl+' target="_blank">'+d[i].title+'</a></div><p>'+d[i].newsText+'<font></font></p></div></div>';
			}
		 }
  }

   if(flag =='财经'){
		if(appName == '腾讯新闻'){
			$("#tmpub").html(htmlString);
		}
		else if(appName=='网易新闻'){
			$("#nmpub").html(htmlString);
		}
		else if(appName=='新浪新闻'){
			$("#smpub").html(htmlString);
		}
		else if(appName=='凤凰新闻'){
			$("#fmpub").html(htmlString);
		}
		else if(appName=='人民新闻'){
			$("#pmpub").html(htmlString);
		}
		else if(appName=='环球时报'){
			$("#hmpub").html(htmlString);
		}
		else if(appName=='头版'){
			$("#bmpub").html(htmlString);
		}
		else if(appName=='搜狐新闻'){
			$("#shmpub").html(htmlString);
		}
		else if(appName=='央视新闻'){
			$("#ysmpub").html(htmlString);
		}
		else if(appName=='中国新闻网'){
			$("#zgmpub").html(htmlString);
		}
		else if(appName=='今日头条'){
			$("#jrmpub").html(htmlString);
		}
		else if(appName=='新华炫闻'){
			$("#xhmpub").html(htmlString);
		}
		else if(appName=='国搜新闻'){
      	   $("#gsmpub").html(htmlString); 
        }
	}
	if(flag =='科技'){
		if(appName == '腾讯新闻'){
			$("#tmpub").html(htmlString);
		}
		else if(appName=='网易新闻'){
			$("#nmpub").html(htmlString);
		}
		else if(appName=='凤凰新闻'){
			$("#fmpub").html(htmlString);
		}else if(appName=='头版'){
			$("#bmpub").html(htmlString);
		}else if(appName=='今日头条'){
			$("#jrmpub").html(htmlString);
		}
		else if(appName=='新华炫闻'){
			$("#xhmpub").html(htmlString);
		}
		else if(appName=='国搜新闻'){
       	   		$("#gsmpub").html(htmlString); 
        		}
                    else if(appName=='360新闻'){
       	   		$("#qhmpub").html(htmlString); 
        		}
	}
	if(flag =='军事'){
		if(appName == '腾讯新闻'){
			$("#tmpub").html(htmlString);
		}
		else if(appName=='网易新闻'){
			$("#nmpub").html(htmlString);
		}
		else if(appName=='新浪新闻'){
			$("#smpub").html(htmlString);
		}
		else if(appName=='凤凰新闻'){
			$("#fmpub").html(htmlString);
		}
		else if(appName=='人民新闻'){
			$("#pmpub").html(htmlString);
		}
		else if(appName=='环球时报'){
			$("#hmpub").html(htmlString);
		}
		else if(appName=='头版'){
			$("#bmpub").html(htmlString);
		}
		else if(appName=='搜狐新闻'){
			$("#shmpub").html(htmlString);
		}
		else if(appName=='央视新闻'){
			$("#ysmpub").html(htmlString);
		}
		else if(appName=='中国新闻网'){
			$("#zgmpub").html(htmlString);
		}	
		else if(appName=='今日头条'){
			$("#jrmpub").html(htmlString);
		}
		else if(appName=='国搜新闻'){
      	   $("#gsmpub").html(htmlString); 
         }
                    else if(appName=='360新闻'){
      	   $("#qhmpub").html(htmlString); 
         }else if(appName=='新华炫闻'){
        	   $("#xhmpub").html(htmlString); 
         }
	}
	if(flag =='国际'){
		if(appName=='新浪新闻'){
			$("#smpub").html(htmlString);
		}
		else if(appName=='人民新闻'){
			$("#pmpub").html(htmlString);
		}
		else if(appName=='搜狐新闻'){
			$("#shmpub").html(htmlString);
		}
		else if(appName=='中国新闻网'){
			$("#zgmpub").html(htmlString);
		}
		else if(appName=='今日头条'){
			$("#jrmpub").html(htmlString);
		}
		else if(appName=='新华炫闻'){
			$("#xhmpub").html(htmlString);
		}
		else if(appName=='国搜新闻'){
      	   $("#gsmpub").html(htmlString); 
        }
                    else if(appName=='360新闻'){
       	   $("#qhmpub").html(htmlString); 
        }
	}

	
	if(flag =='社会'){
		if(appName == '腾讯新闻'){
			$("#tmpub").html(htmlString);
		}
		else if(appName=='搜狐新闻'){
			$("#shmpub").html(htmlString);
		}
		else if(appName=='央视新闻'){
			$("#ysmpub").html(htmlString);
		}
		else if(appName=='中国新闻网'){
			$("#zgmpub").html(htmlString);
		}
		else if(appName=='今日头条'){
			$("#jrmpub").html(htmlString);
		}
                    else if(appName=='360新闻'){
       	   $("#qhmpub").html(htmlString); 
        }else if(appName=='新华炫闻'){
     	   $("#xhmpub").html(htmlString); 
        }
	}
	
	if(flag =='国内'){
		if(appName=='新浪新闻'){
			$("#smpub").html(htmlString);
		}
		else if(appName=='环球时报'){
			$("#hmpub").html(htmlString);
		}
		else if(appName=='中国新闻网'){
			$("#zgmpub").html(htmlString);
		}
		else if(appName=='新华炫闻'){
			$("#xhmpub").html(htmlString);
		}
                    else if(appName=='360新闻'){
       	   $("#qhmpub").html(htmlString); 
         }
	}
	if(flag =='要闻'){
		if(appName=='腾讯新闻'){
			$("#tmpub").html(htmlString);
		}
		else if(appName=='环球时报'){
			$("#hmpub").html(htmlString);
		}
		else if(appName=='搜狐新闻'){
			$("#shmpub").html(htmlString);
		}
		else if(appName=='央视新闻'){
			$("#ysmpub").html(htmlString);
		}
	}
	
	if(flag =='头条'){
		if(appName=='网易新闻'){
			$("#nmpub").html(htmlString);
		}
		else if(appName=='新浪新闻'){
			$("#smpub").html(htmlString);
		}
		else if(appName=='凤凰新闻'){
			$("#fmpub").html(htmlString);
		}
		else if(appName=='经济日报'){
			$("#jjrmpub").html(htmlString);
		}
	}
	
	if(flag =='数据'){
		if(appName=='经济日报'){
			$("#jjrmpub").html(htmlString);
		}
	}
	
	if(flag =='观点'){
		if(appName=='经济日报'){
			$("#jjrmpub").html(htmlString);
		}
	}
	
	if(flag =='时政'){
		if(appName=='凤凰新闻'){
			$("#fmpub").html(htmlString);
		}
	}
	if(flag =='政务'){
		$("#jjrmpub").html(htmlString);
	}
}else{
	if(appName=='腾讯新闻')
		$("#tmpub").html("<span style=\"color:red\">暂时没有数据</span>");
	else if(appName=='网易新闻')
		$("#nmpub").html("<span style=\"color:red\">暂时没有数据</span>");
	else if(appName=='凤凰新闻')
		$("#fmpub").html("<span style=\"color:red\">暂时没有数据</span>");
	else if(appName=='经济日报')
		$("#jjrmpub").html("<span style=\"color:red\">暂时没有数据</span>");
}
}