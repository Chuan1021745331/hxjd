/**
 * Created by hxjd on 2018/1/26.
 */

layui.use(['form','layer','element'], function() {
    var element = layui.element;
    var layer = layui.layer;
    /*监听元素事件*/
    /*隐藏事件*/
    $(".animationHide").on("mouseover", function () {

    })
    /*隐藏动画*/
    $(".animationHide").on("click", function () {

        $("#cutline").css("animation", "myfirst 2s");
        $("#cutline").css("-moz-animation", "myfirst 2s");
        $("#cutline").css("-webkit-animation", "myfirst 2s");
        $("#cutline").css("-o-animation", "myfirst 2s");
        $("#cutline").removeClass("button-group");
        $("#cutline").addClass("button-group-190-left");

        /*动画二*/

        $("#bordercutline").css("animation", "myBorder 1s 2.3s")
        $("#bordercutline").css("-moz-animation", "myBorder 1s 2.3s")
        $("#bordercutline").css("-webkit-animation", "myBorder 1s 2.3s")
        $("#bordercutline").css("-o-animation", "myBorder 1s 2.3s")
        setTimeout(function () {
            $("#cutline").removeClass("button-group");
            $("#cutline").addClass("button-group-190-left");
            $("#bordercutline").removeClass("button-group-70-left");
            $("#bordercutline").addClass("button-group-45-left");
        }, 2500);

    })
    /*显示动画*/
    $("#bordercutline").on("click", function () {
        $("#bordercutline").css("animation", "myBorder2 1s");
        $("#bordercutline").css("-moz-animation", "myBorder2 1s");
        $("#bordercutline").css("-webkit-animation", "myBorder2 1s ");
        $("#bordercutline").css("-o-animation", "myBorder2 1s");
        $("#bordercutline").removeClass("button-group-45-left");
        $("#bordercutline").addClass("button-group-70-left");


        $("#cutline").css("animation", "myfirst2 2s 1s");
        $("#cutline").css("-moz-animation", "myfirst2 2s 1s");
        $("#cutline").css("-webkit-animation", "myfirst2 2s 1s");
        $("#cutline").css("-o-animation", "myfirst2 2s 1s");

        setTimeout(function () {
            $("#cutline").removeClass("button-group-190-left");
            $("#cutline").addClass("button-group");
        }, 1100);

    })

    var jsonObj = "NULL";

    function initAjax() {
        $.ajax({
            url: bpath + "/mainData",
            type: 'post',
            dataType: 'json',
            success: function (json) {
                console.log(json);
                jsonObj = json;
                initDraw();
            }
        })
    }


    /*初始化所有建筑*/
    function initDraw() {
        for (var a = 0; a < jsonObj.length; a++) {
            var items = analysisPoints(jsonObj[a].points);
            var color = jsonObj[a].color;
            var workSites = jsonObj[a].worksites;
            draw(items, "init", color, 3);
            for (var b = 0; b < workSites.length; b++) {
                var witems = analysisPoints(workSites[b].coord);
                var wcolor = workSites[b].color;
                var tbms = workSites[b].tbms;
                var wname = workSites[b].name;
                draw(witems, "init", wcolor, 8);
                var z = parseInt(witems.length / 2);
                var contentCoord = witems[z];
                drawText(wname, contentCoord);
                for (var c = 0; c < tbms.length; c++) {
                    var coord = tbms[c].coord;
                    var tname = tbms[c].name;
                    var tid = tbms[c].id;
                    var xy = coord.split(",");
                    creatMaker(xy[0], xy[1], tname,tid);
                }
            }
        }
    }


    /*创建高德地图*/
    var map = new AMap.Map('container', {
        layers: [new AMap.TileLayer.Satellite({zIndex: 10})],
        zoom: 13,
        center: [106.545474, 29.601621]
    });
    initAjax();
    var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
    /*全局变量*/
    var marker = "NULL";

    /*map.on('click',function (e) {
     if(marker != "NULL"){
     clearTag();
     }
     var x = e.lnglat.getLng();
     var y = e.lnglat.getLat();
     creatMaker(x,y);
     });*/

    /*解析坐标为数组*/
    function analysisPoints(points) {
        var lineStr = new Array();
        lineStr = points.split("#");

        var items = new Array();
        for (var j = 0; j < lineStr.length; j++) {

            var str = lineStr[j].split(",");
            var x = str[0];
            var y = str[1];
            items[j] = [x, y];
        }
        return items;
    }

    /*将盾构机坐标保存到表单*/
    function setAddPoints(x, y, oth) {
        var data;
        if (oth == "NULL") {
            data = x + "," + y;
            $("#addTbmCoord").val(data);
        }
    }

    /*初始化线路*/
    function initLine() {
        var pointsPart = "NULL";
        pointsPart = $("#hiddenPoints").val();
        if (pointsPart != "NULL") {
            var items = analysisPoints(pointsPart);
            var pcolor = $("#hiddenCircuitColor").val();
            draw(items, "init", pcolor, 3);
        }

    }

    /*初始化工点段*/
    function initWorkSite() {
        var coordPart = "NULL";
        coordPart = $("#hiddenCoord").val();
        if (coordPart != "NULL") {
            var items = analysisPoints(coordPart);
            var wcolor = $("#hiddenWorksiteColor").val();
            draw(items, "init", wcolor, 8);
        }

    }

    /*监听重置按钮*/
    $(".restPoints").on('click', function (e) {
        clearTag()
    });

    /*清除标记点*/
    function clearTag() {
        map.remove(marker);
        marker = "NULL";
//            $("#addTbmCoord").val('');
    }

    /*绘制*/
    function draw(points, order, color, lineWidth) {
        if (lineWidth == "NULL") {
            lineWidth = 3;
        }
        var polyline = new AMap.Polyline({
            path: points,          //设置线覆盖物路径
            strokeColor: color, //线颜色
            strokeOpacity: 1,       //线透明度
            strokeWeight: lineWidth,        //线宽
            strokeStyle: "solid",   //线样式
            strokeDasharray: [10, 5] //补充线样式
        });
        polyline.setMap(map);
    }

    /*绘制文字*/
    function drawText(text, coord) {
        var text = new AMap.Text({
            text: text,
            textAlign: 'center', // 'left' 'right', 'center',
            verticalAlign: 'middle', //middle 、bottom
            // draggable:true,
            cursor: 'pointer',
            angle: 0,
            style: {
                'background-color': 'rgba(0, 0, 0, 0.36)',
                'border': 'solid 1px red',
                'color': 'white'
            },
            position: coord
        });
        text.setMap(map);
    }

    /*绘制tag*/
    function creatMaker(x, y, tname,tid) {
        marker = new AMap.Marker({
            icon: ctx + "/images/tbm/tbm2.png",
//                draggable:true,//拖拽
            position: [x, y]
        });
        marker.content = tname;
        marker.on('mouseover', infoWindowOpen);
        marker.on('mouseout', infoWindowClose);
        marker.on('click', function(){
            markerClick(tid);
        });
        marker.setMap(map);
        setAddPoints(x, y, "NULL");
    }

    /*信息框*/
    function infoWindowOpen(e) {
        infoWindow.setContent(e.target.content);
        infoWindow.open(map, e.target.getPosition());
    }

    /*关闭*/
    function infoWindowClose(e) {
        map.clearInfoWindow();
    }

    /*点击标记事件*/
    function markerClick(tid) {
        // var tdata = $(this).attr("tdata");
        var iframe = window.parent.document.getElementsByClassName("tbmiframe")[0];
        var url = bpath+"/route/tbm?tid=" + tid;
        var index = top.layer.open({
            type: 2,
            resize: false,
            anim: Math.ceil(Math.random() * 6),
            content: [url] //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
        top.layer.full(index);

    }

//点击地图空白地方，清除所有的弹出窗体
    AMap.event.addListener(map, 'click', function (e) {
        map.clearInfoWindow();
    });
})
