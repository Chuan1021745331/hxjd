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
	
	var client ;
	var pixelRatio = window.devicePixelRatio;//设备像素比
	var data;
	
	if(device.android || device.ios){
		client = "phone";
		$("#bigDiv").insertAfter($("#anchorI"));
		bigDivW = parent.$("#zero_tab_content").outerWidth();
		drawCircle(0,bigDivW,zero_tab_contentH);
	}else{
		client = "pc";
		if(zero_tab_contentW<1024){
			$("#bigDiv").insertAfter($("#anchorI"));
			bigDivW = parent.$("#zero_tab_content").outerWidth();
			drawCircle(0,bigDivW,zero_tab_contentH);
		}else{
			drawCircle(1,bigDivW,zero_tab_contentH);
		}
		
	}
						//0   414      683
	function drawCircle(type,bigDivW,zero_tab_contentH) {
		if(bigDivW<zero_tab_contentH){
			$("#circleDiv").attr("width",(bigDivW-10)*pixelRatio).attr("height",(bigDivW-10)*pixelRatio);
			drawCircleOneLev(type,(bigDivW-10)/2);
			wh = bigDivW;
		}else if(bigDivW>zero_tab_contentH){
			$("#circleDiv").attr("width",(zero_tab_contentH-10)*pixelRatio).attr("height",(zero_tab_contentH-10)*pixelRatio);
			drawCircleOneLev(type,(zero_tab_contentH-10)/2);
			wh = zero_tab_contentH-10;
		}
	}
	function drawCircleOneLev(type,size) {
		$("#bigDiv").height(size*2+10);
		if(bigDivW>$("#bigDiv").height()){
			D_value = (bigDivW-$("#bigDiv").height())/2;
		}
		if(client == "phone"){
			$("#circleDiv").css("left",Math.floor(5));
		} else{
			$("#circleDiv").css("left",Math.floor(D_value));
		} 
		$("#circleDiv").css("width",($("#circleDiv").attr("width"))/pixelRatio);
		$("#circleDiv").css("height",($("#circleDiv").attr("height"))/pixelRatio);
	}
	
	var beauty = new Image(),
	tbm_Img = ctx+"/images/tbm.png";  
	beauty.src = tbm_Img;
	var canvas = document.getElementById("circleDiv");
	var ctx1 = canvas.getContext("2d");	
	//简单地检测当前浏览器是否支持Canvas对象，以免在一些不支持html5的浏览器中提示语法错误
	if(canvas.getContext){  
	    //获取对应的CanvasRenderingContext2D对象(画笔)
		
		
	    beauty.onload = DrawData(data);
	    	
	    	//DrawData();

	}
	
	
	function DrawData(data){
		ctx1.clearRect(0,0,width,height);
		ctx1.drawImage(beauty, 0, 0,(wh-10)*pixelRatio,(wh-10)*pixelRatio);
    	var width = $("#circleDiv").attr("width");
		var height = $("#circleDiv").attr("height");
    	
    	ctx1.globalAlpha=0.6;
	    //ctx2.globalCompositeOperation="source-over";		    
    	ctx1.fillStyle="#FFFFFF";
	    //上
    	ctx1.fillRect(width*0.430, height*0.17, width*0.13, height*0.075);//左上角x,y, width, height
	    //左
    	ctx1.fillRect(width*0.140, height*0.443, width*0.13, height*0.075);//左上角x,y, width, height
	    //右
    	ctx1.fillRect(width*0.725, height*0.443, width*0.13, height*0.075);//左上角x,y, width, height		    
	    //左下
    	ctx1.fillRect(width*0.260, height*0.684, width*0.13, height*0.075);//左上角x,y, width, height
	    //右下
    	ctx1.fillRect(width*0.612, height*0.684, width*0.13, height*0.075);//左上角x,y, width, height
	    
	    //中心圆
    	ctx1.beginPath();
	    if(client == "phone"){
	    	ctx1.arc(width*0.50, height*0.50, width*0.220, 0 ,2*Math.PI);	
	    } else {
	    	ctx1.arc(width*0.495, height*0.494, width*0.220, 0 ,2*Math.PI);	
	    }
	    	    	    
	    //ctx2.strokeStyle		    
	    ctx1.closePath();
	    ctx1.stroke();
	    ctx1.fillStyle="#FFFFFF";
	    ctx1.fill();
	//周边文字
	    //上 下 左 右
	    ctx1.fillStyle="#000000";
	    ctx1.globalAlpha=1;
	    //0.6 || 1
	    //0.018-0.023
	    if( client == "phone"){
	    	ctx1.font=""+width*0.027+"px Arial";
	    	ctx1.fillText("D组推进位移: ", width*0.380, height*0.060);
		    ctx1.fillText("D组推进压力: ", width*0.380, height*0.100);
		    
		    ctx1.fillText("B组推进位移: ", width*0.380, height*0.910);
		    ctx1.fillText("B组推进压力: ", width*0.380, height*0.950);
	    } else {
	    	ctx1.font=""+width*0.018+"px Arial";
	    	ctx1.fillText("D组推进位移: ", width*0.400, height*0.060);
		    ctx1.fillText("D组推进压力: ", width*0.400, height*0.090);
		    
		    ctx1.fillText("B组推进位移: ", width*0.400, height*0.910);
		    ctx1.fillText("B组推进压力: ", width*0.400, height*0.940);
	    }
	    /*
	    ctx1.fillText("D组推进位移: ", width*0.400, height*0.060);
	    ctx1.fillText("D组推进压力: ", width*0.400, height*0.090);
	    
	    ctx1.fillText("B组推进位移: ", width*0.400, height*0.910);
	    ctx1.fillText("B组推进压力: ", width*0.400, height*0.940);
	    */
	    ctx1.fillText("C组位移", width*0.020, height*0.450);
	    ctx1.fillText("C组压力", width*0.020, height*0.540);
	    
	    ctx1.fillText("A组位移", width*0.870, height*0.450);
	    ctx1.fillText("A组压力", width*0.870, height*0.540);
	    
	    ctx1.fillText("上部土压", width*0.450, height*0.200);
	    ctx1.fillText("左中土压", width*0.160, height*0.470);
	    ctx1.fillText("右中土压", width*0.740, height*0.470);
	    ctx1.fillText("左下土压", width*0.270, height*0.710);
	    ctx1.fillText("右下土压", width*0.622, height*0.710);
	    
	  //中心圆
	    ctx1.fillText("推进压力: ", width*0.400, height*0.340);
	    ctx1.fillText("推进速度: ", width*0.400, height*0.380);
	    ctx1.fillText("总推进力: ", width*0.400, height*0.420);
	    
	    ctx1.fillText("仰俯角: ", width*0.400, height*0.480);
	    ctx1.fillText("滚动角: ", width*0.400, height*0.520);
	    ctx1.fillText("贯入度: ", width*0.400, height*0.560);
	    
	    ctx1.fillText("抓举头角: ", width*0.400, height*0.620);
	    ctx1.fillText("抓举头压力: ", width*0.400, height*0.660);
		

		var width = $("#circleDiv").attr("width");
		var height = $("#circleDiv").attr("height");
		var dataCanvas = document.getElementById("circleData");
		$("#circleData").attr("width",$("#circleDiv").attr("width"));
		$("#circleData").attr("height",$("#circleDiv").attr("height"));
		$("#circleData").attr("style",$("#circleDiv").attr("style"));
		if(data != null && typeof(data) != "undefined"){
			
		
			if(dataCanvas.getContext){  
			    //获取对应的CanvasRenderingContext2D对象(画笔)
			    var ctx2 = canvas.getContext("2d");
			    //ctx2.globalCompositeOperation="source-over";	
			    
			    
			//数据    
			    ctx2.globalAlpha=1;
			    ctx2.fillStyle="#FF8C00";
			    
			    if( client == "phone"){
			    	ctx2.fillText(data.d1+" mm", width*0.550, height*0.060);
				    ctx2.fillText(data.b1+" mm", width*0.550, height*0.910);
			    } else {
			    	ctx2.fillText(data.d1+" mm", width*0.530, height*0.060);
				    ctx2.fillText(data.b1+" mm", width*0.530, height*0.910);
			    }
			    
			    ctx2.fillText(data.c1+" mm", width*0.020, height*0.480);
			    ctx2.fillText(data.a1+" mm", width*0.870, height*0.480);
			    
			    ctx2.globalAlpha=1;
			    ctx2.fillStyle="#32CD32";
			    
			    if( client == "phone"){
			    	ctx2.fillText(data.d2+" bar", width*0.550, height*0.100);
				    ctx2.fillText(data.b2+"10 bar", width*0.550, height*0.950);
			    	
				  //中心圆
				    ctx2.fillText(data.ring1 + " bar", width*0.520, height*0.340);
				    ctx2.fillText(data.ring1 + " mm/min", width*0.520, height*0.380);
				    ctx2.fillText(data.ring1 + " KN", width*0.520, height*0.420);
				    
				    ctx2.fillText(data.ring1 + " °", width*0.500, height*0.480);
				    ctx2.fillText(data.ring1 + " °", width*0.500, height*0.520);
				    ctx2.fillText(data.ring1 + " mm/r", width*0.500, height*0.560);
				    
				    ctx2.fillText(data.ring1 + " °", width*0.520, height*0.620);
				    ctx2.fillText(data.ring1 + " bar", width*0.550, height*0.660);
			    } else {
			    	ctx2.fillText(data.d2+" bar", width*0.530, height*0.090);
				    ctx2.fillText(data.b2+" bar", width*0.530, height*0.940);
				  //中心圆
				    ctx2.fillText(data.ring1 + " bar", width*0.490, height*0.340);
				    ctx2.fillText(data.ring2 + " mm/min", width*0.490, height*0.380);
				    ctx2.fillText(data.ring3 + " KN", width*0.490, height*0.420);
				    
				    ctx2.fillText(data.ring4 + " °", width*0.490, height*0.480);
				    ctx2.fillText(data.ring5 + " °", width*0.490, height*0.520);
				    ctx2.fillText(data.ring6 + " mm/r", width*0.490, height*0.560);
				    
				    ctx2.fillText(data.ring7 + " °", width*0.500, height*0.620);
				    ctx2.fillText(data.ring8 + " bar", width*0.510, height*0.660);
			    }
			    
			    ctx2.fillText(data.c2+" bar", width*0.020, height*0.580);
			    ctx2.fillText(data.a2+" bar", width*0.870, height*0.580);
			    
			    ctx2.fillText(data.outRing1 + " bar", width*0.450, height*0.230);
			    ctx2.fillText(data.outRing2 + " bar", width*0.160, height*0.500);
			    ctx2.fillText(data.outRing3 + " bar", width*0.740, height*0.500);
			    ctx2.fillText(data.outRing4 + " bar", width*0.270, height*0.740);
			    ctx2.fillText(data.outRing5 + " bar", width*0.622, height*0.740); 
			}
		}		
	}	

	//var bpath = ${BPATH};
	//var path =  window.location.host;
	//console.log(path);
	ws1 = new WebSocket("ws://" + window.location.host + "/IndexWebsocketTest.ws");
	ws1.onopen = function(event) {
     		console.log("websocket ws1 has been opened!");  
     		ws1.send("1");
     		
    };
    ws1.onmessage = function(event) {//WebSocket收到消息时调用
 		var received_msg = event.data;
 		// console.log("socketData: " + received_msg);
 		data = eval('(' + received_msg + ')');
 		DrawData(data);
 	}
    ws1.onclose = function(event) {
 		console.log("websocket ws1 has been closed!");
 		clearInterval();
 	};
  	ws1.onerror = function(event) {
 		ws1 = undefined;
 	}; 
 	window.onunload = function() {
        ws.close();
    }
 	function sendMessage(){
 		ws1.send("1");
 	}
 	setInterval(sendMessage, 1000); //循环执行！！
	
});