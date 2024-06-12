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

// 팀 이름을 서버로 보내는 함수
function sendTeamNameToServer(teamName) {
  return $.ajax({
    url: "team/create",
    method: "POST",
    //contentType: "application/x-www-form-urlencoded", // 폼 인코딩 형식으로 전송
    data: { teamName: teamName }, // 객체 형태로 전송
    dataType: "text"
  });
}

//초대 코드 받아오는 통신하는 함수
function fetchRandomCode() {
  return $.ajax({
    url: "team/create", // 서버의 URL
    method: "GET", // 요청 방식
    dataType: "json" // 서버로부터 받을 데이터의 타입
  });
}

function displayInviteCode() {
  sendTeamNameToServer().done(function(codeResponse) {
    document.getElementById("generated-code").textContent = codeResponse.joinCode;
  }).fail(function(jqXHR, textStatus, errorThrown) {
    console.error("Failed to fetch invite code: ", textStatus, errorThrown);
  });
}

document.addEventListener("DOMContentLoaded", function() {
  displayInviteCode();
})

document.querySelector(".create-team-button").addEventListener("click", function() {
  let teamName = document.querySelector("#room-name-input").value.trim(); // 팀 이름 입력값 저장
  if (teamName !== "") {
    // 서버로 팀 이름 전송
    // 여기서 초대코드 받아오는 함수를 호출합니다.
    sendTeamNameToServer(teamName).done(function(codeResponse) {
      console.log("joinCode를 받아왔다 ~~~ : ", codeResponse)
      document.getElementById("generated-code").textContent = codeResponse;
      teamNames.push(teamName); // 팀 이름 배열에 추가
      teams[codeResponse] = teamName; // 초대코드와 팀 이름 연결하여 저장
      addTeamToList(); // My Team 목록에 팀 이름 추가
      closePopup('popup'); // 팝업 닫기
      openPopup("code-popup"); // 코드 팝업 열기
      document.querySelector("#room-name-input").value = ''; // 입력란 초기화
    }).fail(function(jqXHR, textStatus, errorThrown) {
      console.log("jqXHR:", jqXHR);
      console.log("textStatus:", textStatus);
      console.log("errorThrown:", errorThrown);
      alert("초대 코드를 받아오는데 실패했습니다: " + textStatus);
    });
  }
});

// My Team 목록에 팀 이름 추가하는 함수
function addTeamToList() {
  const teamListContainer = document.querySelector('.team-list');
  teamListContainer.innerHTML = ''; // 기존 목록 초기화
  teamNames.forEach(function(name) {
    const teamItem = document.createElement('button'); // 버튼 요소 생성
    teamItem.textContent = name;
    teamItem.classList.add('team-button'); // 필요 시 클래스 추가
    teamListContainer.appendChild(teamItem);
  });
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

function isValidInviteCode(inviteCode) {
  // 초대코드가 5~10자리 알파벳 대소문자로만 구성되어 있는지 확인하는 정규 표현식
  var inviteCodePattern = /^[A-Za-z]{5,10}$/;
  return inviteCodePattern.test(inviteCode);
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

// 초대코드 확인 버튼 클릭 시 호출
document.querySelector(".confirm-button").addEventListener("click", function() {
  checkInviteCode();
});