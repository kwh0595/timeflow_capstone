document.querySelectorAll('.field button').forEach(button => {
    button.addEventListener('click', function() {
        let inputField = this.previousElementSibling;
        inputField.disabled = !inputField.disabled;
        if (inputField.disabled) {
            // 로직을 추가하여 서버에 변경사항 저장
            console.log(inputField.value); // 예시 로그
        } else {
            inputField.focus();
        }
    });
});

function enableEdit(id) {
    var input = document.getElementById(id);
    if(input.disabled) {
        input.disabled = false;
        input.focus(); // 입력창에 자동으로 커서를 위치
    } else {
        input.disabled = true;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    // 'Edit' 버튼에 대한 이벤트 리스너 설정
    document.querySelectorAll('.edit-button').forEach(button => {
        button.addEventListener('click', function() {
            let inputField = this.closest('.field').querySelector('input');
            // 입력 필드 활성화
            inputField.disabled = false;
            // 'Save Changes' 버튼 활성화
            document.querySelector('.save-changes-button').disabled = false;
        });
    });

    // 'Save Changes' 버튼에 대한 이벤트 리스너 설정
    document.querySelector('.save-changes-button').addEventListener('click', () => {
        // 모든 입력 필드 비활성화
        document.querySelectorAll('.field input').forEach(input => {
            input.disabled = true;
        });
    });
});

//------------------jQuery Ajax 요청 부분---------------------
$(document).ready(function() {
    // 페이지가 로드되면 사용자 정보를 가져옵니다.
    getUserInfo();

    // 'Save Changes' 버튼 클릭 이벤트 리스너 설정
    $('.save-changes-button').click(function() {
        saveChanges();
    });

    // 'Delete Account' 버튼 클릭 이벤트 리스너 설정
    $('.delete-account-button').click(function() {
        deleteAccount();
    });
});

// 사용자 정보를 가져오는 함수
function getUserInfo() {
    $.ajax({
        url: '/api/user', // 백엔드 엔드포인트
        method: 'GET',
        success: function(response) {
            $('#name').val(response.name);
            $('#email').val(response.email);
            $('#team').val(response.team);
        },
        error: function(error) {
            console.error('Failed to fetch user info:', error);
        }
    });
}

// 변경 사항을 저장하는 함수
function saveChanges() {
    let updatedUser = {
        name: $('#name').val(),
        email: $('#email').val(),
        team: $('#team').val()
    };

    $.ajax({
        url: '/api/user', // 백엔드 엔드포인트
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedUser),
        success: function(response) {
            alert('Changes saved successfully');
            // 모든 입력 필드를 비활성화합니다.
            $('.field input').prop('disabled', true);
        },
        error: function(error) {
            console.error('Failed to save changes:', error);
            alert('Failed to save changes');
        }
    });
}

// 계정을 삭제하는 함수
function deleteAccount() {
    if (confirm('Are you sure you want to delete your account?')) {
        $.ajax({
            url: '/api/user', // 백엔드 엔드포인트
            method: 'DELETE',
            success: function(response) {
                alert('Account deleted successfully');
                window.location.href = 'login.html';
            },
            error: function(error) {
                console.error('Failed to delete account:', error);
                alert('Failed to delete account');
            }
        });
    }
}

// 필드 편집 활성화 함수
function enableEdit(fieldId) {
    let inputField = document.getElementById(fieldId);
    inputField.disabled = false;
    inputField.focus();
    document.querySelector('.save-changes-button').style.display = 'block';
}

// Edit 버튼 클릭 이벤트 리스너
document.querySelectorAll('.field button').forEach(button => {
    button.addEventListener('click', function() {
        let inputField = this.previousElementSibling;
        inputField.disabled = !inputField.disabled;
        if (!inputField.disabled) {
            inputField.focus();
            document.querySelector('.save-changes-button').style.display = 'block';
        }
    });
});









