// Sign out 함수
function signOut() {
    alert("로그아웃 합니다...");

    fetch('/api/logout', {
        method: 'POST', // 로그아웃 요청을 서버로 보냄
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                setTimeout(function() {
                    window.location.href = '/'; // 로그아웃 후 로그인 페이지로 이동
                }, 2000);
            } else {
                alert("로그아웃에 실패했습니다.");
            }
        })
        .catch(error => console.error('Error:', error));
}
// Edit Schedule 페이지로 리다이렉션
function redirectToMainPage() {
    window.location.href = 'main.html';
}

// 필드 편집 활성화 함수
function enableEdit(fieldId) {
    let inputField = document.getElementById(fieldId);
    inputField.disabled = false;
    inputField.focus();
    document.querySelector('.save-changes-button').style.display = 'block';
}

// 변경 사항 저장 및 입력란 비활성화 함수
function saveChanges() {
    let updatedUser = {
        userName: document.getElementById('name').value,
       // password: document.getElementById('password').value,
    };

    // 이름을 즉시 업데이트
    document.getElementById('displayName').innerText = updatedUser.userName; // 큰 글자로 표시된 이름 업데이트

    fetch('/api/user', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedUser)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            alert('Changes saved successfully'); // 성공 메시지 표시
            // 모든 입력 필드를 비활성화
            document.querySelectorAll('.field input').forEach(input => {
                input.disabled = true; // 입력 필드 비활성화
            });
            // 저장 버튼 숨기기
            document.querySelector('.save-changes-button').style.display = 'none';
        })
        .catch(error => {
            console.error('Failed to save changes:', error); // 오류 메시지 콘솔에 출력
            alert('Failed to save changes'); // 실패 메시지 표시
        });
}

// 사용자 정보를 가져오는 함수
function getUserInfo() {
    fetch('/api/user')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('name').value = data.userName; // 이름 값 설정
            document.getElementById('email').value = data.userMail; // 이메일 값 설정
            document.getElementById('team').value = data.team; // 팀 값 설정
            document.getElementById('displayName').innerText = data.userName; // 큰 글자로 표시된 이름 설정
        })
        .catch(error => {
            console.error('Failed to fetch user info:', error); // 오류 메시지 콘솔에 출력
        });
}

// DOMContentLoaded 이벤트가 발생하면 사용자 정보를 가져옵니다
document.addEventListener('DOMContentLoaded', () => {
    getUserInfo(); // 사용자 정보 가져오기
});

// 필드 편집 활성화 함수
document.querySelectorAll('.field button').forEach(button => {
    button.addEventListener('click', function() {
        let inputField = this.previousElementSibling; // 이전 형제 요소인 입력 필드 가져오기
        inputField.disabled = false; // 입력 필드 활성화
        inputField.focus(); // 입력 필드에 포커스 맞추기
        document.querySelector('.save-changes-button').style.display = 'block'; // 저장 버튼 표시
    });
});

// Save Changes 버튼 클릭 이벤트 리스너
document.querySelector('.save-changes-button').addEventListener('click', function() {
    saveChanges(); // 변경 사항 저장 함수 호출
});
