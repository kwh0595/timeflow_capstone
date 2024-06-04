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
  var daysInMonth = new Date(year, month-1, 0).getDate();
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
    if (xhr.readyState === 4 && xhr.status === 200) {
      var inputName = xhr.responseText.trim();
      var foundSuccess = document.querySelector('.found-success');
      var foundFail = document.querySelector('.found-fail');

      if (foundSuccess && foundFail) {
        if(inputName){
          var foundId = foundSuccess.querySelector('.found-id');
          if (foundId) {
            foundId.textContent = inputName;
          }
          foundSuccess.style.display='block';
          foundFail.style.display='none';
        } else {
          foundFail.style.display='block';
          foundSuccess.style.display='none';
        }
      }

      // AJAX 요청이 완료된 후 페이지 이동
      window.location.href = 'findIdResult';
    }
  };
  xhr.send('userName=' + encodeURIComponent(nameInput) + '&birthday_year=' + year + '-' + month + '-' + day);

  // 에러 메시지 초기화
  document.getElementById('name_error_message').innerHTML = "";
  document.getElementById('birthday_error_message').innerHTML = "";
}

function findId(){
  validateForm();
}
