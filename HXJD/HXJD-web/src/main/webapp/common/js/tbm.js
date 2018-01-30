layui.use(['jquery', 'layer', 'element'], function() {
	var $ = layui.jquery,
		layer = layui.layer,
		common = layui.common,
		element = layui.element,
		device = layui.device();
	var wh,
		D_value=0;
	
	var client ;
	var data;
	if(device.android || device.ios){
		client = "phone";
		$("#phoneDiv").css("display","block");
		$("#pcDiv").css("display","none");
	}else{
		client = "pc";
		$("#pcDiv").css("display","block");
		$("#phoneDiv").css("display","none");
	}
	
	$("#phoneDiv").on("click",function () {
		$(".UlDiv").toggle();
	});
	
	/**
	 * 底部菜单按钮
     */
    $(".tbmClick").on("click",function () {
		var adata = $(this).attr("adata");
		var atype = $(this).attr("atype");
        var url;
		if(atype == 'camera'){
            url = bpath+"/route/camera?tid=" + adata;
		}
        var index = top.layer.open({
            type: 2,
            resize: false,
            anim: Math.ceil(Math.random() * 6),
            content: [url, 'no'] //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
        top.layer.full(index);
    })
});