// 해당 월의 이름을 가져오는 함수
function getMonthName(monthIndex) {
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
  const monthName = getMonthName(date.getMonth());
  const year = date.getFullYear();
  const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1).getDay();
  const daysInMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();

  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;

  const calendarDates = document.querySelectorAll('.calendar-date');
  calendarDates.forEach((dateElement, index) => {
    const dateNumber = index - firstDayOfMonth + 1;
    dateElement.classList.remove('highlight-today', 'selected');
    dateElement.innerHTML = '';
    if (dateNumber > 0 && dateNumber <= daysInMonth) {
      const dateText = document.createElement('span');
      dateText.textContent = dateNumber;
      dateElement.appendChild(dateText);

      const today = new Date();
      if (date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear() && dateNumber === today.getDate()) {
        dateElement.classList.add('highlight-today');
      }

      dateElement.onclick = function (event) {
        if (event.target === dateElement || event.target === dateText) { // 날짜 칸의 여백을 클릭했을 때만
          clearSelectedHighlights();
          dateElement.classList.add('selected');
          const selectedDateString = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(dateNumber).padStart(2, '0')}`;
          selectedDate = new Date(selectedDateString);
          updateDateDisplay(selectedDate);
          showEventInput(dateElement, dateNumber);
        }
      };
    } else {
      dateElement.textContent = '';
    }
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

// 선택된 요소의 하이라이트를 제거하는 함수
function clearSelectedHighlights() {
  const selectedElements = document.querySelectorAll('.selected');
  selectedElements.forEach(element => {
    element.classList.remove('selected');
  });
}

// 이벤트 '입력' 함수
function showEventInput(dateElement, dateNumber, existingEvent = null, eventInfo = null, isEdit = false) {
  clearEventInputs();

  const inputContainer = document.createElement('div');
  inputContainer.classList.add('input-container');

  if (isEdit) {
    const editTitle = document.createElement('h3');
    editTitle.textContent = '일정 수정';
    inputContainer.appendChild(editTitle);
  }

  const closeButton = document.createElement('button');
  closeButton.classList.add('close-button');
  closeButton.textContent = 'x';
  closeButton.onclick = () => {
    clearEventInputs();
    clearSelectedHighlights();
  };

  const eventNameInput = document.createElement('input');
  eventNameInput.type = 'text';
  eventNameInput.classList.add('eventname-input');
  eventNameInput.placeholder = '일정 이름';
  if (existingEvent) eventNameInput.value = existingEvent.name;

  const eventContentInput = document.createElement('input');
  eventContentInput.type = 'text';
  eventContentInput.classList.add('event-input');
  eventContentInput.placeholder = '일정 내용';
  if (existingEvent) eventContentInput.value = existingEvent.content;

  const startDateInput = document.createElement('input');
  startDateInput.type = 'date';
  startDateInput.classList.add('date-input');
  startDateInput.placeholder = '시작일';
  if (existingEvent) startDateInput.value = existingEvent.startDate;

  const endDateInput = document.createElement('input');
  endDateInput.type = 'date';
  endDateInput.classList.add('date-input');
  endDateInput.placeholder = '종료일';
  if (existingEvent) endDateInput.value = existingEvent.endDate;

  const statusSelect = document.createElement('select');
  statusSelect.classList.add('status-select');
  ['진행 중', '완료'].forEach(status => {
    const option = document.createElement('option');
    option.value = status;
    option.textContent = status;
    statusSelect.appendChild(option);
  });
  if (existingEvent) statusSelect.value = existingEvent.status;

  // 색상 팔레트 추가
  const colorPalette = document.createElement('div');
  colorPalette.classList.add('color-palette');
  const colors = ['orange', 'yellow', 'green', 'purple', 'red', 'pink'];
  colors.forEach(color => {
    const colorOption = document.createElement('div');
    colorOption.classList.add('color-option', color);
    colorOption.onclick = () => {
      selectedColor = color;
    };
    colorPalette.appendChild(colorOption);
  });
  let selectedColor = existingEvent && existingEvent.color ? existingEvent.color : 'sky blue'; // 기본 색상 파란색

  inputContainer.append(
      closeButton,
      eventNameInput,
      eventContentInput,
      startDateInput,
      endDateInput,
      statusSelect,
      colorPalette
  );

  const inputs = [eventNameInput, eventContentInput, startDateInput, endDateInput, statusSelect];
  inputs.forEach(input => {
    input.addEventListener('keypress', function (event) {
      if (event.key === 'Enter') {
        event.preventDefault();
        saveEvent(eventNameInput.value, eventContentInput.value, startDateInput.value, endDateInput.value, statusSelect.value, selectedColor, dateElement, eventInfo, existingEvent ? existingEvent.sid : null);
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

  eventContentInput.addEventListener('input', function () {
    if (this.value.length > 100) {
      this.value = this.value.slice(0, 100);
    }
  });

  // 콘솔에 이벤트 정보 출력
  if (isEdit) {
    const eventIdMessage = existingEvent && existingEvent.sid ? `이벤트 ID: ${existingEvent.sid}` : '이벤트 ID 없음';
    console.log(eventIdMessage);
    console.log("이벤트 내용:", existingEvent ? existingEvent : 'No event data');
  }
}

// 이벤트 '저장' 함수
function saveEvent(name, content, startDate, endDate, status, color, dateElement, eventInfo = null, eventId = null) {
  console.log('Saving event:', { name, content, startDate, endDate, status, color, dateElement, eventId }); // 저장할 이벤트 데이터 확인
  const truncatedContent = content.length > 100 ? content.slice(0, 100) + '...' : content;

  if (!eventInfo) {
    eventInfo = document.createElement('div');
    eventInfo.classList.add('event-info');
    eventInfo.style.backgroundColor = color;
    eventInfo.innerHTML = `<strong class="event-button">${name}</strong><br>${truncatedContent}`;

    const actionButtonsContainer = document.createElement('div');
    actionButtonsContainer.classList.add('action-buttons-container');

    const editButton = document.createElement('button');
    editButton.classList.add('edit-button');
    editButton.textContent = '수정';
    editButton.onclick = function () {
      showEventInput(dateElement, null, { sid: eventId, name, content, startDate, endDate, status, color }, eventInfo, true);
    };

    const deleteButton = document.createElement('button');
    deleteButton.classList.add('delete-button');
    deleteButton.textContent = '삭제';
    deleteButton.onclick = function () {
      deleteEvent(eventId, eventInfo);
    };

    actionButtonsContainer.append(editButton, deleteButton);
    eventInfo.appendChild(actionButtonsContainer);

    const spanElement = dateElement.querySelector('span');
    let eventsContainer = dateElement.querySelector('.events-container');

    if (!eventsContainer) {
      eventsContainer = document.createElement('div');
      eventsContainer.classList.add('events-container');
      dateElement.appendChild(eventsContainer);
      dateElement.insertBefore(eventsContainer, spanElement.nextSibling);
    }

    eventsContainer.appendChild(eventInfo);
  } else {
    // 기존 이벤트가 있는 경우, 기존 이벤트 정보를 업데이트
    eventInfo.querySelector('.event-button').textContent = name;
    eventInfo.querySelector('.event-button + br').nextSibling.nodeValue = truncatedContent;
    eventInfo.style.backgroundColor = color;
  }

  // 날짜 포맷 함수 정의
  const formatDate = (date) => {
    const d = new Date(date);
    return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}T${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`;
  };

  // 이벤트 데이터 생성
  const eventData = {
    sid: eventId,
    sname: name,
    scontents: content,
    startdate: formatDate(startDate),
    enddate: formatDate(endDate),
    sprocess: status,
    scolor: color
  };

  const url = eventId ? `/api/schedules/${eventId}` : '/schedule/personal';
  const method = eventId ? 'PUT' : 'POST';

  console.log("Saving event with method:", method, "Event ID:", eventId ? eventId : '없음');

  fetch(url, {
    method: method,
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(eventData)
  })
      .then(response => response.json())
      .then(data => {
        if (method === 'POST') {
          eventId = data.sid; // 서버로부터 반환된 sid 저장
          eventInfo.dataset.sid = eventId; // eventInfo 객체에 sid 저장
        }
        console.log('Event saved successfully with sid:', eventId);

        if (method === 'PUT') {
          fetch(`/api/schedules/${eventId}`)
              .then(response => response.json())
              .then(updatedEvent => {
                eventInfo.querySelector('.event-button').textContent = updatedEvent.sname;
                eventInfo.querySelector('.event-button + br').nextSibling.nodeValue = updatedEvent.scontents;
                eventInfo.style.backgroundColor = updatedEvent.scolor;
              });
        }
      })
      .catch(error => {
        console.error('Network error:', error);
      });
}

// fetch 이벤트 삭제 함수
function deleteEvent(eventId, eventInfo) {
  if (eventId) {
    fetch(`/api/schedules/${eventId}`, {
      method: 'DELETE',
    })
        .then(response => {
          if (response.ok) {
            console.log('Event deleted successfully');
            eventInfo.remove();
          } else {
            console.error('Error deleting event');
          }
        })
        .catch(error => {
          console.error('Network error:', error);
        });
  } else {
    eventInfo.remove();
  }
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
document.getElementById('date-input').addEventListener('change', function (event) {
  const selectedDateValue = new Date(event.target.value);
  selectedDate = selectedDateValue;
  updateDateDisplay(selectedDate);
  updateCalendar(selectedDate);
});

// 사용자의 일정을 불러오는 함수
// 사용자의 일정을 불러오는 함수
function fetchUserSchedules() {
  fetch('/api/schedules/user')
      .then(response => response.json())
      .then(data => {
        console.log('Fetched schedules:', data); // 불러온 일정 데이터 확인
        data.forEach(schedule => {
          const dateElement = document.querySelector(`.calendar-date[data-date="${schedule.startDate.split('T')[0]}"]`);
          if (dateElement) {
            saveEvent(schedule.sname, schedule.scontents, schedule.startDate, schedule.endDate, schedule.sprocess, schedule.scolor, dateElement, null, schedule.sid);
          }
        });
      })
      .catch(error => {
        console.error('Error fetching schedules:', error);
      });
}


// 페이지 로드 시 사용자 일정 불러오기
fetchUserSchedules();
