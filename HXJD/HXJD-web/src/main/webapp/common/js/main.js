layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	
	if(device.android || device.ios){
		$("#bigDiv").insertAfter($("#anchorI"));  
	}
});