var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/my-websockets');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/queue/greetings', function (greeting) {
            showMessage(JSON.parse(greeting.body).name);
        });
        stompClient.subscribe('/topic/news', function (message) {
            const name = JSON.parse(message.body).name;
            const content = JSON.parse(message.body).content;
            showMessage(`${name}: ${content}`);
        });
        sendName();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}
var username;
function sendName() {
    username = $("#name").val();
    const name = JSON.stringify({'name': username});
    stompClient.send("/app/greetings", {}, name);

}

function sendMessage() {
    const message = {
        name: username,
        content: $("#message").val()
    }
    stompClient.send("/app/news", {}, JSON.stringify(message));
    $("#message").val("");
}

function showMessage(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#sendMessage" ).click(function() { sendMessage(); });
});

