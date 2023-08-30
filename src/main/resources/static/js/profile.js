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

// 프로필 수정 팝업 창
document.getElementById('user-menu-profile').addEventListener('click', function() {
    const popupWidth = 500; // 팝업 창 너비
    const popupHeight = 500; // 팝업 창 높이

    const screenWidth = window.innerWidth; // 브라우저 창 너비
    const screenHeight = window.innerHeight; // 브라우저 창 높이

    const left = (screenWidth - popupWidth) / 2; // 가로 가운데 정렬
    const top = (screenHeight - popupHeight) / 2; // 세로 가운데 정렬

    // editProfile.html /view/user/editProfile
    const popupUrl = '/view/user/editProfile';
    const popupFeatures = `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`;

    window.open(popupUrl, '팝업 창 제목', popupFeatures);
});


// 비밀번호 변경 팝업 창
document.getElementById('user-menu-password').addEventListener('click', function() {
    const popupWidth = 500; // 팝업 창 너비
    const popupHeight = 500; // 팝업 창 높이

    const screenWidth = window.innerWidth; // 브라우저 창 너비
    const screenHeight = window.innerHeight; // 브라우저 창 높이

    const left = (screenWidth - popupWidth) / 2; // 가로 가운데 정렬
    const top = (screenHeight - popupHeight) / 2; // 세로 가운데 정렬

    // editPassword.html /view/user/editPassword
    const popupUrl = '/view/user/editPassword';
    const popupFeatures = `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`;

    window.open(popupUrl, '팝업 창 제목', popupFeatures);
});