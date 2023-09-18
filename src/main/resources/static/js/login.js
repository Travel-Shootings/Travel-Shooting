document.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        // 엔터 키를 눌렀을 때 실행할 동작
        event.preventDefault(); // 폼 제출 방지
        document.getElementById("btn-login").click(); // 원하는 버튼 클릭
    }
});


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

// login
let idx = {
    init: function () {
        $("#btn-login").on("click", () => {
            this.login();
        });
    },

    login: function () {
        let data = {
            email: $('#inputUsername').val(),
            password: $('#inputPassword').val()
        };

        $.ajax({
            // 로그인 수행 요청
            type: "POST",
            url: "/api/user/login",
            data: JSON.stringify(data), // http body data
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        })
            .done(function (res) {
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.location.href = '/view/home';
                }
            })
            .fail(function (jqXHR) {
                alert(JSON.stringify(jqXHR.responseJSON.message))
            });
    }
}
idx.init();
