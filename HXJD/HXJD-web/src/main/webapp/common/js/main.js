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
		wh,
		D_value=0;
	
	
	
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
		if(bigDivW>$("#bigDiv").height()){
			D_value = (bigDivW-$("#bigDiv").height())/2;
		}
		$("#circleDiv").css("left",Math.floor(D_value));
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