document.getElementById('findpasswordForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막음

    var currentPassword = document.getElementById('userpasswd').value; // 현재 비밀번호 입력 값 가져오기

    // 서버에 현재 비밀번호 확인 요청
    fetch('/api/check-password', {
        method: 'POST', // POST 메서드 사용
        headers: {
            'Content-Type': 'application/json' // JSON 형식의 요청 본문
        },
        body: JSON.stringify({ password: currentPassword }) // 요청 본문에 현재 비밀번호 포함
    })
        .then(response => response.json()) // 응답을 JSON으로 파싱
        .then(data => {
            if (data.valid) { // 비밀번호가 유효한 경우
                // 팝업 메시지 표시
                var popupMessage = document.getElementById('popup_message');
                popupMessage.textContent = "비밀번호가 확인되었습니다.";
                popupMessage.style.display = 'block';

                // 팝업 오버레이 표시
                var popupOverlay = document.getElementById('popup_overlay');
                popupOverlay.style.display = 'block';

                // 2초 후 팝업 메시지와 오버레이 숨기기
                setTimeout(function() {
                    popupMessage.classList.add('fade-out');
                    popupOverlay.style.display = 'none';

                    setTimeout(function() {
                        popupMessage.style.display = 'none';
                        popupMessage.classList.remove('fade-out');

                        // 현재 비밀번호 섹션 숨기기
                        var currentPasswordSection = document.getElementById('currentPasswordSection');
                        currentPasswordSection.style.display = 'none';

                        // 새 비밀번호 섹션 표시
                        var newPasswordSection = document.getElementById('newPasswordSection');
                        newPasswordSection.style.display = 'block';
                    }, 500);
                }, 2000);
            } else { // 비밀번호가 유효하지 않은 경우
                alert("현재 비밀번호가 일치하지 않습니다.");
            }
        })
        .catch(error => console.error('Error:', error)); // 에러 처리
});

document.getElementById('newpasswordForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막음

    var newPassword = document.getElementById('newpasswd').value; // 새 비밀번호 입력 값 가져오기
    var confirmNewPassword = document.getElementById('confirmnewpasswd').value; // 새 비밀번호 확인 입력 값 가져오기
    var errorMessage = document.getElementById('error-message'); // 오류 메시지 요소 가져오기

    if (newPassword !== confirmNewPassword) { // 새 비밀번호와 확인 비밀번호가 일치하지 않는 경우
        errorMessage.textContent = "* 비밀번호가 일치하지 않습니다";
        errorMessage.style.display = 'block'; // 오류 메시지 표시
    } else { // 비밀번호가 일치하는 경우
        errorMessage.style.display = 'none'; // 오류 메시지 숨기기

        // 서버에 새 비밀번호 업데이트 요청
        fetch('/api/update-password', {
            method: 'PUT', // POST 메서드 사용
            headers: {
                'Content-Type': 'application/json' // JSON 형식의 요청 본문
            },
            body: JSON.stringify({ password: newPassword }) // 요청 본문에 새 비밀번호 포함
        })
            .then(response => response.json()) // 응답을 JSON으로 파싱
            .then(data => {
                if (data.success) { // 비밀번호 변경이 성공한 경우
                    alert("비밀번호가 성공적으로 변경되었습니다.");
                    window.location.href = '/'; // login.html로 리디렉션
                } else { // 비밀번호 변경이 실패한 경우
                    alert("비밀번호 변경에 실패했습니다.");
                }
            })
            .catch(error => console.error('Error:', error)); // 에러 처리
    }
});