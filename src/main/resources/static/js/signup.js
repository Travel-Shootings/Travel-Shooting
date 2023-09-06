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

// 비밀번호 확인

const passwordConfirmInput = document.getElementById('inputPasswordConfirm');
const passwordInput = document.getElementById('inputPassword');
const passwordMatchIndicator = document.getElementById('passwordMatchIndicator');

passwordConfirmInput.addEventListener('input', () => {
    // 비밀번호 입력란의 값과 비밀번호 확인 입력란의 값을 비교하여 일치 여부 확인
    const password = passwordInput.value;
    const confirmPassword = passwordConfirmInput.value;

    // 비밀번호 일치 여부에 따라 아이콘 변경
    if (password === confirmPassword) {
        passwordMatchIndicator.textContent = '✓';
        passwordMatchIndicator.style.color = 'green';
    } else {
        passwordMatchIndicator.textContent = '✗';
        passwordMatchIndicator.style.color = 'red';
    }
});

// signup
let idx = {
    init: function (){
        $("#btn-signup").on("click", () => {
            const password = passwordInput.value;
            const confirmPassword = passwordConfirmInput.value;

            if (password === confirmPassword) {
                this.signup();
            } else {
                alert("비밀번호를 다시 확인해주세요.")
            }
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
            }
        }).fail(function (response, status, error){
            console.log(status)
            console.log(error)
            console.log(response)
            alert(response.responseJSON.message);
        });
    }
}
idx.init();
