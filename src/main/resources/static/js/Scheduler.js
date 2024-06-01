function getMonthName(monthIndex) {
  const months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];
  return months[monthIndex];
}
let selectedDate = new Date(); // 현재 날짜로 초기화

function updateCalendar(selectedDate) {
  const monthName = getMonthName(selectedDate.getMonth());
  const year = selectedDate.getFullYear();
  const firstDayOfMonth = new Date(selectedDate.getFullYear(), selectedDate.getMonth(), 1).getDay();
  const daysInMonth = new Date(selectedDate.getFullYear(), selectedDate.getMonth() + 1, 0).getDate();

  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;

  const calendarDates = document.querySelectorAll('.calendar-date');
  calendarDates.forEach((dateElement, index) => {
    const dateNumber = index - firstDayOfMonth + 1;
    dateElement.classList.remove('highlight-today', 'selected');
    dateElement.innerHTML = ''; // Clear previous content
    if (dateNumber > 0 && dateNumber <= daysInMonth) {
      const dateText = document.createElement('span');
      dateText.textContent = dateNumber;
      dateElement.appendChild(dateText);

      const today = new Date();
      if (selectedDate.getMonth() === today.getMonth() && selectedDate.getFullYear() === today.getFullYear() && dateNumber === today.getDate()) {
        dateElement.classList.add('highlight-today');
      }

      dateElement.onclick = function() {
        clearSelectedHighlights();
        dateElement.classList.add('selected');
        const selectedDateString = `${selectedDate.getFullYear()}-${String(selectedDate.getMonth() + 1).padStart(2, '0')}-${String(dateNumber).padStart(2, '0')}`;
        updateDateDisplay(new Date(selectedDateString));
        showEventInput(dateElement, dateNumber);
      };
    } else {
      dateElement.textContent = '';
    }
  });
}

function clearSelectedHighlights() {
  const selectedElements = document.querySelectorAll('.selected');
  selectedElements.forEach(element => {
    element.classList.remove('selected');
  });
}

function updateDateDisplay(date) {
  const monthName = getMonthName(date.getMonth());
  const year = date.getFullYear();
  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;
}

function setCurrentDate() {
  const today = new Date();
  updateDateDisplay(today);
  updateCalendar(today);
}

function showEventInput(dateElement, dateNumber) {
  clearEventInputs();

  const inputContainer = document.createElement('div');
  inputContainer.classList.add('input-container');

  const closeButton = document.createElement('button');
  closeButton.classList.add('close-button');
  closeButton.textContent = 'x';
  closeButton.onclick = function() {
    clearEventInputs();
  };

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
  const statusOptions = ['진행 중', '완료'];
  statusOptions.forEach(status => {
    const option = document.createElement('option');
    option.value = status;
    option.textContent = status;
    statusSelect.appendChild(option);
  });

  inputContainer.appendChild(closeButton);  // 'x' 버튼을 입력란에 추가
  inputContainer.appendChild(eventNameInput);
  inputContainer.appendChild(eventContentInput);
  inputContainer.appendChild(startDateInput);
  inputContainer.appendChild(endDateInput);
  inputContainer.appendChild(statusSelect);

  const inputs = [eventNameInput, eventContentInput, startDateInput, endDateInput, statusSelect];

  function handleEnterKey(event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      saveEvent(eventNameInput.value, eventContentInput.value, startDateInput.value, endDateInput.value, statusSelect.value, dateElement);
      clearEventInputs(); // 입력 칸을 숨김
      dateElement.focus(); // 날짜 칸에 포커스 설정
    }
  }

  inputs.forEach(input => {
    input.addEventListener('keypress', handleEnterKey);
  });

  const boundingRect = dateElement.getBoundingClientRect();

  // 입력 칸을 일 칸 내부에 위치하도록 설정
  inputContainer.style.top = `${boundingRect.top + dateElement.clientHeight}px`; // 세로 위치 조정
  inputContainer.style.left = `${boundingRect.left}px`; // 가로 위치 조정
  inputContainer.style.maxHeight = `calc(90vh - ${boundingRect.top + dateElement.clientHeight}px - 10px)`; // 최대 높이 조정

  document.body.appendChild(inputContainer); // 입력란을 body에 추가

  eventNameInput.focus(); // 입력란에 포커스 설정

  // 입력된 내용을 최대 10자로 제한
  eventContentInput.addEventListener('input', function() {
    if (this.value.length > 10) {
      this.value = this.value.slice(0, 10);
    }
  });
}

function saveEvent(name, content, startDate, endDate, status, dateElement, dateNumber) {
  // 여기서 이벤트를 저장하고 필요한 최소한의 정보를 화면에 표시
  const truncatedContent = content.length > 10 ? content.slice(0, 10) + '...' : content;
  const eventInfo = document.createElement('div');
  eventInfo.classList.add('event-info');
  eventInfo.style.fontSize = '12px'; // 폰트 크기 조정
  eventInfo.innerHTML = `<strong>${name}</strong><br>${truncatedContent}`;
  dateElement.appendChild(eventInfo);

  // AJAX 요청을 통해 서버에 이벤트 데이터를 전송
  // 날짜를 올바른 형식으로 변환
  const formatDate = (date) => {
    const d = new Date(date);
    return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}T${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`;
  };

  const eventData = {
    sname: name,
    scontents: content,
    scheduleTime: formatDate(startDate),
    endTime: formatDate(endDate),
    sprocess: status
  };

  const xhr = new XMLHttpRequest();
  xhr.open('POST', '/api/schedules', true);
  xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
  xhr.onreadystatechange = function() {
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

function clearEventInputs() {
  const inputContainers = document.querySelectorAll('.input-container');
  inputContainers.forEach(container => {
    container.remove();
  });
}

function clearSelectedHighlights() {
  const selectedElements = document.querySelectorAll('.selected');
  selectedElements.forEach(element => {
    element.classList.remove('selected');
  });
}

function updateDateDisplay(date) {
  const monthName = getMonthName(date.getMonth());
  const year = date.getFullYear();
  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;
}

function setCurrentDate() {
  const today = new Date();
  updateDateDisplay(today);
  updateCalendar(today);
}

// 페이지 로드 시 현재 날짜를 설정
window.onload = function() {
  setCurrentDate();
};


