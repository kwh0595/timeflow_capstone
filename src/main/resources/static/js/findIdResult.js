document.addEventListener('DOMContentLoaded', function() {
    fetch('/user/findIdResult')
        .then(response => {
            console.log('응답 상태:', response.status); // HTTP 상태 코드 확인
            if (!response.ok) {
                throw new Error('연결 안됨 ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('서버 응답:', data);
            document.getElementById('foundId').textContent = data.userEmail;
        })
        .catch(error => {
            console.error('Error:', error); // 에러 처리
        });

    document.getElementById('backToLoginButton').addEventListener('click', function() {
        window.location.href = '/';
    });
});
