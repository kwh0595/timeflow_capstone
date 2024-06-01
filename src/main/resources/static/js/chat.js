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

// 팀 생성 버튼 클릭 시 코드 생성 및 팝업창 열기
document.querySelector(".create-team-button").addEventListener("click", function() {
  var roomName = document.querySelector("#popup input[type='text']").value.trim();
  if (roomName !== "") {
    var generatedCode = generateRandomCode();
    document.getElementById("generated-code").textContent = generatedCode;
    closePopup("popup");
    openPopup("code-popup");
    addTeamToList(roomName); // My Team 목록에 팀 이름 추가
  }
});

// My Team 목록에 팀 이름 추가하는 함수
function addTeamToList(teamName) {
  const teamListContainer = document.querySelector('.my-team-container p');
  teamListContainer.textContent = teamName;
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
  var isValid = isValidInviteCode(inviteCode);
  if (isValid) {
    closePopup("invite-popup");
  } else {
    document.getElementById("invite-error-msg").style.display = "block";
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

// 문서 로드 시 팝업창 숨기기
window.onload = function() {
  document.querySelectorAll(".popup").forEach(function(popup) {
    popup.style.display = "none";
  });
};
// 팀 이름 저장 변수
let teamName = '';
// 팀 생성 버튼 클릭 시 코드 생성 및 팝업창 열기
document.querySelector(".create-team-button").addEventListener("click", function() {
  teamName = document.querySelector("#popup input[type='text']").value.trim(); // 팀 이름 입력값 저장
  if (teamName !== "") {
    var generatedCode = generateRandomCode();
    document.getElementById("generated-code").textContent = generatedCode;
    openPopup("code-popup");
  }
});
// My Team 목록에 팀 이름 추가하는 함수
function addTeamToList() {
  const teamListContainer = document.querySelector('.my-team-container p');
  teamListContainer.textContent = teamName;
}
// 팀 생성 버튼 클릭 시 코드 생성 및 팝업창 열기
document.querySelector(".create-team-button").addEventListener("click", function() {
  var roomName = document.querySelector("#popup input[type='text']").value;
  if (roomName.trim() !== "") {
    var generatedCode = generateRandomCode();
    document.getElementById("generated-code").textContent = generatedCode;
    openPopup("code-popup");
    addTeamToList(); // My Team 목록에 팀 이름 추가
  }
});
// 랜덤 코드 생성 함수
function generateRandomCode() {
  var characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  var codeLength = 6; // 코드 길이를 6자리로 변경
  var code = "";
  for (var i = 0; i < codeLength; i++) {
    code += characters.charAt(Math.floor(Math.random() * characters.length));
  }
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
    closePopup(this.closest(".popup").classList[0]);
  });
});

// 문서 로드 시 팝업창 숨기기
window.onload = function() {
  document.querySelectorAll(".popup").forEach(function(popup) {
    popup.style.display = "none";
  });
};





  