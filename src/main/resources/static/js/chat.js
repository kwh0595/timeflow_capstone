// 팝업창 표시 함수
function openPopup(popupId) {
  var popup = document.getElementById(popupId);
  popup.style.display = "block";
}

// 팝업창 닫기 함수
function closePopup(popupId) {
  var popup = document.getElementById(popupId);
  popup.style.display = "none";
}

// 방 만들기 버튼 클릭 시 팝업창 열기
document.querySelector(".create-room-button").addEventListener("click", function() {
  openPopup("popup");
});

// 초대코드 버튼 클릭 시 팝업창 열기
document.querySelector(".invite-code-button").addEventListener("click", function() {
  openPopup("invite-popup");
});

// 닫기 버튼 클릭 시 팝업창 닫기
document.querySelectorAll(".close-button").forEach(function(closeButton) {
  closeButton.addEventListener("click", function() {
    var popupId = this.closest(".popup").id;
    closePopup(popupId);
  });
});

// 문서 로드 시 팝업창 숨기기
window.onload = function() {
  document.querySelectorAll(".popup").forEach(function(popup) {
    popup.style.display = "none";
  });
};

// 팀 이름 저장 배열
let teamNames = [];

// 초대코드와 팀 이름을 연결하는 객체
let teams = {};

// 팀 생성 버튼 클릭 시 코드 생성 및 팝업창 열기
document.querySelector(".create-team-button").addEventListener("click", function() {
  let teamName = document.querySelector("#room-name-input").value.trim(); // 팀 이름 입력값 저장
  if (teamName !== "") {
    var generatedCode = generateRandomCode();
    document.getElementById("generated-code").textContent = generatedCode;
    teamNames.push(teamName); // 팀 이름 배열에 추가
    teams[generatedCode] = teamName; // 초대코드와 팀 이름 연결하여 저장
    addTeamToList(); // My Team 목록에 팀 이름 추가
    closePopup('popup'); // 팝업 닫기
    openPopup("code-popup"); // 코드 팝업 열기
    document.querySelector("#room-name-input").value = ''; // 입력란 초기화
  }
});

// My Team 목록에 팀 이름 추가하는 함수
function addTeamToList() {
  const teamListContainer = document.querySelector('.team-list');
  teamListContainer.innerHTML = ''; // 기존 목록 초기화
  teamNames.forEach(function(name) {
    const teamItem = document.createElement('p');
    teamItem.textContent = name;
    teamListContainer.appendChild(teamItem);
  });
}

// 코드 생성 함수
function generateRandomCode() {
  var numbers = "0123456789";
  var letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var code = "";
  // 숫자 랜덤 3개 생성
  for (var i = 0; i < 3; i++) {
    code += numbers.charAt(Math.floor(Math.random() * numbers.length));
  }
  // 영문 랜덤 3개 생성
  for (var i = 0; i < 3; i++) {
    code += letters.charAt(Math.floor(Math.random() * letters.length));
  }
  // 생성된 코드를 섞기 위해 배열로 변환 후 셔플
  code = code.split('').sort(function() {
    return 0.5 - Math.random();
  }).join('');
  return code;
}

// 코드 복사 함수
function copyCode() {
  var generatedCode = document.getElementById("generated-code").textContent;
  navigator.clipboard.writeText(generatedCode)
      .then(function() {
        console.log("Code copied to clipboard: " + generatedCode);
      })
      .catch(function(error) {
        console.error("Failed to copy code to clipboard: ", error);
      });
  closePopup("code-popup");
}

// 초대코드 확인 함수
function checkInviteCode() {
  var inviteCode = document.getElementById("invite-code").value.trim();
  var inviteErrorMsg = document.getElementById("invite-error-msg");
  var isValid = isValidInviteCode(inviteCode);

  if (isValid && teams[inviteCode]) {
    if (!teamNames.includes(teams[inviteCode])) {
      teamNames.push(teams[inviteCode]);
      addTeamToList();
    }
    closePopup("invite-popup");
    inviteErrorMsg.style.display = "none"; // 오류 메시지 숨기기
  } else {
    inviteErrorMsg.style.display = "block"; // 오류 메시지 표시
  }
}

// 초대코드 유효성 검사 함수
function isValidInviteCode(code) {
  // 초대코드가 비어 있는 경우 유효하지 않음
  if (code === "") {
    return false;
  }
  // 영문과 숫자가 섞여 있는지 확인
  var hasLetter = false;
  var hasNumber = false;
  for (var i = 0; i < code.length; i++) {
    var charCode = code.charCodeAt(i);
    if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122)) {
      hasLetter = true;
    } else if (charCode >= 48 && charCode <= 57) {
      hasNumber = true;
    }
  }
  return hasLetter && hasNumber;
}

// 초대코드 확인 버튼 클릭 시 호출
document.querySelector(".confirm-button").addEventListener("click", function() {
  checkInviteCode();
});
