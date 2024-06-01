const calendarDates = document.getElementById("calendarDates");
const currentMonthElement = document.getElementById("currentMonth");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const today = new Date(); // 현재 날짜를 나타내는 Date 객체를 저장한다.
let currentMonth = today.getMonth();
/* 현재 월을 나타내는 값을 저장한다. getMonth() 메서드는 0부터 시작하는 월을 반환하므로
1월이면 0, 2월이면 1을 반환한다. */
let currentYear = today.getFullYear(); // 변수에 현재 연도를 나타내는 값을 저장한다.
function renderCalendar() {
  /* renderCalendar 함수는 월별 캘랜더를 생성하고 표시하는 함수이다. */
  const firstDayOfMonth = new Date(currentYear, currentMonth, 1);
  /* firstDayOfMonth 변수에 현재 월의 첫 번째 날짜를 나타내는 Date 객체를 저장한다.
해당 월의 첫 번째 날짜에 대한 정보를 얻는다. */
  const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
  /* daysInMonth 변수에 현재 월의 총 일 수를 나타내는 값을 저장한다. 
  해당 월이 몇 일까지 있는지 알 수 있다. */
  const startDayOfWeek = firstDayOfMonth.getDay();
  /* 변수에 현재 월의 첫 번째 날짜의 요일을 나타내는 값을 저장한다.
  해당 월의 첫 번째 날짜가 무슨 요일인지 알 수 있다. */
  currentMonthElement.textContent = `${currentYear}년 ${currentMonth + 1}월`;
  // 월을 나타내는 요소에 현재 월과 연도를 설정하여 표시한다.
  calendarDates.innerHTML = " "; // 일자를 표시하는 그리드 컨테이너를 비운다.
  // 빈 날짜(이전 달)
  for (let i = 0; i < startDayOfWeek; i++) {
    const emptyDate = document.createElement("div");
    //  빈 날짜를 나타내는 div 요소를 생성한다.
    emptyDate.classList.add("date", "empty");
    // 생성한 div 요소에 "date"와 "empty" 클래스를 추가한다.
    calendarDates.appendChild(emptyDate);
    // 생성한 빈 날짜 요소를 캘린더 그리드에 추가한다.
  }

// 이전 달의 마지막 일부 날짜 채우기
  const prevMonthDays = new Date(currentYear, currentMonth, 0).getDate();
  for (let i = startDayOfWeek - 1; i >= 0; i--) {
    const dateElement = document.createElement("div");
    dateElement.classList.add("date", "prev-month");
    dateElement.textContent = prevMonthDays - i;
    calendarDates.appendChild(dateElement);
  }

  // 현재 달의 날짜
  for (let i = 1; i <= daysInMonth; i++) {
    const dateElement = document.createElement("div");
    dateElement.classList.add("date");
    dateElement.textContent = i;
    calendarDates.appendChild(dateElement);
  }
  // 다음 달의 빈 날짜 채우기 (총 칸수를 35개로 맞추기)
  const totalDatesElements = startDayOfWeek + daysInMonth;
  const nextMonthEmptyDays = totalDatesElements > 35 ? 0 : 35 - totalDatesElements;
  for (let i = 0; i < nextMonthEmptyDays; i++) {
    const dateElement = document.createElement("div");
    dateElement.classList.add("date", "next-month");
    dateElement.textContent = i + 1;
    calendarDates.appendChild(dateElement);
  }
  /* 
  1. for 문을 이용하여 현재 월의 총 일 수만큼 반복하여 월의 날짜를 순서대로 표시한다.
  2. const dateElement = document.createElement("div");를 통해 날짜를 나타내는 div 요소를 생성한다.
  3. dateElement.classList.add("date");를 통해 생성한 div 요소에 "date" 클래스를 추가한다.
  4. dateElement.textContent = i;를 통해 해당 날짜 값을 div 요소의 텍스트로 설정한다.
  5. calendarDates.appendChild(dateElement);를 통해 생성한 날짜 요소를 캘린더 그리드에 추가한다.
  */
}
renderCalendar();
// 페이지가 로드되면 renderCalendar 함수를 실행하여 초기 캘린더를 표시한다.
prevBtn.addEventListener("click", () => {
  currentMonth--;
  if (currentMonth < 0) {
    currentMonth = 11;
    currentYear--;
  }
  renderCalendar();
});
/* 
1. 이전 버튼(prevBtn)을 클릭하면 현재 월을 이전 월로 변경하고, 연도가 바뀌어야 한다면 연도를 변경한다.
2. 변경된 월과 연도를 바탕으로 renderCalendar 함수를 호출하여 이전 월의 캘린더를 표시한다.
*/
nextBtn.addEventListener("click", () => {
  currentMonth++;
  if (currentMonth > 11) {
    currentMonth = 0;
    currentYear++;
  }
  renderCalendar();
});

calendarDates.addEventListener("mouseover", (event) => {
  if (event.target.classList.contains("date")) {
    event.target.classList.add("highlight");
  }
});

calendarDates.addEventListener("mouseout", (event) => {
  if (event.target.classList.contains("date")) {
    event.target.classList.remove("highlight");
  }
});

function renderCalendar() {
  const firstDayOfMonth = new Date(currentYear, currentMonth, 1);
  const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
  const startDayOfWeek = firstDayOfMonth.getDay();
  currentMonthElement.textContent = `${currentYear}년 ${currentMonth + 1}월`;
  calendarDates.innerHTML = ""; // 일자를 표시하는 그리드 컨테이너를 비운다.

  // 이전 달의 마지막 일부 날짜 채우기
  const prevMonthDays = new Date(currentYear, currentMonth, 0).getDate();
  for (let i = startDayOfWeek; i > 0; i--) {
    const dateElement = document.createElement("div");
    dateElement.classList.add("date", "prev-month");
    dateElement.textContent = prevMonthDays - i + 1;
    calendarDates.appendChild(dateElement);
  }

  // 현재 달의 날짜
  for (let i = 1; i <= daysInMonth; i++) {
    const dateElement = document.createElement("div");
    dateElement.classList.add("date");
    dateElement.textContent = i;
    calendarDates.appendChild(dateElement);
  }

}
/* 
1. 다음 버튼(nextBtn)을 클릭하면 현재 월을 다음 월로 변경하고, 연도가 바뀌어야 한다면 연도를 변경한다.
2. 변경된 월과 연도를 바탕으로 renderCalendar 함수를 호출하여 다음 월의 캘린더를 표시한다.

*/
