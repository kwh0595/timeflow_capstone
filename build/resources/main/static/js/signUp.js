//아이디
let elInputUsername = document.querySelector('#email');
let elSuccessMessage = document.querySelector('.success-message');
let elFailureMessage = document.querySelector('.failure-message');

//비밀번호
let elInputPassword = document.querySelector('#password');
let elStrongPasswordMessage = document.querySelector('.strongPassword-message');

//버튼 비활성화
var elSignUpButton = document.getElementById('signUpButton');

function strongPassword(str) {
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{6,20}$/.test(str);
}

elInputUsername.onkeyup = function () {
  // 이메일 주소가 올바른 형식인지 확인
  var isValidEmail = validateEmail(elInputUsername.value);

  // 이메일 주소가 올바른 경우
  if (isValidEmail) {
    elSuccessMessage.classList.remove('hide');
    elFailureMessage.classList.add('hide');
    elSignUpButton.disabled = false; // 버튼 활성화
  } else {
    elSuccessMessage.classList.add('hide');
    elFailureMessage.classList.remove('hide');
    elSignUpButton.disabled = true; // 버튼 비활성화
  }
}

// 이메일 주소 유효성 검사 함수
function validateEmail(email) {
  // 이메일 주소 유효성을 확인하는 코드 작성 (정규표현식 등)
  // 유효한 이메일 주소인 경우 true 반환, 그렇지 않으면 false 반환
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

elInputPassword.onkeyup = function () {
  // console.log(elInputPassword.value);
  // 값을 입력한 경우
  if (elInputPassword.value.length !== 0) {
    if(strongPassword(elInputPassword.value)) {
      elStrongPasswordMessage.classList.add('hide'); // 실패 메시지가 가려져야 함
      elSignUpButton.disabled=false;
    }
    else {
      elStrongPasswordMessage.classList.remove('hide'); // 실패 메시지가 보여야 함
      elSignUpButton.disabled=true;
    }
  }
      // 값을 입력하지 않은 경우 (지웠을 때)
  // 모든 메시지를 가린다.
  else {
    elStrongPasswordMessage.classList.add('hide');
    elSignUpButton.disabled=true;
  }
};

function change_btn(gender){
  var btns = document.querySelectorAll(".genders")
  btns.forEach(function(btn, i) {
    if(gender.currentTarget == btn){
      btn.classList.add("active");
    }
    else{
      btn.classList.remove("active");
    }
  });
  console.log(gender.currentTarget);
}

const birthYearEl = document.querySelector('#birth-year')
isYearOptionExisted = false;
birthYearEl.addEventListener('focus', function() {
  if(!isYearOptionExisted) {
    isYearOptionExisted = true
    for(var i = 1950; i<=2024; i++){
      const YearOption = document.createElement('option')
      YearOption.setAttribute('value', i)
      YearOption.innerText = i
      this.appendChild(YearOption);
    }
  }
});

const birthMonthEl = document.querySelector('#birth-month')
isMonthOptionExisted = false;
birthMonthEl.addEventListener('focus', function() {
  if(!isMonthOptionExisted) {
    isMonthOptionExisted = true
    for(var i = 1; i<=12; i++){
      const MonthOption = document.createElement('option')
      MonthOption.setAttribute('value', i)
      MonthOption.innerText = i
      this.appendChild(MonthOption);
    }
  }
});

const birthDayEl = document.querySelector('#birth-day')
isDayOptionExisted = false;
birthDayEl.addEventListener('focus', function() {
  if(!isDayOptionExisted) {
    isDayOptionExisted = true
    for(var i = 1; i<=31; i++){
      const DayOption = document.createElement('option')
      DayOption.setAttribute('value', i)
      DayOption.innerText = i
      this.appendChild(DayOption);
    }
  }
});
function updateBirthDate() {
  var year = document.getElementById("birth-year").value;
  var month = document.getElementById("birth-month").value;
  var day = document.getElementById("birth-day").value;
  document.getElementById("completeBirthDate").value = year + '-' + month + '-' + day;
}

document.getElementById("birth-year").addEventListener("change", updateBirthDate);
document.getElementById("birth-month").addEventListener("change", updateBirthDate);
document.getElementById("birth-day").addEventListener("change", updateBirthDate);
document.addEventListener('DOMContentLoaded', function() {
  const yearSelect = document.getElementById('birth-year');
  const monthSelect = document.getElementById('birth-month');
  const daySelect = document.getElementById('birth-day');
  const completeBirthDateInput = document.getElementById('completeBirthDate');

  function updateCompleteBirthDate() {
    const year = yearSelect.value;
    const month = monthSelect.value.padStart(2, '0'); // 한자리 숫자 앞에 0 붙이기
    const day = daySelect.value.padStart(2, '0'); // 한자리 숫자 앞에 0 붙이기
    if (year && month && day) {
      completeBirthDateInput.value = `${year}-${month}-${day}`;
    } else {
      completeBirthDateInput.value = ''; // 모든 필드가 선택되지 않았다면 입력값을 비웁니다.
    }
  }

  // 각 셀렉트 박스에 대해 change 이벤트 리스너 등록
  yearSelect.addEventListener('change', updateCompleteBirthDate);
  monthSelect.addEventListener('change', updateCompleteBirthDate);
  daySelect.addEventListener('change', updateCompleteBirthDate);

  // 초기화를 위해 함수를 한 번 호출
  updateCompleteBirthDate();
});
/**
 window.onload = function(){
  var hw = document.getElementById('hw');
  hw.addEventListener('click', function(){
    alert('Hello world');
  })
}
 */

function Click(){
  var agree_data;
  if(document.getElementById("agree").checked){
    agree_data="동의";
  }
  else if(document.getElementById("disagree").checked){
    agree_data="비동의";
  }
  else{
    alert("동의 버튼을 눌러주세요.");
  }

  if(agree_data.length!=0){
    location.replace("search_list.php?id="+agree_data);
  }
}

function Click2(){
  var agree_data2;
  if(document.getElementById("agree2").checked){
    agree_data2="동의";
  }
  else if(document.getElementById("disagree2").checked){
    agree_data2="비동의";
  }
  else{
    alert("동의 버튼을 눌러주세요.");
  }
  if(agree_data2.length!=0){
    location.replace("search_list.php?id="+agree_data2);
  }
}
function change_btn(event) {
  var buttons = document.querySelectorAll('.genders');
  buttons.forEach(button => button.classList.remove('selected')); // 기존 선택된 버튼의 스타일 제거

  var genderText = event.target.textContent;
  event.target.classList.add('selected'); // 클릭된 버튼에 'selected' 클래스 추가
  document.getElementById('userSex').value = genderText;
  document.getElementById('sex').innerText = genderText;

  localStorage.setItem('selectedGender', genderText); // 선택된 성별 localStorage에 저장
}

window.onload = function() {
  var savedGender = localStorage.getItem('selectedGender');
  if (savedGender) {
    var buttons = document.querySelectorAll('.genders');
    document.getElementById('userSex').value = savedGender;
    document.getElementById('sex').innerText = savedGender;

    buttons.forEach(button => {
      if (button.textContent === savedGender) {
        button.classList.add('selected'); // 저장된 성별에 해당하는 버튼에 'selected' 클래스 추가
      }
    });
  }
}