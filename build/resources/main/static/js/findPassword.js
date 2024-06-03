document.getElementById('findPasswordForm').addEventListener('submit', function(event) {
    event.preventDefault();

    // 폼 데이터 가져오기
    var formData = new FormData(this);

    // AJAX 요청 보내기
    fetch('/user/findPassword', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text()) // 응답을 텍스트로 변환
        .then(text => {
            // 서버에서 "findPassword"라는 문자열을 반환하면 성공으로 간주
            if (text === 'findPassword') {
                alert('재설정 비밀번호가 이메일로 전송되었습니다.');
                window.location.href='logIn.html';
            } else {
                alert('오류가 발생했습니다. 다시 시도해 주세요.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('서버와의 통신 중 오류가 발생했습니다.');
        });
});
