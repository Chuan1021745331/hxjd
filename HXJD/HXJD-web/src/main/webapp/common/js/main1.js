layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	//运行环境
	var type = 0 ;
	
	if(device.android || device.ios){
		type = 1;
	}
});