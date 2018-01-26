/**
 * Created by hxjd on 2018/1/26.
 */

layui.use(['form','layer'], function() {
    var from = layui.from;
    var common = layui.common;
    var layer = layui.layer;
    /**
     * 侧边菜单按钮
     */
    $(".aclick").on("click", function () {
        var atype = $(this).attr("atype");
        var adata = $(this).attr("adata");
        var url;
        if(atype == 1){
            url = bpath+"/route/index?cid=" + adata;
        }else {
            url = bpath+"/route/workSiteSel?wid=" + adata;
        }


        $(".tbmiframe").attr("src", url);
    })
})
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
