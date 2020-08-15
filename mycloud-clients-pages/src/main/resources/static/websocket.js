var socket;
var socketUrl;

//开启websocket
function openSocket(socketUrl) {
    if(typeof(WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
    }else{
        var userId = document.getElementById('userId').value;
        console.log("开始连接:"+socketUrl);
        if(socket!=null){
            socket.close();
            socket=null;
        }
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = function() {
            console.log("websocket已打开");
        };
        //获得消息事件并用json转化成对象
        socket.onmessage = function(message) {
        	var messageObject=JSON.parse(message.data);
            handleMessage(messageObject);
            console.log("收到消息:"+message.data);
        };
        //关闭事件
        socket.onclose = function() {
            console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function() {
            console.log("websocket发生了错误");
        }
    }
}

//将对象转化成json字符串并发送
function sendMessage(messageObject) {
	var message=JSON.stringify(messageObject);
    socket.send(message);
    console.log("发出消息:"+message);
}