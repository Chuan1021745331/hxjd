layui.config({
	base: cpath+'/lib/'
});
layui.use(['jquery','layer','element','common'],function(){
	var $ = layui.$,
	layer = layui.layer,
	common = layui.common,
	device = layui.device(),
	element = layui.element;

	$(document).ready(function() {
		// 浏览器兼容检查
		if (device.ie && device.ie < 9) {
			common.zeroCmsError('本系统最低支持ie8，您当前使用的是古老的 IE' + device.ie + '！ \n 建议使用IE9及以上版本的现代浏览器', common.zeroCore.tit);
		}
        // 移动设备适配
        if(device.android || device.ios){
             if($('#zero_admin_out').length>0){
                $('#zero_admin_out').addClass('zero-mobile');
             }else{
                $('body').addClass('zero-mobile');
             }
        }else{
            if($('#zero_admin_out').length>0){
                if($('#zero_admin_out').hasClass('zero-mobile')){
                    $('#zero_admin_out').removeClass('zero-mobile');
                }
             }else{
                if($('body').hasClass('zero-mobile')){
                    $('body').removeClass('zero-mobile');
                }
             }
        }
	});
});