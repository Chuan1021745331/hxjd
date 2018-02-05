//运行环境

var firstItem ;
var lastItem ;
var itemIndex;
var flag=true;



var type = 0 ;
layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	
	if(device.android || device.ios){
		type = 1;
		$("#pcDiv").remove();
		$("#left-menu-chart").remove();
        $("#more-news").remove();
		$("#logoDiv").removeClass("layui-logo");
		$("#logoDiv").addClass("layui-logo-p");
		$("#menuDiv").removeClass("displayNone");
		$("#userDiv").removeClass("displayNone");
		$("#bottomDiv").removeClass("displayNone");
		$(".layui-layout-admin .layui-body").css("bottom","0px");
		$("#zero_tab_content").css("left","0px");
		$("#left-menu").css("bottom","52px");
		$("right-user").css("bottom","52px");
		resizeSize(0,112,0);
		
		$("#items").css("display","none");
		
	}else{
		type = 0;
		$("#left-menu").removeClass("displayNone");
		$("#header-right").removeClass("displayNone");
		$("#left-menu-chart").removeClass("displayNone");
		$("#menuDiv").remove();
		$("#userDiv").remove();
		$("#pcDiv").removeClass("displayNone");
		resizeSize(180,60,360);
	}
	function resizeSize(left,size,width) {
		$(window).on('resize', function() {
	    	$('#zero_tab_content').width($(window).width()-left-width).height($(window).height()-size);
	    	$('#zero_tab_content').find('iframe').each(function() {
	    		$(this).height($('#zero_tab_content').height()-3);
	    		$(this).width($('#zero_tab_content').width()-width);
	    	});
	    	$("#right-user .paddingDiv").css("height",$('#zero_tab_content').height()-115);
	    }).resize();
	}
	$("#zero_tab_content").click(function(){
		alert("dddd");
	});
	$("#menuDiv").click(function(){
		$("#left-menu").toggle();
		if($("#left-menu").css("display") == 'none' ){
			$("#menuDiv").removeClass("color-black");
		}else{
			$("#menuDiv").addClass("color-black");
		}
		if(type==1){
			$("#right-user").hide();
			if($("#right-user").css("display") == 'none' ){
				$("#userDiv").removeClass("color-black");
			}else{
				$("#userDiv").addClass("color-black");
			}
			if($("#left-menu").css("display") == 'none' ){
				$("#menuDiv").removeClass("color-black");
			}else{
				$("#menuDiv").addClass("color-black");
			}
			
		}
	});
	$("#header-right").click(function(){
		$("#right-user").toggle();
	});
	$("#userDiv").click(function(){
		$("#right-user").toggle();
		
		if(type==1){
			$("#left-menu").hide();
			if($("#right-user").css("display") == 'none' ){
				$("#userDiv").removeClass("color-black");
			}else{
				$("#userDiv").addClass("color-black");
			}
			if($("#left-menu").css("display") == 'none' ){
				$("#menuDiv").removeClass("color-black");
			}else{
				$("#menuDiv").addClass("color-black");
			}
		}
	});

	$("#user-sel").click(function (){
		var area=['30%', '86%'];
        if(device.android || device.ios){
        	area=['90%', '534px'];
		}
        var index = layer.open({
			id:'visitor-sel',
            type: 2 ,
            title: '访客详情',
            content: bpath+"/visitor/sel?id="+visitorId,
            resize: false,
            zIndex:99999,
            area: area,
            anim: Math.ceil(Math.random() * 6),
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        });
	});
    $("#user-edit").click(function (){
        var area=['30%', '30%'];
        if(device.android || device.ios){
            area=['90%', '40%'];
        }
        var index = layer.open({
            type: 2 ,
            title: '修改密码',
            content: bpath+"/visitor/edit?id="+visitorId,
            resize: false,
            area: area,
            anim: Math.ceil(Math.random() * 6),
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500);
            }
        });
    });
    $("#user-logout").click(function (){
        layer.confirm('是否确认退出',{
            icon: 3,
            skin:'zero-green',
            title: "退出登录",
            offset: '200px',
            closeBtn: 0,
            skin: 'layui-layer-molv',
            anim: Math.ceil(Math.random() * 6),
            btn: ['确定', '取消']
		},function () {
			window.location.href=bpath+'/logout';
        });
    });

    $("#more-news").click(function () {
    	var width=$("#left-menu-chart").width();
    	var height=$("#left-menu-chart").height();
    	console.log("width"+width);
    	console.log("height"+height);
        var area=[width+'px', height+'px'];
        var index = layer.open({
            id:"more-news-list",
            type: 2 ,
            title: '今日头条',
            content: bpath+"/news",
            resize: false,
            zIndex:999,
            area: area,
            offset:'rb'	,
            move: false,
            shade:0,
            anim: Math.ceil(Math.random() * 6),
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        });
    });
    
});
function drawChart() {
	if(type==1){
		return;
	}
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'),'shine');
    var myChart1 = echarts.init(document.getElementById('main1'),'shine');
    var myChart2 = echarts.init(document.getElementById('main2'),'shine');

	option = {
	    title: {
	        text: '世界人口总量',
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	        data: ['总数', '已完工']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        data: ['盾构(台)','深基(个)','暗挖(个)','高架(个)']
	    },
	    yAxis: {
	        
	        type: 'value',
	        boundaryGap: [0, 0.01]
	    },
	    series: [
	        {
	            name: '总数',
	            type: 'bar',
	            data: [20, 30, 40, 50]
	        },
	        {
	            name: '已完工',
	            type: 'bar',
	            data: [10, 15, 10, 30]
	        }
	    ]
	};
	option2 = {
	    title : {
	        text: '某站点用户访问来源',
	        subtext: '纯属虚构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['在建','运营','拟开工']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:335, name:'在建'},
	                {value:310, name:'运营'},
	                {value:1548, name:'拟开工'}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
    myChart.setOption(option);
    myChart1.setOption(option);
    myChart2.setOption(option2);
}


function newsScroll(){
	if(flag){//向上滚动
		if(itemIndex == 1){
			flag = false;
			itemIndex = $("#items").children().size();
		} else {
			firstItem.animate({marginTop:"-=19px"});			
			itemIndex = itemIndex - 1;
		}
	} else {//向下滚动
 		if(itemIndex == 1){
			flag = true;
			itemIndex = $("#items").children().size();
		} else {
			firstItem.animate({marginTop:"+=19px"});			
			itemIndex = itemIndex - 1;
		}
 		
	} 
}