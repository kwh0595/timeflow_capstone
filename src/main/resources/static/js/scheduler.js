// 해당 월의 이름을 가져오는 함수
function getMonthName(monthIndex) {
  const months = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];
  return months[monthIndex];
}

// 선택된 날짜를 저장하는 변수
let selectedDate = new Date(); // 초기값은 현재 날짜

// 캘린더를 업데이트하는 함수
function updateCalendar(date) {
  const monthName = getMonthName(date.getMonth()); // 현재 월의 이름
  const year = date.getFullYear(); // 현재 연도
  const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1).getDay(); // 해당 월의 첫째 날의 요일
  const daysInMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate(); // 해당 월의 총 일수

  // 캘린더 헤더에 월과 연도를 설정
  document.getElementById('month').innerText = monthName;
  document.getElementById('year').innerText = year;

  // 모든 날짜 요소를 초기화
  const calendarDates = document.querySelectorAll('.calendar-date');
  calendarDates.forEach((dateElement, index) => {
    const dateNumber = index - firstDayOfMonth + 1;
    dateElement.classList.remove('highlight-today', 'selected'); // 기존 스타일 초기화
    dateElement.innerHTML = ''; // 기존 내용을 초기화

    if (dateNumber > 0 && dateNumber <= daysInMonth) {
      const dateText = document.createElement('span');
      dateText.textContent = dateNumber; // 날짜 번호 설정
      dateElement.appendChild(dateText);

      // data-date 속성을 설정하여 올바른 날짜로 업데이트
      const fullDate = `${year}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(dateNumber).padStart(2, '0')}`;
      dateElement.setAttribute('data-date', fullDate);

      // 일요일인 경우 텍스트 빨간색으로 설정
      const dayOfWeek = new Date(date.getFullYear(), date.getMonth(), dateNumber).getDay();
      if (dayOfWeek === 0) {
        dateText.style.color = 'red';
      }

      // 오늘 날짜에 하이라이트 추가
      const today = new Date();
      if (date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear() && dateNumber === today.getDate()) {
        dateElement.classList.add('highlight-today');
      }

      // 날짜 클릭 이벤트 설정
      dateElement.onclick = function (event) {
        if (event.target === dateElement || event.target === dateText) {
          clearSelectedHighlights(); // 선택된 하이라이트 초기화
          dateElement.classList.add('selected'); // 선택된 날짜에 하이라이트 추가
          const selectedDateString = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(dateNumber).padStart(2, '0')}`;
          selectedDate = new Date(selectedDateString); // 선택된 날짜 업데이트
          updateDateDisplay(selectedDate); // 날짜 디스플레이 업데이트
          showEventInput(dateElement, dateNumber); // 이벤트 입력 폼 표시
        }
      };
    } else {
      dateElement.setAttribute('data-date', ''); // 날짜가 없는 요소는 비워둠
    }
  });

  // 사용자의 일정을 다시 불러옵니다.
  fetchUserSchedules();
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

// 이벤트 입력 폼을 표시하는 함수
function showEventInput(dateElement, dateNumber, existingEvent = null, eventInfo = null, isEdit = false) {
  clearEventInputs(); // 기존에 표시된 입력 폼을 제거

  const inputContainer = document.createElement('div');
  inputContainer.classList.add('input-container');

  if (isEdit) {
    const editTitle = document.createElement('h3');
    editTitle.textContent = '일정 수정'; // 수정 모드일 때 제목 표시
    inputContainer.appendChild(editTitle);
  }

  const closeButton = document.createElement('button');
  closeButton.classList.add('close-button');
  closeButton.textContent = 'x';
  closeButton.onclick = () => {
    clearEventInputs(); // 입력 폼 닫기
    clearSelectedHighlights(); // 선택된 하이라이트 초기화
  };

  // 일정 입력
  const eventNameInput = document.createElement('input');
  eventNameInput.type = 'text';
  eventNameInput.classList.add('eventname-input');
  eventNameInput.placeholder = '일정 이름';
  if (existingEvent) eventNameInput.value = existingEvent.sname; // 기존 일정의 이름을 입력 필드에 설정

  const eventContentInput = document.createElement('input');
  eventContentInput.type = 'text';
  eventContentInput.classList.add('event-input');
  eventContentInput.placeholder = '일정 내용';
  if (existingEvent) eventContentInput.value = existingEvent.scontents; // 기존 일정의 내용을 입력 필드에 설정

  const startDateInput = document.createElement('input');
  startDateInput.type = 'date';
  startDateInput.classList.add('date-input');
  startDateInput.placeholder = '시작일';
  if (existingEvent) startDateInput.value = existingEvent.startdate.split('T')[0]; // 기존 일정의 시작일을 입력 필드에 설정

  const endDateInput = document.createElement('input');
  endDateInput.type = 'date';
  endDateInput.classList.add('date-input');
  endDateInput.placeholder = '종료일';
  if (existingEvent) endDateInput.value = existingEvent.enddate.split('T')[0]; // 기존 일정의 종료일을 입력 필드에 설정

  const statusSelect = document.createElement('select');
  statusSelect.classList.add('status-select');
  ['진행 중', '완료'].forEach(status => {
    const option = document.createElement('option');
    option.value = status;
    option.textContent = status;
    statusSelect.appendChild(option);
  });
  if (existingEvent) statusSelect.value = existingEvent.sprocess; // 기존 이벤트의 상태를 선택 필드에 설정

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
  let selectedColor = existingEvent && existingEvent.scolor ? existingEvent.scolor : 'sky blue'; // 기본 색상 파란색

  // 입력 요소들을 입력필드에 추가
  inputContainer.append(
      closeButton,
      eventNameInput,
      eventContentInput,
      startDateInput,
      endDateInput,
      statusSelect,
      colorPalette
  );

  // Append the input container to the body
  document.body.appendChild(inputContainer);
  eventNameInput.focus();

  // 입력란과 색상 선택에 Enter 키 이벤트 추가
  const inputs = [eventNameInput, eventContentInput, startDateInput, endDateInput, statusSelect];
  inputs.forEach(input => {
    input.addEventListener('keypress', function (event) {
      if (event.key === 'Enter') {
        event.preventDefault();
        saveEventIfAllFieldsFilled();
      }
    });
  });

  // 색상 선택 시 Enter 키 이벤트 추가
  colorPalette.addEventListener('click', function (event) {
    if (event.target.classList.contains('color-option')) {
      selectedColor = event.target.classList[1];
      saveEventIfAllFieldsFilled();
    }
  });

  // 모든 필드가 채워져 있는지 확인하고 이벤트를 저장하는 함수
  function saveEventIfAllFieldsFilled() {
    if (
        eventNameInput.value.trim() !== '' &&
        eventContentInput.value.trim() !== '' &&
        startDateInput.value.trim() !== '' &&
        endDateInput.value.trim() !== '' &&
        statusSelect.value.trim() !== ''
    ) {
      saveEvent(
          eventNameInput.value,
          eventContentInput.value,
          startDateInput.value,
          endDateInput.value,
          statusSelect.value,
          selectedColor,
          dateElement,
          eventInfo,
          existingEvent ? existingEvent.sid : null
      );
      clearEventInputs();
      dateElement.focus();
    } else {
      alert('모든 입력란을 채워주세요.');
    }
  }

  // 입력 형식
  const boundingRect = dateElement.getBoundingClientRect();
  inputContainer.style.top = `${boundingRect.top + dateElement.clientHeight}px`;
  inputContainer.style.left = `${boundingRect.left}px`;
  inputContainer.style.maxHeight = `calc(90vh - ${boundingRect.top + dateElement.clientHeight}px - 10px)`;

  document.body.appendChild(inputContainer);
  eventNameInput.focus();
}

// 이벤트를 수정/삭제하는 함수
function saveEvent(name, content, startDate, endDate, status, color, dateElement, eventInfo = null, eventId = null) {
  const truncatedContent = content.length > 100 ? content.slice(0, 100) + '...' : content; // 입력 길이 제한

  if (!eventInfo) {
    eventInfo = document.createElement('div');
    eventInfo.classList.add('event-info');
    eventInfo.classList.add(color); // 색상 클래스 추가
    eventInfo.innerHTML = `<strong class="event-button">${name}</strong><br class="event-content">${truncatedContent}
    <span class="event-start-date" style="display: none;">${startDate}</span>
    <span class="event-end-date" style="display: none;">${endDate}</span>
    <span class="event-status" style="display: none;">${status}</span>`;

    const actionButtonsContainer = document.createElement('div');
    actionButtonsContainer.classList.add('action-buttons-container');

    const editButton = document.createElement('button');
    editButton.classList.add('edit-button');
    editButton.textContent = '수정';
    editButton.onclick = function () {
      showEventInput(dateElement, null, {
        sid: eventId,
        sname: name,
        scontents: content,
        startdate: startDate,
        enddate: endDate,
        sprocess: status,
        scolor: color
      }, eventInfo, true);
    };

    const deleteButton = document.createElement('button');
    deleteButton.classList.add('delete-button');
    deleteButton.textContent = '삭제';
    deleteButton.onclick = function () {
      deleteEvent(eventId, eventInfo);
    };

    // 수정한 입력 요소 칸에 삽입
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

    // 마우스 오버 이벤트 추가
    addHoverEvent(eventInfo, {
      sname: name,
      scontents: content,
      startdate: startDate,
      enddate: endDate,
      sprocess: status,
      scolor: color
    });
  } else {
    // 기존 이벤트가 있는 경우, 기존 이벤트 정보를 업데이트
    eventInfo.querySelector('.event-button').textContent = name;
    eventInfo.querySelector('.event-button + br').nextSibling.nodeValue = truncatedContent;
    eventInfo.querySelector('.event-start-date').textContent = startDate;
    eventInfo.querySelector('.event-end-date').textContent = endDate;
    eventInfo.querySelector('.event-status').textContent = status;
    eventInfo.classList.remove(...eventInfo.classList); // 기존 클래스 제거
    eventInfo.classList.add('event-info', color); // 새로운 색상 클래스 추가

    // 마우스 오버 이벤트 추가
    addHoverEvent(eventInfo, {
      sname: name,
      scontents: content,
      startdate: startDate,
      enddate: endDate,
      sprocess: status,
      scolor: color
    });

    const actionButtonsContainer = eventInfo.querySelector('.action-buttons-container');
    if (!actionButtonsContainer) {
      const newActionButtonsContainer = document.createElement('div');
      newActionButtonsContainer.classList.add('action-buttons-container');

      const editButton = document.createElement('button');
      editButton.classList.add('edit-button');
      editButton.textContent = '수정';
      editButton.onclick = function () {
        showEventInput(dateElement, null, {
          sid: eventId,
          sname: name,
          scontents: content,
          startdate: startDate,
          enddate: endDate,
          sprocess: status,
          scolor: color
        }, eventInfo, true);
      };

      const deleteButton = document.createElement('button');
      deleteButton.classList.add('delete-button');
      deleteButton.textContent = '삭제';
      deleteButton.onclick = function () {
        deleteEvent(eventId, eventInfo);
      };

      newActionButtonsContainer.append(editButton, deleteButton);
      eventInfo.appendChild(newActionButtonsContainer);

      const boundingRect = eventInfo.getBoundingClientRect();
      newActionButtonsContainer.style.top = `${boundingRect.top}px`;
      newActionButtonsContainer.style.left = `${boundingRect.right + 10}px`;
    }
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

  // 이벤트 데이터를 서버로 전송하기 위한 fetch 함수
  const url = eventId ? `/api/schedules/${eventId}` : '/schedule/personal';
  const method = eventId ? 'PUT' : 'POST'; // method는 eventId가 있으면 'PUT' (업데이트), 없으면 'POST' (저장)

  fetch(url, {
    method: method, // 설정된 method 사용
    headers: {
      'Content-Type': 'application/json' // 요청의 데이터 형식을 JSON으로 설정
    },
    body: JSON.stringify(eventData) // eventData 객체를 JSON 문자열로 변환하여 요청 본문에 포함
  })
      .then(response => response.json()) // 서버로부터 응답받은 데이터를 JSON 형식으로 변환
      .then(data => {
        if (method === 'POST') {
          eventId = data.sid; // 서버로부터 반환된 sid를 eventId에 저장
          eventInfo.dataset.sid = eventId; // eventInfo 객체의 dataset에 sid 저장
        }
        console.log('Event saved successfully with sid:', eventId); // 이벤트 저장 성공 메시지를 콘솔에 출력

        if (method === 'PUT') {
          fetch(`/api/schedules/${eventId}`) // 업데이트된 이벤트 정보를 다시 서버로부터 가져오기 위해 fetch 호출
              .then(response => response.json()) // 응답을 JSON 형식으로 변환
              .then(updatedEvent => {
                // 가져온 업데이트된 이벤트 정보를 화면에 반영
                eventInfo.querySelector('.event-button').textContent = updatedEvent.sname; // 이벤트 이름 업데이트
                eventInfo.querySelector('.event-button + br').nextSibling.nodeValue = updatedEvent.scontents; // 이벤트 내용 업데이트
                eventInfo.style.backgroundColor = updatedEvent.scolor; // 이벤트 배경색 업데이트
              });
        }
      })
      .catch(error => {
        console.error('Network error:', error); // 네트워크 오류 발생 시 오류 메시지를 콘솔에 출력
      });

  // 이벤트 삭제를 위한 fetch 함수
  function deleteEvent(eventId, eventInfo) {
    if (eventId) {
      fetch(`/api/schedules/${eventId}`, {
        method: 'DELETE', // 삭제 요청을 위해 HTTP method를 'DELETE'로 설정
      })
          .then(response => {
            if (response.ok) {
              console.log('Event deleted successfully'); // 이벤트 삭제 성공 메시지를 콘솔에 출력
              eventInfo.remove(); // 화면에서 해당 이벤트 정보를 제거
            } else {
              console.error('Error deleting event'); // 삭제 실패 시 오류 메시지를 콘솔에 출력
            }
          })
          .catch(error => {
            console.error('Network error:', error); // 네트워크 오류 발생 시 오류 메시지를 콘솔에 출력
          });
    } else {
      eventInfo.remove(); // eventId가 없을 경우 화면에서 해당 이벤트 정보를 바로 제거
    }
  }
}

// 이벤트 입력 요소들을 지우는 함수
function clearEventInputs() {
  const inputContainers = document.querySelectorAll('.input-container');
  inputContainers.forEach(container => {
    container.remove();
  });
}

// db에서 사용자 불러오는 함수
// 데이터가 배열인지 확인 후 forEach 실행해서 객체로 전환
// 추후에 팀 일정 불러올때도 비슷하게 작성
function fetchUserSchedules() {
  fetch('/api/schedules/assignee')
      .then(response => {
        // 응답을 텍스트로 받아서 콘솔에 출력
        return response.text().then(text => {
          console.log('Raw response:', text); // 원시 응답 텍스트 출력
          // JSON 파싱 시도
          try {
            return JSON.parse(text);
          } catch (error) {
            throw new Error('Invalid JSON: ' + text);
          }
        });
      })
      .then(data => {
        console.log('Fetched schedules:', data); // 불러온 일정 데이터 확인
        if (Array.isArray(data)) {
          data.forEach(schedule => {
            const dateElement = document.querySelector(`.calendar-date[data-date="${schedule.startdate.split('T')[0]}"]`);
            if (dateElement) {
              saveEvent(schedule.sname, schedule.scontents, schedule.startdate, schedule.enddate, schedule.sprocess, schedule.scolor, dateElement, null, schedule.sid);
            }
          });
        } else {
          console.error('Data is not an array:', data); // 데이터가 배열이 아닌 경우 오류 메시지 출력
        }
      })
      .catch(error => {
        console.error('Error fetching schedules:', error); // 수정된 부분: 응답이 JSON 형식이 아닌 경우 오류 메시지 출력
      });
}

// // 페이지 로드 시 사용자 일정 불러오기
// fetchUserSchedules();

// 기존 일정에 마우스 오버 이벤트 추가
function addHoverEvent(eventInfo, eventData) {
  eventInfo.addEventListener('mouseenter', (e) => {
    showEventDetails(e, eventData);
  });
  eventInfo.addEventListener('mouseleave', clearEventDetails);
}

// 일정 정보를 보여주는 함수
function showEventDetails(event, eventData) {
  clearEventDetails();

  const detailsContainer = document.createElement('div');
  detailsContainer.classList.add('event-details-container', 'show');

  // 닫기 버튼 추가
  const closeButton = document.createElement('button');
  closeButton.classList.add('close-button');
  closeButton.textContent = 'x';
  closeButton.onclick = () => {
    clearEventDetails();
  };

  detailsContainer.innerHTML = `
    <h3>일정</h3>
    <hr class="details-line">
    <p>일정명: ${eventData.sname || ''}</p>
    <p>일정 내용: ${eventData.scontents || ''}</p>
    <p>시작일: ${eventData.startdate || ''}</p>
    <p>종료일: ${eventData.enddate || ''}</p>
    <p>상태: ${eventData.sprocess || ''}</p>
  `;

  // 닫기 버튼을 detailsContainer에 추가
  detailsContainer.appendChild(closeButton);

  const boundingRect = event.target.getBoundingClientRect();
  detailsContainer.style.top = `${boundingRect.top + window.scrollY + event.target.clientHeight}px`;
  detailsContainer.style.left = `${boundingRect.left + window.scrollX}px`;

  document.body.appendChild(detailsContainer);
}

// 일정 디테일을 제거하는 함수. 일정명 빼고 다 날림
function clearEventDetails() {
  const detailsContainers = document.querySelectorAll('.event-details-container');
  detailsContainers.forEach(container => {
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