/**
 * Created by hxjd on 2018/1/26.
 */
layui.use(['form','layer','element'], function(){
    var element = layui.element;
    initSizeHeight($("#circuit_root_div"));
    //监听折叠
    element.on('test', function(data){
        layer.msg('展开状态：'+ data.show);
    });

    /*工点按钮事件*/
    $(".wbutton").on("click",function () {
        var adata = $(this).attr("wdata");
         var iframe = window.parent.document.getElementsByClassName("tbmiframe")[0];
         var url = bpath+"/route/workSiteSel?wid=" + adata;
         $(iframe).attr("src", url);
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