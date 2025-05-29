# ✅ ToDoApp  
> 🗓️ 일정 관리, 우선순위 설정, 마감일 알림까지 가능한 미니멀한 투두리스트 앱

---

## 🧾 프로젝트 개요

ToDoApp은 **할 일(Task) 추가, 편집, 삭제, 완료 체크**를  
지원하는 안드로이드 기반의 투두리스트 앱입니다.  

SQLite DB에 데이터를 저장하여 앱을 껐다 켜도 유지되며,  
`RecyclerView`를 활용해 성능과 사용자 경험을 모두 고려했습니다.

---

## 🧩 핵심 기능

| 기능             | 설명                                                                    |
|------------------|-------------------------------------------------------------------------|
| ➕ 작업 추가       | Add 버튼 클릭 시 TaskDialog 팝업을 통해 새로운 작업을 등록               |
| ✅ 완료 체크       | 할 일 항목을 클릭하면 완료 여부를 토글                                 |
| 🗑️ 삭제 기능       | 완료된 작업을 롱클릭하여 삭제                                           |
| ⏰ 마감일 설정     | DatePicker + TimePicker로 마감일 지정 가능                              |
| 🎯 우선순위 선택   | Spinner로 선택 (높음, 중간, 낮음)                                       |
| 📦 SQLite 저장     | 앱 데이터는 SQLite에 저장되며, 앱 종료 후에도 유지됨                   |

---

## 🧠 주요 구조 설명

### `MainActivity.java`

```java
// DB 초기화 및 데이터 불러오기
dbHelper = new TaskDatabaseHelper(this);
taskList = dbHelper.getAllTasks();

// RecyclerView 설정
adapter = new TaskAdapter(taskList, this);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(adapter);

// FloatingActionButton 클릭 시 다이얼로그 실행
fab.setOnClickListener(v -> {
    AddTaskDialog dialog = new AddTaskDialog(this, this);
    dialog.show();
});
AddTaskDialog.java
// 다이얼로그 뷰 구성
EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
TextView textViewDueDate = dialogView.findViewById(R.id.textViewDueDate);
Spinner spinnerPriority = dialogView.findViewById(R.id.spinnerPriority);

// 마감일 선택 다이얼로그 연결
textViewDueDate.setOnClickListener(v -> {
    showDatePicker(); // → DatePicker + TimePicker 조합
});
📂 프로젝트 구성도
ToDoApp/
├── MainActivity.java              # 메인 화면, 전체 리스트 및 FAB
├── AddTaskDialog.java            # 작업 추가를 위한 커스텀 다이얼로그
├── Task.java                     # Task 모델 클래스
├── TaskAdapter.java              # RecyclerView 어댑터
├── TaskDatabaseHelper.java       # SQLite DB 관리
├── activity_main.xml             # 메인 UI 레이아웃
├── dialog_add_task.xml           # 다이얼로그 레이아웃
├── item_task.xml                 # 각 할 일 항목의 레이아웃
└── AndroidManifest.xml
💡 기술 스택
요소	내용
UI 구성	RecyclerView + CardLayout
DB 저장	SQLite + Helper Class
다이얼로그 처리	AlertDialog + 커스텀 뷰
날짜/시간 선택	DatePickerDialog, TimePickerDialog

🧠 SWOT 분석
✅ Strengths
📅 마감일 + 우선순위 설정 → 일정 관리 기능 탁월

💾 SQLite 기반 → 로컬 저장으로 지속성 확보

📱 간단한 구조 → 학습 및 커스터마이징 용이

⚠️ Weaknesses
📲 알림 기능 없음 (푸시 알람 등 미지원)

🔍 필터/검색 기능 미구현

🌱 Opportunities
🔔 알림 기능 연동으로 실시간 리마인더 가능

☁ Firebase 연동으로 클라우드 저장 가능

📊 통계 보기(완료율, 우선순위별 비율 등) 확장 가능

🚫 Threats
📉 대형 투두 앱 대비 기능 부족

📵 데이터 백업 미지원 시 사용자 불안 요소

✨ 확장 아이디어
Google Calendar 연동

알림(Notification) 기능 추가

태그 시스템 추가 및 필터링

다크 모드 대응 UI


