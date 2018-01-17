//运行环境
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
	}else{
		type = 0;
		$("#left-menu").removeClass("displayNone");
		$("#header-right").removeClass("displayNone");
		$("#left-menu-chart").removeClass("displayNone");
		$("#menuDiv").remove();
		$("#userDiv").remove();
		$("#pcDiv").removeClass("displayNone");
		resizeSize(200,60,360);
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