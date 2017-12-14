layui.config({
	base: cpath+'/lib/'
});
layui.use(['jquery','layer','element','common','larryMenu','form'],function(){
	var $ = layui.$,
	layer = layui.layer,
	common = layui.common,
	device = layui.device(),
	form = layui.form,
	element = layui.element;
    // 页面上下文菜单
    larryMenu = layui.larryMenu();
    layui.laytpl.sexTpl = function (data){
    	if("0"==data){
    		return  '<div><input type="checkbox" name="zzz" lay-skin="switch" lay-text="男|女" disabled><div>';
    	}else{
    		return  '<div><input type="checkbox" name="zzz" lay-skin="switch" lay-text="男|女" disabled checked><div>';
    	}
    }
    // 右键菜单控制
    var larrycmsMenuData = [
		[{
			text: "刷新当前页",
		    func: function() {
		    	$(".layui-tab-content .layui-tab-item", parent.document).each(function() {
		    		if ($(this).hasClass('layui-show')) {
		    			$(this).children('iframe')[0].contentWindow.location.reload(true);
		    		}
		    	});
		    }
		},{
			text: "重载主框架",
			func: function() {
				top.document.location.reload();
			}
		},{
			text: "选项卡常用操作",
			data: [
				[{
					text: "定位当前选项卡",
					func: function() {
						top.document.getElementById('tabCtrD').click();
					}
				},{
					text: "关闭当前选项卡",
					func: function() {
						top.document.getElementById('tabCtrA').click();
					}
				}, {
					text: "关闭其他选项卡",
					func: function() {
						top.document.getElementById('tabCtrB').click();
					}
				}, {
					text: "关闭全部选项卡",
					func: function() {
						top.document.getElementById('tabCtrC').click();
					}
				}]
			]
		}, {
			text: "清除缓存",
			func: function() {
				top.document.getElementById('clearCached').click();
			}
		}]
	];
	larryMenu.ContentMenu(larrycmsMenuData,{
         name: "html" 
	},$('html'));
	// 右键菜单结束
	$('#larry_tab_content', parent.document).mouseout(function(){
         larryMenu.remove();
	});
});