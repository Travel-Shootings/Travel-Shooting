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

// signup
let idx = {
    init: function (){
        $("#btn-signup").on("click", () => {
            this.signup();
        });
    },

    signup: function () {
        let data = {
            email : $('#inputEmail').val(),
            password : $('#inputPassword').val(),
            username : $('#inputName').val(),
            nickname : $('#inputNickname').val(),
            region : $('#select-box').val()
        };

        $.ajax({
            // 로그인 수행 요청
            type: "POST",
            url:"/api/user/signup",
            data: JSON.stringify(data), // http body data
            contentType:"application/json; charset=urf-8",
            dataType:"json"
        }).done(function(res){
            console.log(res)
            console.log(res.statusCode)
            if (res.statusCode === 201) {
                alert(res.message)
                window.location.href = "/view/user/login"
            } else if (res.statusCode === 400) {
                alert(res.message)
            }
        }).fail(function (request, status, error){
            console.log(status)
            console.log(error)
            alert("회원 가입에 실패했습니다.");
        });
    }
}
idx.init();
