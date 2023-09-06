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
            contentType: "application/json; charset=urf-8",
            dataType: "json"
        })
            .done(function (res) {
                console.log(res)
                console.log(res.statusCode)
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.location.href = '/view/home';
                }
            })
            .fail(function (request, status, error, response) {
                console.log(response)
                console.log(error)
                alert(response.responseJSON.message);
            });
    }
}
idx.init();
