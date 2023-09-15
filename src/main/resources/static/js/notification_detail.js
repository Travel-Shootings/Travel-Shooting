var userIdElement = document.getElementById('user-id');
var userId = userIdElement.getAttribute('data');

$(document).ready(function() {
    loadUncheckNotificationData()
});
function loadUncheckNotificationData() {
    fetch('/api/notifications/' + userId + '/uncheck', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            $('#uncheck-notifications-list').empty()
            console.log(data);

            for (let i = data.length - 1; i >= 0; i--) {
                let notificationId = data[i].id;
                let message = data[i].message;
                let postId;
                if (data[i].postId != null) {
                    postId = data[i].postId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/post/${postId}">${message}</a></div>`

                    $('#uncheck-notifications-list').append(temp_html);
                } else {
                    postId = data[i].reviewPostId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/review-post/${postId}">${message}</a></div>`

                    $('#uncheck-notifications-list').append(temp_html);
                }


            }

        })
}

$(document).ready(function() {
    loadCheckNotificationData()
});
function loadCheckNotificationData() {
    fetch('/api/notifications/' + userId + '/check', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            $('#check-notifications-list').empty()
            console.log(data);

            for (let i = data.length - 1; i >= 0; i--) {
                let notificationId = data[i].id;
                let message = data[i].message;
                let postId;
                if (data[i].postId != null) {
                    postId = data[i].postId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/post/${postId}">${message}</a></div>`

                    $('#check-notifications-list').append(temp_html);
                } else {
                    postId = data[i].reviewPostId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/review-post/${postId}">${message}</a></div>`

                    $('#check-notifications-list').append(temp_html);
                }


            }

        })
}

function confirmNotification(notificationId) {
    fetch('/api/notifications/' + notificationId + '/read', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
    })
        .then(response => response.json())
        .then(res => {
            console.log(res);
            console.log(res.status);
        })
}

function deleteNotification() {
    const confirmResult = confirm('정말로 삭제하시겠습니까?');
    if (confirmResult) {
        $.ajax({
            type: "DELETE",
            url:'/api/notifications/'+ userId + '/check/delete',
            dataType:"json"
        }).done(function(res){
            console.log(res)
            console.log(res.status)
            if (res.statusCode === 200) {
                alert(res.message);
                window.location.reload();
            }
        }).fail(function (request, status, error){
            console.log(status)
            console.log(error)
            alert("에러가 발생했습니다.");
        });
    }
}