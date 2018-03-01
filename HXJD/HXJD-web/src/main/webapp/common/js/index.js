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
		$("#fold-chart").remove();
		$("#items").css("display","none");
		
	}else{
		type = 0;
		$("#left-menu").removeClass("displayNone");
		$("#header-right").removeClass("displayNone");
		$("#left-menu-chart").removeClass("displayNone");
		$("#menuDiv").remove();
		$("#userDiv").remove();
		$("#pcDiv").removeClass("displayNone");
		$("#fold-chart").removeClass("displayNone");
		$("#more-news").removeClass("displayNone");
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
			//添加遮罩
            $("body").append("<div class=\"layui-layer-shade\" id=\"layui-layer-shade1\" times='1'  style=\"z-index: 999; background-color: rgb(0, 0, 0); opacity: 0;\"></div>")
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

	//遮罩点击事件
    $("body").on("click","#layui-layer-shade1",function () {
        $("#menuDiv").trigger("click");
        $("#layui-layer-shade1").remove();
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

    //显示访客详情
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
	//修改访客密码
    $("#user-edit").click(function (){
        var area=['420px', '220px'];
        if(device.android || device.ios){
            area=['90%', '35%'];
        }
        var index = layer.open({
            type: 2 ,
            title: '修改密码',
            content: bpath+"/visitor/edit?id="+visitorId,
            resize: false,
            area: area,
            move: false,
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
    //退出登录
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

    var moreNewsIndex=0;
    $("#more-news").click(function () {
    	// var width=$("#left-menu-chart").width();
    	var width=360;
    	var height=$("#left-menu-chart").height();
        var area=[width+'px', height+'px'];

        //隐藏个人详情
		if($("#right-user").css("display")!="none"){
            $("#right-user").toggle();
		}
        moreNewsIndex = layer.open({
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
            },
            end : function(){
                moreNewsIndex=0;
            }
        });
    });

    $("#bottomDiv").children().click(function () {
		//隐藏个人详情
        if($("#right-user").css("display")!="none"){
			$("#userDiv").trigger("click");
        }
    });

    //图表缩进
    $("#fold-chart").click(function () {
        var chartRight=0;
        var floadRight=360;
        var time=500;
        if($("#left-menu-chart").css("right")=='0px'){
            chartRight=-361;
            floadRight=0;
            $(this).find("i").html("&#xe65a;")
            $('#zero_tab_content').animate({
                width:"+=360px"
            },time);
        }else{
            $(this).find("i").html("&#xe65b;");
            $('#zero_tab_content').animate({
                width:"-=360px"
            },time);
        }
        $("#left-menu-chart").animate({
            right:chartRight+"px"
        },time);
        $(this).animate({
            right:floadRight+"px"
        },time);
    });
    $(window).resize(function() {
        var width=360;
        var height=$(window).height()-60;
    	if(moreNewsIndex!=0){
            layer.style(moreNewsIndex, {
                width: width+'px',
                height: height+'px'
            });
        }
    });

    //树形菜单
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    $(function() {
        var setting = {
            isSimpleData: true,              //数据是否采用简单 Array 格式，默认false
            treeNodeKey: "id",               //在isSimpleData格式下，当前节点id属性
            showLine: true,                  //是否显示节点间的连线
            view: {
                /*addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,*/
                selectedMulti: false
            },
            edit: {
                enable: false,
                editNameSelectAll: true,
               /* showRemoveBtn: showRemoveBtn,
                showRenameBtn: showRenameBtn,
                removeTitle: "删除节点",
                renameTitle: "编辑节点"*/
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId"
                }
            },
            callback: {
                beforeClick: beforeClick,
                onClick: zTreeOnClick
            }
        };


        function beforeClick(treeId, treeNode) {
            var check = (treeNode && !treeNode.isParent);
            /*if(treeNode.id!=0){
             if (!check) top.parent.MSG(2, "末尾节点才拥有功能按钮");
             return check;
             }*/

        }

        /**
		 * 点击节点,展开路线工地信息
         * @param event
         * @param treeId
         * @param treeNode
         */
        function zTreeOnClick(event, treeId, treeNode) {
            where = treeNode.id;
            //1级为路线，2级为工点
            var atype=treeNode.level;
            var adata = treeNode.id;
            var url;
            if(atype == 1){
                url = bpath+"/route/index?cid=" + adata;
            }else if(atype == 2) {
                url = bpath+"/route/workSiteSel?wid=" + adata;
            }else {
                url = bpath+"/main";
            }

            $(".tbmiframe").attr("src", url);
        };

        function reloadTreeData() {
            $.ajax({
                url: bpath + "/route/tree",
                type: 'get',
                dataType: 'json',
                success: function (data) {
                    zTree = $.fn.zTree.init($("#treeDemo"), setting, data);
                    zTree.expandAll(true);
                }
            });
        }
        reloadTreeData();
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
	var height = $("#items").children().first().height();
	if($("#items").children().length<0){
		return false;
	}
	if(flag){//向上滚动
		if(itemIndex == 2){
			flag = false;
			itemIndex = $("#items").children().size();
		} else {
			firstItem.animate({marginTop:"-="+height+"px"});			
			itemIndex = itemIndex - 1;
		}
	} else {//向下滚动
 		if(itemIndex == 2){
			flag = true;
			itemIndex = $("#items").children().size();
		} else {
			firstItem.animate({marginTop:"+="+height+"px"});			
			itemIndex = itemIndex - 1;
		}
 		
	} 
}
function newsDetails(id){
	var windowHeight = window.screen.availHeight;
	var windowWidth = window.screen.availWidth;

	var height = Math.ceil(windowHeight*0.71);
	var width = Math.ceil(windowWidth*0.71);
	var area = [width+'px',height+"px"];
	var index = parent.layer.open({
        id:"news-sel",
        type: 2 ,
        title: '新闻详情',
        content: bpath+"/news/getNewsById?id="+id,
        resize: false,
        area: area,
        move: false,
        shade:0,
        anim: Math.ceil(Math.random() * 6),
        success : function(layero, index){
        	
        }
    });
}
