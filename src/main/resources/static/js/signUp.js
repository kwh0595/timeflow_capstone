// script.js
document.addEventListener("DOMContentLoaded", function () {
    const yearSelect = document.getElementById("year");
    const monthSelect = document.getElementById("month");
    const daySelect = document.getElementById("day");

    const currentYear = new Date().getFullYear();
    const startYear = currentYear - 100; // 100년 전부터 시작
    const endYear = currentYear;

    // 연도 추가
    for (let year = endYear; year >= startYear; year--) {
        const option = document.createElement("option");
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }

    // 월 추가
    for (let month = 1; month <= 12; month++) {
        const option = document.createElement("option");
        option.value = month;
        option.textContent = month;
        monthSelect.appendChild(option);
    }

    // 일 추가 함수
    function populateDays(month) {
        daySelect.innerHTML = ""; // 기존 날짜 제거
        const daysInMonth = new Date(currentYear, month, 0).getDate();

        for (let day = 1; day <= daysInMonth; day++) {
            const option = document.createElement("option");
            option.value = day;
            option.textContent = day;
            daySelect.appendChild(option);
        }
    }

    // 초기 일자 채우기
    populateDays(monthSelect.value);

    // 월이 바뀔 때마다 일자 갱신
    monthSelect.addEventListener("change", function () {
        populateDays(monthSelect.value);
    });
});
