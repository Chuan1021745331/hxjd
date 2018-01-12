var webSocket = null;
// var webSocketUrl = "ws://localhost:10105/videoPlayCheck";
var webSocketUrl = "ws://localhost:12251/HeartBeat.ws";
var camId = "001";

(function heartBeatInit()
{
    // initWebSocket($("#webSocketUrl").val());
    initWebSocket(webSocketUrl);
})();

// function choosenCam(id)
// {
//     camId = id;
// }

function initWebSocket(url) {
    if ('WebSocket' in window) {
        webSocket = new WebSocket(url);
    }
    else {
        alert('对不起，您的浏览器不支持websocket，请更换浏览器')
    }

    //Error callback
    webSocket.onerror = function () {
        //alert('error')
    };

    //socket opened callback
    webSocket.onopen = function (event) {

        // webSocket.send("#init#" + camId);
        sendActivedCam2Server("#init#");

        setInterval(function ()
        {
            sendActivedCam2Server("#heartbeat#");
            // console.log(activedCamMapping);
            // webSocket.send("???")
        }, 3000)
    };

    webSocket.onmessage = function (event) {
        dataHandler(event.data);
    };

    webSocket.onclose = function () {
        // alert("onclose")
    };

    //when browser window closed, close the socket, to prevent server exception
    window.onbeforeunload = function () {
        webSocket.close();
        // alert("onbeforeunload")
    }
}

function sendActivedCam2Server(type)
{
    var activedCamMap = getActivedCamMapping();
    for (var i = 0; i < 6; i++) {
        // console.log("key:" + (i + 1) +" value: " + activedCamMap.get((i + 1) + ""))
        if(activedCamMap.get((i + 1) + "") !== "")
        {
            console.log("key:" + (i + 1) +" value: " + activedCamMap.get((i + 1) + ""));
            console.log(":::" + type + activedCamMap.get((i + 1) + ""))
            webSocket.send(type + activedCamMap.get((i + 1) + ""))
        }
    }
}

function dataHandler(data)
{
    console.log("收到服务器反馈：" + data);
}