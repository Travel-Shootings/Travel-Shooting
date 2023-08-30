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
                } else if (res.statusCode === 400) {
                    alert(res.message)
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