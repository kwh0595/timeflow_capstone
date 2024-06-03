document.addEventListener('DOMContentLoaded', function() {
    // AJAX 요청으로 서버에서 아이디를 받아옵니다.
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/findIdResult', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var foundId = xhr.responseText.trim();
            document.getElementById('foundId').textContent = foundId;
        }
    };
    xhr.send();

    document.getElementById('backToLoginButton').addEventListener('click', function() {
        window.location.href = 'login.html';
    });
});
