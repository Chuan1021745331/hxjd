var webSocket = null;
// var webSocketUrl = "ws://localhost:10105/videoPlayCheck";
var webSocketUrl = "ws://192.168.0.192:28080/HeartBeat.ws";
var camId = "001";

$().ready(function(){
    // initVue();
    initWebSocket(webSocketUrl);
    // initSend();

})


// function choosenCam(id)
// {
//     camId = id;
// }

function initWebSocket(url) {
    if ('WebSocket' in window) {
        webSocket = new WebSocket(url);
    } else {
        alert('对不起，您的浏览器不支持websocket，请更换浏览器')
    }

    //Error callback
    webSocket.onerror = function () {
        alert('error')
    };

    //socket opened callback
    webSocket.onopen = function (event) {
        alert("open");
        initSend();
        // webSocket.send("#init#" + camId);
       /* sendActivedCam2Server("#init#");

        setInterval(function ()
        {
            sendActivedCam2Server("#heartbeat#");
            // console.log(activedCamMapping);
            // webSocket.send("???")
        }, 3000)*/
    };

    webSocket.onmessage = function (event) {
        dataHandler(event.data);
    };

    webSocket.onclose = function () {
        alert("onclose")
    };

    //when browser window closed, close the socket, to prevent server exception
    window.onbeforeunload = function () {
        webSocket.close();
        // alert("onbeforeunload")
    }
}

function sendActived()
{
    webSocket.send("#hxjd#2#1")

}
function sendActived1()
{
    webSocket.send("#hxjd#1#2")

}
function initSend() {
    console.log("initSend")
    webSocket.send("#hxjd#0000#2")
}

function dataHandler(data)
{
    console.log("收到服务器反馈：" + data);
}

/*vue webSocket*/
function initVue() {
    var webSocket = new Vue({
        el:'#camera_root_div',
        data:{
            webSocket:null,
            webSocketUrl:"ws://localhost:18080/HeartBeat.ws"
        },
        mounted:function () {
            this.init();
        },
        methods:{
            test:function () {
                alert("button")
            },
            initMachine:function (no) {
                var param = {
                    machineNo:no
                }
                this.$http.post("/admin/camera/getMachine",param,{emulateJSON: true}).then(function (res) {

                });
            },
            /*
            * 1:绿色√，2：红色×，3：黄色？，4：灰色锁，5：红色不开心,6：绿色开心，7：黄色！
            * */
            dialog:function (msg,icon) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg(msg, {icon: icon});
                });
            },
            initWebSocket:function () {
                if ('WebSocket' in window) {
                    this.webSocket = new WebSocket(url);
                } else {
                    this.dialog('对不起，您的浏览器不支持websocket，请更换浏览器',5);
                }
            }
        }
    });
}