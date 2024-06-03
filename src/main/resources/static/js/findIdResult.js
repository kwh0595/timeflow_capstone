document.addEventListener('DOMContentLoaded', function() {
    // AJAX 요청으로 서버에서 아이디를 받아옵니다.
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/findIdResult', true); // '/user/getFoundId'는 서버에서 아이디를 반환하는 엔드포인트로 가정합니다.
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var foundId = xhr.responseText.trim();
            document.getElementById('foundId').textContent = foundId;
        }
    };
    xhr.send();

    document.getElementById('backToLoginButton').addEventListener('click', function() {
        window.location.href = 'login.html'; // 로그인 페이지로의 경로를 설정합니다.
    });
});
