/**
 * Created by hxjd on 2018/1/26.
 */
layui.use('element', function(){
    var element = layui.element;

    //监听折叠
    element.on('test', function(data){
        layer.msg('展开状态：'+ data.show);
    });
});