# 📝 Dark Notepad App  
> 📒 어두운 테마 기반의 간편 메모장, 로컬 파일에 저장되는 나만의 기록 도구

---

## 📌 프로젝트 소개

Dark Notepad는 **로컬 저장 기반 메모장 앱**입니다.  
간단한 UI, 자동 저장 기능, 텍스트 편집기를 갖춘 메모 앱으로  
개발자 공부용 또는 개인 기록용으로 적합합니다.

- ✍ 메모 추가/수정/삭제 가능
- 💾 메모는 내부 저장소에 `.txt` 파일로 저장
- 🌙 다크 테마 느낌의 기본 레이아웃

---

## 🔧 기능 요약

| 기능             | 설명                                                        |
|------------------|-------------------------------------------------------------|
| 📄 메모 추가      | FAB 버튼 클릭 → 메모 편집 화면 진입                         |
| ✏️ 메모 수정      | 메모 카드 클릭 시 제목/내용 편집 가능                       |
| 🧹 전체 로드       | 액티비티 재진입 시 메모 전체 재로드 (`onResume`) 처리      |
| 💾 파일 저장      | `FileStorageHelper` 통해 title.txt 형식으로 내부 저장소 저장 |
| 🗑 메모 삭제 메뉴 | 카드 롱클릭 시 삭제 옵션 제공 (context menu or popup)       |

---

## 🧠 앱 구조

```text
MainActivity
 ├─ note_card.xml 로 각 메모를 카드로 구성
 └─ NoteEditorActivity로 이동 (제목, 내용 입력)
📄 주요 코드 요약
MainActivity.java
// + 버튼 누르면 새 메모 편집기로 이동
findViewById(R.id.fabAdd).setOnClickListener(v -> {
    startActivity(new Intent(this, NoteEditorActivity.class));
});

// onResume 때 모든 메모 다시 로드
protected void onResume() {
    super.onResume();
    loadNotes(); // → FileStorageHelper 통해 불러오기
}
NoteEditorActivity.java
String title = getIntent().getStringExtra("title");
String content = getIntent().getStringExtra("content");

if (title != null) etTitle.setText(title);
if (content != null) editNote.setText(content);

findViewById(R.id.btnSave).setOnClickListener(v -> {
    FileStorageHelper.saveNote(this, etTitle.getText().toString(), editNote.getText().toString());
    finish();
});
🗃 파일 구조
DarkNotepadApp/
├── MainActivity.java
├── NoteEditorActivity.java
├── FileStorageHelper.java
├── Note.java
├── layout/
│   ├── activity_main.xml
│   ├── activity_note_editor.xml
│   ├── note_card.xml
├── res/
│   └── menu/note_card_menu.xml
└── AndroidManifest.xml
🧠 SWOT 분석
✅ Strengths
💾 오프라인 사용 가능 → 내부 저장소 기반

🧩 단순 구조 → 학습용으로 좋음

🌓 다크 테마 + 카드 UI로 직관적

⚠️ Weaknesses
📤 클라우드 백업/연동 기능 없음

🔄 리사이클러뷰 대신 LinearLayout 수동 관리 → 성능 저하 가능

🌱 Opportunities
☁ Firebase/Google Drive 연동으로 백업 기능 추가 가능

🔐 비밀번호 잠금 기능으로 보안 강화 가능

🔔 알림 메모 등 기능 확장 여지 많음

🚫 Threats
📱 Android 최신 정책에서 파일 접근 제한 위험

📦 앱 데이터 삭제 시 모든 메모 소실

🛠 기술 스택
구성 요소	사용 기술
🧱 UI 구성	ConstraintLayout + CardLayout
💾 데이터 저장	내부 저장소 (파일 기반)
🧭 Activity 전환	Intent 활용

✨ 개선 아이디어
메모 리스트를 RecyclerView로 리팩토링

삭제/수정 기능을 ContextMenu로 고도화

테마 선택 기능 (라이트/다크 전환)


