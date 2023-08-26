let authCookie = Cookies.get("Authorization");

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/travel-shooting-websocket',
    connectHeaders: {
        "Authorization": authCookie
    }
});

let chatRoomId = null;

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/sub/chat/room/' + chatRoomId, (message) => {
        showMessage(JSON.parse(message.body).body);
    });
    $.ajax({
        url: "/api/chat-room/" + chatRoomId,
        type: "GET",
        header: {"Authorization": authCookie}
    })
        .done(function (response) {
            response.forEach(function (message) {
                showMessage(message);
            })
        })
        .fail(function (response, status, xhr) {
            console.log(response, status, xhr);
        })
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
    alert("웹소켓 연결 오류");
    window.location.reload();
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
    alert("STOMP 오류");
    window.location.reload();
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#chat-messages").html("");
}

function connect() {
    chatRoomId = $("#room-number").val();
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/pub/message/" + chatRoomId,
        body: JSON.stringify({
            'content': $("#message").val()
        })
    });
}

function showMessage(message) {
    $("#chat-messages").append("<tr><td>"
        + "<div class='message-content'>"
        + message.senderName + ": " + message.content
        + "</div>"
        + "<div class='message-time'>"
        + message.simpleTime
        + "</div>"
        + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});