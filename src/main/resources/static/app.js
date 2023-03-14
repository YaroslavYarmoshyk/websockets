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
            showGreeting(JSON.parse(greeting.body).name);
        });
        stompClient.subscribe('/topic/news', function (message) {
            const name = JSON.parse(message.body).name;
            const content = JSON.parse(message.body).content;
            showGreeting(`${name}:${content}`);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    const name = JSON.stringify({'name': $("#name").val()});
    stompClient.send("/app/greetings", {}, name);
}

function sendMessage() {
    const message = JSON.stringify({'message': $("#message").val()});
    stompClient.send("/topic/news", {}, message);
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#sendMessage" ).click(function() { sendMessage(); });
});

