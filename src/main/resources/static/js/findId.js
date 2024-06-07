document.getElementById('findIdForm').addEventListener('submit', function(event) {
  event.preventDefault();
  validateForm();
});

document.getElementById('findIdButton').addEventListener('click', function(event) {
  findId();
});

// 출생 연도 옵션 생성
var birthYearEl = document.getElementById('birthday_year');
for (var i = 1950; i <= 2024; i++) {
  var option = document.createElement('option');
  option.value = i;
  option.textContent = i;
  birthYearEl.appendChild(option);
}

// 월 옵션 생성
var birthMonthEl = document.getElementById('birthday_month');
for (var i = 1; i <= 12; i++) {
  var option = document.createElement('option');
  option.value = i;
  option.textContent = i;
  birthMonthEl.appendChild(option);
}

// 일 옵션 생성 (달에 따라 동적으로 변경)
function updateDays() {
  var year = document.getElementById('birthday_year').value;
  var month = document.getElementById('birthday_month').value;
  var dayEl = document.getElementById('birthday_day');
  dayEl.innerHTML = '<option disabled selected>일</option>';
  var daysInMonth = new Date(year, month, 0).getDate();
  for (var i = 1; i <= daysInMonth; i++) {
    var option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    dayEl.appendChild(option);
  }
}

document.getElementById('birthday_year').addEventListener('change', updateDays);
document.getElementById('birthday_month').addEventListener('change', updateDays);

function validateForm() {
  var nameInput = document.getElementById('userName').value;
  var yearInput = document.getElementById('birthday_year').value;
  var monthInput = document.getElementById('birthday_month').value;
  var dayInput = document.getElementById('birthday_day').value;
  var popupMessage = document.getElementById('popup_message');

  if (!nameInput) {
    document.getElementById('name_error_message').innerHTML = "이름을 입력해주세요.";
    document.getElementById('birthday_error_message').innerHTML = "";
    popupMessage.style.display = 'none';
    return;
  }

  if (!yearInput || !monthInput || !dayInput) {
    document.getElementById('birthday_error_message').innerHTML = "생년월일을 입력해주세요.";
    document.getElementById('name_error_message').innerHTML = "";
    popupMessage.style.display = 'none';
    return;
  }

  // 입력된 연, 월, 일을 각각 변수에 저장합니다.
  var year = encodeURIComponent(yearInput);
  var month = encodeURIComponent(monthInput);
  var day = encodeURIComponent(dayInput);

  // 월과 일이 한 자리 숫자일 경우, 앞에 '0'을 추가합니다.
  if (month.length === 1) {
    month = '0' + month;
  }
  if (day.length === 1) {
    day = '0' + day;
  }

  // AJAX 요청 보내기
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/user/findIdResult', true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      var button = document.createElement('button'); // '확인' 버튼 생성
      button.textContent = '확인'; // 버튼 텍스트 설정
      button.onclick = function() {
        // form 태그를 동적으로 생성하여 페이지 이동
        var form = document.createElement('form');
        form.method = 'GET';
        form.action = 'login';
        document.body.appendChild(form);
        form.submit();
      };

      if (xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);
        if (response.userEmail) {
          popupMessage.textContent = '아이디 찾기에 성공했습니다: ' + response.userEmail + ' ';
        } else {
          popupMessage.textContent = '아이디를 찾을 수 없습니다. ';
        }
      } else if (xhr.status === 404) {
        var errorResponse = JSON.parse(xhr.responseText);
        console.log('xhr.status', xhr.status, 'xhr.responseText:', xhr.responseText);
        popupMessage.textContent = errorResponse.message + ' ';
      } else if (xhr.status === 500) {
        var errorResponse = JSON.parse(xhr.responseText);
        console.log('xhr.status', xhr.status, 'xhr.responseText:', xhr.responseText);
        popupMessage.textContent = errorResponse.message + ' ';
      } else {
        console.log('xhr.status', xhr.status, 'xhr.responseText:', xhr.responseText);
        popupMessage.textContent = '알 수 없는 오류가 발생했습니다. 다시 시도해주세요. ';
      }

      // '확인' 버튼을 팝업 메시지에 추가
      popupMessage.appendChild(button);
      popupMessage.style.display = 'block';
    }
  };

  xhr.send('userName=' + encodeURIComponent(nameInput) + '&birthday_year=' + year + '-' + month + '-' + day);

  // 에러 메시지 초기화
  document.getElementById('name_error_message').innerHTML = "";
  document.getElementById('birthday_error_message').innerHTML = "";
}

function findId() {
  validateForm();
}