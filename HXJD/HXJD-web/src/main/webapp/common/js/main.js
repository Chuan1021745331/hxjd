layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	var bigDivW = $("#bigDiv").outerWidth(),
		bigDivH = $("#bigDiv").outerHeight(),
		zero_tab_contentH = parent.$("#zero_tab_content").outerHeight()-3;
	console.log(bigDivW+","+bigDivH+","+zero_tab_contentH);
	if(device.android || device.ios){
		$("#bigDiv").insertAfter($("#anchorI"));
		
		drawCircle();
	}else{
		drawCircle();
	}
	if(bigDivW<zero_tab_contentH){
		$("#circleDiv").width(bigDivW).height(bigDivW);
		
	}else if(bigDivW>zero_tab_contentH){
		$("#circleDiv").width(zero_tab_contentH).height(zero_tab_contentH);
	}
	function drawCircle(size) {
		
	}
});