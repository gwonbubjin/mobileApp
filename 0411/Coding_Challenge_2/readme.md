# ✅ Coding Challenge 2 - 할 일 추가 앱 (To-Do List)

EditText로 할 일을 입력하고, "추가" 버튼을 누르면  
리스트에 체크박스로 추가되는 간단한 **To-Do 목록 관리 앱**입니다.

직관적인 UI와 단순한 구조로 Android 초보자가 이벤트 처리와 동적 뷰 생성에 익숙해질 수 있도록 설계되었습니다.

---

## 📱 화면 구성

> 기본 UI:
- 할 일 입력창 (`EditText`)
- 추가 버튼 (`Button`)
- 입력된 할 일을 체크박스로 출력 (`LinearLayout`에 동적 추가)

---

## 🧩 주요 기능

| 기능               | 설명                                                                 |
|--------------------|----------------------------------------------------------------------|
| 📝 할 일 입력         | EditText에 할 일을 입력한 후 추가 버튼을 누름                         |
| 📋 할 일 추가         | `LinearLayout`에 `CheckBox` 형태로 동적으로 항목 추가됨               |
| ✅ 체크 완료 표시      | 각 항목을 체크해 완료 여부 표시 가능 (체크박스로 구현됨)               |
| 🧼 비어 있는 입력 방지 | 공백 입력 시 추가되지 않음 (`trim()` 처리로 공백 제거)                  |

---

## 🛠️ 주요 코드 요약 (`MainActivity.java`)

```java
btnAdd.setOnClickListener(v -> {
    String task = editTask.getText().toString().trim();
    if (!task.isEmpty()) {
        CheckBox checkBox = new CheckBox(MainActivity.this);
        checkBox.setText(task);
        taskList.addView(checkBox);
        editTask.setText("");
    }
});
LinearLayout taskList에 체크박스를 동적으로 추가

CheckBox는 개별 완료 여부 표시용

🎨 UI 구성 (XML)
xml

<EditText android:id="@+id/editTask" android:hint="할일 추가" />
<Button android:id="@+id/btnAdd" android:text="추가" />
<LinearLayout android:id="@+id/taskList" android:orientation="vertical" />
수직 LinearLayout 기반 구조

보라색 버튼 배경(backgroundTint="#7e57c2")

각 체크박스는 할 일 추가 시 자동 생성됨

📁 프로젝트 구조

CodingChallenge2/
├── MainActivity.java           # 동적 체크박스 추가 및 입력처리 로직
├── activity_main.xml           # 입력창, 버튼, 리스트 UI 구성
├── readme.md                   # 설명 문서 (본 파일)
🧠 SWOT 분석
✅ Strengths (강점)
단순하고 명확한 로직으로 Android 초보자에게 적합

동적 UI 생성 및 CheckBox 활용을 직접 실습 가능

할 일 입력과 시각적 확인(체크) 기능을 한 번에 익힘

⚠️ Weaknesses (약점)
입력 항목 저장 기능이 없어 앱 종료 시 데이터가 유지되지 않음

리스트 삭제 기능이나 정렬 기능이 없음

긴 할 일 텍스트에 대한 UI 대응(줄바꿈 등) 부족

💡 Opportunities (기회)
SharedPreferences나 SQLite를 활용한 저장 기능 추가 가능

완료된 항목 숨기기, 삭제 버튼, 중요도 표시 등 기능 확장 여지 많음

디자인 개선 시 포트폴리오용 미니 앱으로 활용 가능

🚫 Threats (위협)
기능이 단순해 실사용 앱으로는 부족함

너무 간단한 구조로 학습 목표를 넘어서기 어렵다면 금방 한계 도달

다양한 화면 크기 대응 및 다크모드 미지원

🚀 확장 아이디어
✔ 완료된 항목 삭제 기능 (CheckBox.isChecked() 활용)

📌 중요 표시 및 색상 강조 기능

💾 앱 종료 후에도 데이터 유지 (SharedPreferences or Room DB)

📱 Jetpack Compose로 재구성


