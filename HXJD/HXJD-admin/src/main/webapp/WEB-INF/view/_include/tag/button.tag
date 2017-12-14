active = {
	<#list buttons?sort_by("sort") as button> 
		${button.code}:function(){ 
			<#if button.isModal> 
				<#if button.isRight> 
					if(null==tempData){
						top.parent.MSG(2, "请选择一条数据"); 
						return false; 
					}
					common.tableOpenLayer('${button.title!}','${button.icon!}',bpath+'/${button.url}?id='+tempData.id);
				<#else> 
					common.tableOpenLayer('${button.title!}','${button.icon!}',bpath+'/${button.url}');
				</#if> 
				<#else> 
					<#if button.isRight> 
					if(null==tempData){
						top.parent.MSG(2, "请选择一条数据"); 
						return false; 
					}
					common.larryCmsConfirm('${button.title!}',bpath+'/${button.url}?id='+tempData.id);
				<#else> 
					common.larryCmsConfirm('${button.title!}',bpath+'/${button.url}');
				</#if> 
			</#if> 
		}<#if button_has_next>,</#if> 
	</#list> 
}; 
var str='';
<#list buttons?sort_by("sort") as button> 
	str+="<button class='layui-btn layui-btn-sm' <#if button.css??>style='${button.css }'</#if> data-type='${button.code}'><i class='icon iconfont ${button.icon}'></i><cite>${button.name}</button>"; 
</#list>
$("#btn_group").append(str); 
$('#btn_group .layui-btn').on('click',function(){ 
	var type = $(this).data('type'); active[type] ? active[type].call(this) : ''; 
	console.log(type);
});
$('.layui-inline .layui-btn').on('click', function(){ 
	var type = $(this).data('type'); activeReload[type] ? activeReload[type].call(this) : ''; 
});
