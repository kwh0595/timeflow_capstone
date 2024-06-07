// 해당 월의 이름을 가져오는 함수
function getMonthName(monthIndex) {
  // 월 이름 배열
  const months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];
  return months[monthIndex];
}

// 선택된 날짜를 저장하는 변수
let selectedDate = new Date(); // 현재 날짜로 초기화

// 캘린더를 업데이트하는 함수
function updateCalendar(date) {
  // 선택한 날짜의 월과 연도, 해당 월의 첫날의 요일, 해당 월의 일 수를 구함
  const monthName = getMonthName(date.getMonth());
  const year = date.getFullYear();
  const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1).getDay();
  const daysInMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();

  // HTML에 월과 연도를 업데이트
  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;

  // 캘린더의 날짜 요소를 가져와 각각 처리
  const calendarDates = document.querySelectorAll('.calendar-date');
  calendarDates.forEach((dateElement, index) => {
    const dateNumber = index - firstDayOfMonth + 1;
    dateElement.classList.remove('highlight-today', 'selected');
    dateElement.innerHTML = ''; // 이전 콘텐츠를 지움
    if (dateNumber > 0 && dateNumber <= daysInMonth) {
      // 날짜 텍스트를 생성하고 추가
      const dateText = document.createElement('span');
      dateText.textContent = dateNumber;
      dateElement.appendChild(dateText);

      // 오늘 날짜인 경우 강조 표시
      const today = new Date();
      if (date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear() && dateNumber === today.getDate()) {
        dateElement.classList.add('highlight-today');
      }

      // 클릭 이벤트 핸들러 추가
      dateElement.onclick = function() {
        clearSelectedHighlights();
        dateElement.classList.add('selected');
        // 선택한 날짜 업데이트 및 이벤트 입력 표시
        const selectedDateString = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(dateNumber).padStart(2, '0')}`;
        selectedDate = new Date(selectedDateString);
        updateDateDisplay(selectedDate);
        showEventInput(dateElement, dateNumber);
      };
    } else {
      dateElement.textContent = '';
    }
  });
}

// 선택된 요소의 하이라이트를 제거하는 함수
function clearSelectedHighlights() {
  const selectedElements = document.querySelectorAll('.selected');
  selectedElements.forEach(element => {
    element.classList.remove('selected');
  });
}

// 날짜를 표시하는 함수를 업데이트
function updateDateDisplay(date) {
  const monthName = getMonthName(date.getMonth());
  const year = date.getFullYear();
  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;
}

// 현재 날짜를 설정하는 함수
function setCurrentDate() {
  const today = new Date();
  selectedDate = today;
  updateDateDisplay(today);
  updateCalendar(today);
}

// 이벤트 입력을 보여주는 함수
function showEventInput(dateElement, dateNumber) {
  clearEventInputs();

  // 이벤트 입력 컨테이너 생성
  const inputContainer = document.createElement('div');
  inputContainer.classList.add('input-container');

  // 닫기 버튼 생성 및 이벤트 리스너 추가
  const closeButton = document.createElement('button');
  closeButton.classList.add('close-button');
  closeButton.textContent = 'x';
  closeButton.onclick = clearEventInputs;

  // 입력 요소들 생성
  const eventNameInput = document.createElement('input');
  eventNameInput.type = 'text';
  eventNameInput.classList.add('eventname-input');
  eventNameInput.placeholder = '일정 이름';

  const eventContentInput = document.createElement('input');
  eventContentInput.type = 'text';
  eventContentInput.classList.add('event-input');
  eventContentInput.placeholder = '일정 내용';

  const startDateInput = document.createElement('input');
  startDateInput.type = 'date';
  startDateInput.classList.add('date-input');
  startDateInput.placeholder = '시작일';

  const endDateInput = document.createElement('input');
  endDateInput.type = 'date';
  endDateInput.classList.add('date-input');
  endDateInput.placeholder = '종료일';

  const statusSelect = document.createElement('select');
  statusSelect.classList.add('status-select');
  ['진행 중', '완료'].forEach(status => {
    const option = document.createElement('option');
    option.value = status;
    option.textContent = status;
    statusSelect.appendChild(option);
  });

  // 생성한 요소들을 컨테이너에 추가
  inputContainer.append(
      closeButton,
      eventNameInput,
      eventContentInput,
      startDateInput,
      endDateInput,
      statusSelect
  );

  // 입력 요소에 Enter 키 이벤트 핸들러 추가
  const inputs = [eventNameInput, eventContentInput, startDateInput, endDateInput, statusSelect];
  inputs.forEach(input => {
    input.addEventListener('keypress', function(event) {
      if (event.key === 'Enter') {
        event.preventDefault();
        // 입력된 이벤트 저장하고 입력 요소들 비우기
        saveEvent(eventNameInput.value, eventContentInput.value, startDateInput.value, endDateInput.value, statusSelect.value, dateElement);
        clearEventInputs();
        dateElement.focus();
      }
    });
  });

  // 입력 컨테이너 위치 지정
  const boundingRect = dateElement.getBoundingClientRect();
  inputContainer.style.top = `${boundingRect.top + dateElement.clientHeight}px`;
  inputContainer.style.left = `${boundingRect.left}px`;
  inputContainer.style.maxHeight = `calc(90vh - ${boundingRect.top + dateElement.clientHeight}px - 10px)`;

  // 입력 컨테이너를 body에 추가하고 포커스 설정
  document.body.appendChild(inputContainer);
  eventNameInput.focus();

  // 입력 내용이 10자를 초과하는 경우 자르기
  eventContentInput.addEventListener('input', function() {
    if (this.value.length > 10) {
      this.value = this.value.slice(0, 10);
    }
  });
}

// 이벤트 저장 함수
function saveEvent(name, content, startDate, endDate, status, dateElement) {
  // 내용이 긴 경우 일부 자르기
  const truncatedContent = content.length > 10 ? content.slice(0, 10) + '...' : content;

  // 이벤트 정보를 담을 div 요소 생성 및 스타일 및 내용 추가
  const eventInfo = document.createElement('div');
  eventInfo.classList.add('event-info');
  eventInfo.style.fontSize = '12px';
  eventInfo.innerHTML = `<strong class="event-button">${name}</strong><br>${truncatedContent}`;

  // 수정/삭제 버튼 컨테이너 생성
  const actionButtonsContainer = document.createElement('div');
  actionButtonsContainer.classList.add('action-buttons-container');
  actionButtonsContainer.style.display = 'none';

  // 수정 버튼 생성 및 이벤트 리스너 추가
  const editButton = document.createElement('button');
  editButton.classList.add('edit-button');
  editButton.textContent = '수정';
  editButton.onclick = function () {
    showEventInput(dateElement, name, { name, content, startDate, endDate, status });
  };

  // 삭제 버튼 생성 및 이벤트 리스너 추가
  const deleteButton = document.createElement('button');
  deleteButton.classList.add('delete-button');
  deleteButton.textContent = '삭제';
  deleteButton.onclick = function () {
    dateElement.removeChild(eventInfo);
  };

  // 수정/삭제 버튼을 컨테이너에 추가
  actionButtonsContainer.append(editButton, deleteButton);
  eventInfo.appendChild(actionButtonsContainer);

  // 일정 이름 클릭 시 수정/삭제 버튼 표시
  eventInfo.querySelector('.event-button').onclick = function () {
    actionButtonsContainer.style.display = actionButtonsContainer.style.display === 'none' ? 'flex' : 'none';
  };

  // 날짜 요소에 이벤트 정보 추가
  dateElement.appendChild(eventInfo);

  // 날짜 포맷 함수 정의
  const formatDate = (date) => {
    const d = new Date(date);
    return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}T${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`;
  };

  // 이벤트 데이터 생성
  const eventData = {
    sname: name,
    scontents: content,
    scheduleTime: formatDate(startDate),
    endTime: formatDate(endDate),
    sprocess: status
  };

  // XMLHttpRequest를 사용하여 이벤트 데이터 서버에 전송
  const xhr = new XMLHttpRequest();
  xhr.open('POST', '/api/schedules', true);
  xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        console.log('Event saved successfully');
      } else {
        console.error('Error saving event');
      }
    }
  };
  xhr.send(JSON.stringify(eventData));
}

function deleteEvent(name) {
  const xhr = new XMLHttpRequest();
  xhr.open('DELETE', `/api/schedules?name=${encodeURIComponent(name)}`, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        console.log('Event deleted successfully');
      } else {
        console.error('Error deleting event');
      }
    }
  };
  xhr.send();
}

// 이벤트 입력을 보여주는 함수
function showEventInput(dateElement, name, eventInfo = {}) {
  clearEventInputs();

  const inputContainer = document.createElement('div');
  inputContainer.classList.add('input-container');

  const closeButton = document.createElement('button');
  closeButton.classList.add('close-button');
  closeButton.textContent = 'x';
  closeButton.onclick = clearEventInputs;

  const eventNameInput = document.createElement('input');
  eventNameInput.type = 'text';
  eventNameInput.classList.add('eventname-input');
  eventNameInput.placeholder = '일정 이름';
  if (eventInfo.name) eventNameInput.value = eventInfo.name;

  const eventContentInput = document.createElement('input');
  eventContentInput.type = 'text';
  eventContentInput.classList.add('event-input');
  eventContentInput.placeholder = '일정 내용';
  if (eventInfo.content) eventContentInput.value = eventInfo.content;

  const startDateInput = document.createElement('input');
  startDateInput.type = 'date';
  startDateInput.classList.add('date-input');
  startDateInput.placeholder = '시작일';
  if (eventInfo.startDate) startDateInput.value = eventInfo.startDate;

  const endDateInput = document.createElement('input');
  endDateInput.type = 'date';
  endDateInput.classList.add('date-input');
  endDateInput.placeholder = '종료일';
  if (eventInfo.endDate) endDateInput.value = eventInfo.endDate;

  const statusSelect = document.createElement('select');
  statusSelect.classList.add('status-select');
  ['진행 중', '완료'].forEach(status => {
    const option = document.createElement('option');
    option.value = status;
    option.textContent = status;
    if (eventInfo.status && eventInfo.status === status) {
      option.selected = true;
    }
    statusSelect.appendChild(option);
  });

  inputContainer.append(
      closeButton,
      eventNameInput,
      eventContentInput,
      startDateInput,
      endDateInput,
      statusSelect
  );

  const inputs = [eventNameInput, eventContentInput, startDateInput, endDateInput, statusSelect];
  inputs.forEach(input => {
    input.addEventListener('keypress', function(event) {
      if (event.key === 'Enter') {
        event.preventDefault();
        saveEvent(eventNameInput.value, eventContentInput.value, startDateInput.value, endDateInput.value, statusSelect.value, dateElement);
        clearEventInputs();
        dateElement.focus();
      }
    });
  });

  const boundingRect = dateElement.getBoundingClientRect();
  inputContainer.style.top = `${boundingRect.top + dateElement.clientHeight}px`;
  inputContainer.style.left = `${boundingRect.left}px`;
  inputContainer.style.maxHeight = `calc(90vh - ${boundingRect.top + dateElement.clientHeight}px - 10px)`;

  document.body.appendChild(inputContainer);
  eventNameInput.focus();

  eventContentInput.addEventListener('input', function() {
    if (this.value.length > 10) {
      this.value = this.value.slice(0, 10);
    }
  });
}

// 이벤트 입력 요소들을 지우는 함수
function clearEventInputs() {
  const inputContainers = document.querySelectorAll('.input-container');
  inputContainers.forEach(container => {
    container.remove();
  });
}

// 페이지 로드 시 현재 날짜를 설정하는 함수 호출
setCurrentDate();

// 날짜 선택 시 이벤트 핸들러 추가
document.getElementById('date-input').addEventListener('change', function(event) {
  const selectedDateValue = new Date(event.target.value);
  selectedDate = selectedDateValue;
  updateDateDisplay(selectedDate);
  updateCalendar(selectedDate);
});
/*
// XMLHttpRequest를 사용하여 이벤트 데이터 서버에 전송
const xhr = new XMLHttpRequest();
xhr.open('POST', '/api/schedules', true);
xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
xhr.onreadystatechange = function () {
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {
      console.log('Event saved successfully');
    } else {
      console.error('Error saving event');
    }
  }
};
xhr.send(JSON.stringify(eventData));
}

// 이벤트 입력을 보여주는 함수
function showEventInput(dateElement, name, eventInfo = {}) {
clearEventInputs();

const inputContainer = document.createElement('div');
inputContainer.classList.add('input-container');

const closeButton = document.createElement('button');
closeButton.classList.add('close-button');
closeButton.textContent = 'x';
closeButton.onclick = clearEventInputs;

const eventNameInput = document.createElement('input');
eventNameInput.type = 'text';
eventNameInput.classList.add('eventname-input');
eventNameInput.placeholder = '일정 이름';
if (eventInfo.name) eventNameInput.value = eventInfo.name;

const eventContentInput = document.createElement('input');
eventContentInput.type = 'text';
eventContentInput.classList.add('event-input');
eventContentInput.placeholder = '일정 내용';
if (eventInfo.content) eventContentInput.value = eventInfo.content;

const startDateInput = document.createElement('input');
startDateInput.type = 'date';
startDateInput.classList.add('date-input');
startDateInput.placeholder = '시작일';
if (eventInfo.startDate) startDateInput.value = eventInfo.startDate;

const endDateInput = document.createElement('input');
endDateInput.type = 'date';
endDateInput.classList.add('date-input');
endDateInput.placeholder = '종료일';
if (eventInfo.endDate) endDateInput.value = eventInfo.endDate;

const statusSelect = document.createElement('select');
statusSelect.classList.add('status-select');
['진행 중', '완료'].forEach(status => {
  const option = document.createElement('option');
  option.value = status;
  option.textContent = status;
  if (eventInfo.status && eventInfo.status === status) {
    option.selected = true;
  }
  statusSelect.appendChild(option);
});

inputContainer.append(
    closeButton,
    eventNameInput,
    eventContentInput,
    startDateInput,
    endDateInput,
    statusSelect
);

const inputs = [eventNameInput, eventContentInput, startDateInput, endDateInput, statusSelect];
inputs.forEach(input => {
  input.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      saveEvent(eventNameInput.value, eventContentInput.value, startDateInput.value, endDateInput.value, statusSelect.value, dateElement);
      clearEventInputs();
      dateElement.focus();
    }
  });
});

const boundingRect = dateElement.getBoundingClientRect();
inputContainer.style.top = `${boundingRect.top + dateElement.clientHeight}px`;
inputContainer.style.left = `${boundingRect.left}px`;
inputContainer.style.maxHeight = `calc(90vh - ${boundingRect.top + dateElement.clientHeight}px - 10px)`;

document.body.appendChild(inputContainer);
eventNameInput.focus();

eventContentInput.addEventListener('input', function() {
  if (this.value.length > 10) {
    this.value = this.value.slice(0, 10);
  }
});
}

// 이벤트 입력 요소들을 지우는 함수
function clearEventInputs() {
const inputContainers = document.querySelectorAll('.input-container');
inputContainers.forEach(container => {
  container.remove();
});
}


// 페이지 로드 시 현재 날짜를 설정하는 함수 호출
setCurrentDate();

// 날짜 선택 시 이벤트 핸들러 추가
document.getElementById('date-input').addEventListener('change', function(event) {
const selectedDateValue = new Date(event.target.value);
selectedDate = selectedDateValue;
updateDateDisplay(selectedDate);
updateCalendar(selectedDate);
});
*/


