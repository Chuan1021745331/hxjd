var webSocket = null;
// var webSocketUrl = "ws://localhost:10105/videoPlayCheck";
var webSocketUrl = "ws://localhost:18080/HeartBeat.ws";
var camId = "001";
$().ready(function(){
    initWebSocket(webSocketUrl);
    // initSend();

})
;

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