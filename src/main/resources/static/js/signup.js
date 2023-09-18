document.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        // 엔터 키를 눌렀을 때 실행할 동작
        event.preventDefault(); // 폼 제출 방지
        document.getElementById("btn-signup").click(); // 원하는 버튼 클릭
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
    init: function () {
        $("#btn-signup").on("click", () => {
            const complete_box = document.getElementById('complete-box');
            const password = passwordInput.value;
            const confirmPassword = passwordConfirmInput.value;

            // 이메일 인증을 진행했는지 확인
            if(complete_box.style.display !== 'block') {
                alert("이메일 인증을 진행해주세요.")
                return;
            }

            // 비밀번호 확인을 했는지 확인
            if (password !== confirmPassword) {
                alert("비밀번호를 다시 확인해주세요.")
                return;
            }

            this.signup();
        });
    },

    signup: function () {
        let data = {
            email: $('#inputEmail').val(),
            password: $('#inputPassword').val(),
            username: $('#inputName').val(),
            nickname: $('#inputNickname').val(),
            region: $('#select-box').val()
        };

        $.ajax({
            type: "POST",
            url: "/api/user/signup",
            data: JSON.stringify(data), // http body data
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            if (res.statusCode === 201) {
                alert(res.message)
                window.location.href = "/view/user/login"
            }
        }).fail(function (response, status, error) {
            alert(response.responseJSON.message);
        });
    }
}
idx.init();

// email 인증
function authEmail() {
    var auth_box = document.getElementById('auth-number-box');
    auth_box.style.display = 'block'

    let data = {
        email: $('#inputEmail').val()
    };

    $.ajax({
        type: "POST",
        url: "/api/user/mail",
        data: JSON.stringify(data), // http body data
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    })
        .done(function (res) {
            if (res.statusCode === 200) {
                alert(res.message)
            }
        })
        .fail(function (response, status, error) {
            alert("메일 전송 실패");
            window.location.reload();
        });

}

// 인증번호 확인
function authConfirm() {
    var auth_box = document.getElementById('auth-number-box');
    var complete_box = document.getElementById('complete-box');
    var auth_btn = document.getElementById('auth-btn');

    let data = {
        authNumber: $('#inputAuthNumber').val()
    };

    $.ajax({
        type: "POST",
        url: "/api/user/mail/confirm",
        data: JSON.stringify(data), // http body data
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    })
        .done(function (res) {
            if (res.statusCode === 200) {
                alert(res.message)

                auth_btn.disabled = true;

                auth_box.style.display = 'none'
                complete_box.style.display = 'block'
            }
        })
        .fail(function (response, status, error) {
            alert("인증에 실패했습니다. 다시 시도해주세요.");
            window.location.reload();
        });
}
