document.addEventListener('DOMContentLoaded', function() {
    // AJAX 요청으로 서버에서 아이디를 받아옵니다.
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/user/findIdResult/result', true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            console.log('XHR readyState:', xhr.readyState); // 상태 코드 확인
            console.log('XHR status:', xhr.status); // HTTP 상태 코드 확인

            if (xhr.status === 200) {
                // document.getElementById('foundId').textContent = userEmail;
            } else {
                console.error('Error fetching ID:', xhr.status, xhr.statusText);
            }
        }
    };
    xhr.send();

    document.getElementById('backToLoginButton').addEventListener('click', function() {
        window.location.href = '/';
    });
});