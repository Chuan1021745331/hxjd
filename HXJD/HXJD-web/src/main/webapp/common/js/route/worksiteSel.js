/**
 * Created by hxjd on 2018/1/26.
 */
layui.use(['form','layer','element'], function(){
    var element = layui.element;
    var layer = layui.layer;
    initSizeHeight($("#root_div"));
    //监听折叠
    element.on('test', function(data){
        layer.msg('展开状态：'+ data.show);
    });
    /*工点按钮事件*/
    $(".tbutton").on("click",function () {
        var tdata = $(this).attr("tdata");
        var iframe = window.parent.document.getElementsByClassName("tbmiframe")[0];
        var url = bpath+"/route/main2?tid=" + tdata;
        var index = top.layer.open({
            type: 2,
            resize: false,
            anim: Math.ceil(Math.random() * 6),
            content: [url, 'no'] //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
        top.layer.full(index);
    });
    /**
     * 初始化页面大小
     * @param $obj
     * @param isIndex
     */
    function initSizeHeight($obj, isIndex)
    {
        // var width = document.documentElement.clientWidth;
        var height = $(window).height();
        if(isIndex)
        {
            $obj.css({ height: height - 60});//112是控制栏
        }
        else
        {
            $obj.css({height:height});
        }
    }
});