layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	var bigDivW = $("#bigDiv").outerWidth(),
		bigDivH = $("#bigDiv").outerHeight(),
		zero_tab_contentH = parent.$("#zero_tab_content").outerHeight()-3,
		zero_tab_contentW = parent.$("#zero_tab_content").outerWidth(),
		wh;
	
	if(device.android || device.ios){
		$("#bigDiv").insertAfter($("#anchorI"));
		bigDivW = parent.$("#zero_tab_content").outerWidth();
		drawCircle(0,bigDivW,zero_tab_contentH);
	}else{
		if(zero_tab_contentW<1024){
			$("#bigDiv").insertAfter($("#anchorI"));
			bigDivW = parent.$("#zero_tab_content").outerWidth();
			drawCircle(0,bigDivW,zero_tab_contentH);
		}else{
			drawCircle(1,bigDivW,zero_tab_contentH);
		}
		
	}
	
	function drawCircle(type,bigDivW,zero_tab_contentH) {
		if(bigDivW<zero_tab_contentH){
			$("#circleDiv").attr("width",bigDivW-10).attr("height",bigDivW-10);
			drawCircleOneLev(type,(bigDivW-10)/2);
			wh = bigDivW;
		}else if(bigDivW>zero_tab_contentH){
			$("#circleDiv").attr("width",zero_tab_contentH-10).attr("height",zero_tab_contentH-10);
			drawCircleOneLev(type,(zero_tab_contentH-10)/2);
			wh = zero_tab_contentH-10;
		}
	}
	function drawCircleOneLev(type,size) {
		$("#bigDiv").height(size*2+10);
		$("#top-left-1").width(size-2).height(size);
		$("#top-right-1").width(size-2).height(size);
		$("#buttom-left-1").width(size-2).height(size);
		$("#buttom-right-1").width(size-2).height(size);
		
		$("#top-left-1").css("border-radius",""+size+"px 0px 0px 0px");
		$("#top-right-1").css("border-radius","0px "+size+"px 0px 0px");
		$("#buttom-left-1").css("border-radius","0px 0px 0px "+size+"px");
		$("#buttom-right-1").css("border-radius","0px 0px "+size+"px 0px");
		if(type==0){
			$("#dataDiv").css("font-size","12px");
			$("#dataDiv #left-D").css({"top":$("#bigDiv").height()/2-35+"px","left":10+"px","position":"fixed","width":"60px"});
			$("#dataDiv #right-D").css({"top":$("#bigDiv").height()/2-35+"px","right":5+"px","position":"fixed","width":"60px"});
		}else{
			$("#dataDiv #left-D").css({"top":$("#bigDiv").height()/2+"px","position":"fixed","width":"60px"});
			$("#dataDiv #right-D").css({"top":$("#bigDiv").height()/2+"px","position":"fixed","width":"60px"});
		}
		$("#dataDiv #top-D").css({"top":10+"px","right":(bigDivW/2)-110+"px","position":"fixed"});
		$("#dataDiv #bottom-D").css({"top":$("#bigDiv").height()-50+"px","right":(bigDivW/2)-110+"px","position":"fixed"});
	}
	var beauty = new Image(),
	tbm_Img = ctx+"/images/tbm.png";  
	beauty.src = tbm_Img;
	var canvas = document.getElementById("circleDiv");
	//简单地检测当前浏览器是否支持Canvas对象，以免在一些不支持html5的浏览器中提示语法错误
	if(canvas.getContext){  
	    //获取对应的CanvasRenderingContext2D对象(画笔)
	    var ctx1 = canvas.getContext("2d");
	    beauty.onload = function(){
	    	ctx1.drawImage(beauty, 0, 0,wh-10,wh-10);
        }
	}
});