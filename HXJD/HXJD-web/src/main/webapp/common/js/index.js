layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	
	if(device.android || device.ios){
		$("#pcDiv").remove();
		$("#phoneDiv").removeClass("displayNone");
		$(".layui-layout-admin .layui-body").css("bottom","0px");
		resizeSize(50);
	}else{
		$("#phoneDiv").remove();
		$("#pcDiv").removeClass("displayNone");
		resizeSize(96);
	}
	$("#phoneDiv").click(function(){
		$(".phoneUl").toggle();
	});
	function resizeSize(size) {
		$(window).on('resize', function() {
	    	$('#zero_tab_content').width($(window).width()).width($(window).width()).height($(window).width()).height($(window).height()-size);
	    	$('#zero_tab_content').find('iframe').each(function() {
	    		$(this).height($('#zero_tab_content').height()-3);
	    		$(this).width($('#zero_tab_content').width());
	    	});
	    }).resize();
	}
});