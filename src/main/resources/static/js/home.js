var userRoleElement = document.getElementById('user-role');
var userRole = userRoleElement.getAttribute('data');

window.onload = function() {
    var login_btn = document.getElementById("login-btn");
    var signup_btn = document.getElementById("signup-btn");
    var mypage_btn = document.getElementById("mypage-btn");
    var notification_btn = document.getElementById("notification-btn");

    var admin_box = document.getElementById("admin-box");


    if (checkAuthorizationCookie()) {
        login_btn.style.display = "none";
        signup_btn.style.display = "none";

        mypage_btn.style.display = "block";
        notification_btn.style.display = "block";
    }

    console.log('User Role:', userRole);

    if (userRole === "ADMIN") {
        admin_box.style.display = "block";
    }

    function checkAuthorizationCookie() {
        var cookies = document.cookie.split(";");

        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].split('=');

            // "Authorization" 쿠키가 존재하는 경우 true 반환
            if (cookie[0] === "Authorization") {
                return true;
            }
        }
        // "Authorization" 쿠키가 존재하지 않는 경우 false 반환
        return false;
    }
}

// 홈페이지 버튼 상호작용 관련
// Side Menu
const sideNav = document.querySelector('.sidenav');
M.Sidenav.init(sideNav, {});

// Slider
const slider = document.querySelector('.slider');
M.Slider.init(slider, {
    indicators: false,
    height: 500,
    transition: 500,
    interval: 6000
});

// Scrollspy
const ss = document.querySelectorAll('.scrollspy');
M.ScrollSpy.init(ss, {});

// Material Boxed
const mb = document.querySelectorAll('.materialboxed');
M.Materialbox.init(mb, {});

// Auto Complete
const ac = document.querySelector('.autocomplete');
M.Autocomplete.init(ac, {
    data: {
        "Aruba": null,
        "Cancun Mexico": null,
        "Hawaii": null,
        "Florida": null,
        "California": null,
        "Jamaica": null,
        "Europe": null,
        "The Bahamas": null,
    }
});

// logout
let idx = {
    init: function () {
        $("#user-menu-logout").on("click", () => {
            this.logout();
        });
    },

    logout: function () {
        $.ajax({
            type: "DELETE",
            url: "/api/user/logout",
            contentType: "application/json; charset=urf-8",
            dataType: "json"
        })
            .done(function (res) {
                console.log(res)
                console.log(res.statusCode)
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.location.href = "/view/home";
                }
            })
            .fail(function (request, status, error) {
                console.log(status)
                console.log(error)
                alert("로그아웃에 실패했습니다.");
            });
    }
}
idx.init();


// JavaScript 코드
document.getElementById('notification-btn').addEventListener('click', function () {
    var popup = document.getElementById('notification-popup');
    if (popup.style.display === 'block') {
        popup.style.display = 'none';
    } else {
        popup.style.display = 'block';
    }
});

// 다른 곳을 클릭하면 팝업이 닫히도록
document.addEventListener('click', function (event) {
    var popup = document.getElementById('notification-popup');
    var btn = document.getElementById('notification-btn');
    if (event.target !== btn && !btn.contains(event.target) && event.target !== popup && !popup.contains(event.target)) {
        popup.style.display = 'none';
    }
});


var userIdElement = document.getElementById('user-id');
var userId = userIdElement.getAttribute('data');
$(document).ready(function() {
    loadNotificationData()
});
function loadNotificationData() {
    fetch('/api/notifications/'+ userId + '/uncheck', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            $('#notification-box').empty()
            console.log(data);

            let length = data.length - 4;

            for (let i = data.length - 1; i > length; i--) {
                let notificationId = data[i].id;
                let message = data[i].message;
                let postId;
                if (data[i].postId != null) {
                    postId = data[i].postId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/post/${postId}">${message}</a></div>`

                    $('#notification-box').append(temp_html);
                } else {
                    postId = data[i].reviewPostId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/review-post/${postId}">${message}</a></div>`

                    $('#notification-box').append(temp_html);
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
