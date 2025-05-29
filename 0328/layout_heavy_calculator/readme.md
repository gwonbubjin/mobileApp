# 🧮 Layout Heavy Calculator

숫자 버튼, 연산자 버튼, 점(.) 등 다양한 키패드를 포함한 안드로이드 계산기 앱입니다.  
UI 구성이 풍부하며, 사용자의 입력을 실시간으로 텍스트뷰에 반영합니다.

---

## 📱 앱 개요

> 이 앱은 "레이아웃이 무거운 계산기"라는 이름처럼 풍부한 버튼 구성과 완성도 있는 UI를 제공합니다.

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🔢 숫자 입력 | 0~9까지 숫자 버튼과 `.` 소수점 입력 |
| ➕➖✖️➗ 연산자 버튼 | `+`, `-`, `x`, `/` 등의 연산 기호 입력 |
| 🧼 입력 초기화 | `C` 버튼을 눌러 전체 초기화 |
| ⌫ 한 글자 삭제 | `DEL` 버튼으로 마지막 입력 제거 |
| 🟰 결과 계산 | `=` 버튼으로 결과 출력 처리 예정(로직 추가 가능)

---

## 🛠️ 핵심 코드 요약

### `MainActivity.java`

```java
// 숫자 및 소수점 버튼
int[] numberIds = { R.id.btn0, R.id.btn1, ..., R.id.btnDot };

for (int id : numberIds) {
    findViewById(id).setOnClickListener(v -> {
        Button b = (Button) v;
        input += b.getText().toString();
        display.setText(input);
    });
}

// C 버튼
findViewById(R.id.btnClear).setOnClickListener(v -> {
    input = "";
    display.setText("0");
});

// DEL 버튼
findViewById(R.id.btnDel).setOnClickListener(v -> {
    if (!input.isEmpty()) {
        input = input.substring(0, input.length() - 1);
        display.setText(input.isEmpty() ? "0" : input);
    }
});
🎨 UI 구성 (activity_main.xml)
xml
복사
편집
<TextView android:id="@+id/display" android:text="0" />
<TableLayout>
  <TableRow>
    <Button android:id="@+id/btnDivide" android:text="/" />
    <Button android:id="@+id/btnMultiply" android:text="x" />
    ...
  </TableRow>
  <!-- 숫자, 연산자, 삭제 등 다양한 버튼 구성 -->
</TableLayout>
LinearLayout + TableLayout 조합으로 키패드 구성

숫자 및 연산자 버튼 전체 배치

결과 출력창은 상단 TextView로 구현

📁 프로젝트 구조
bash
복사
편집
LayoutHeavyCalculator/
├── MainActivity.java         # 입력 로직 및 버튼 핸들링
├── activity_main.xml         # 계산기 키패드 UI
├── readme.md                 # 설명 문서 (본 파일)
🚀 확장 아이디어
연산자 우선순위 및 실시간 계산 기능 추가

수식 파싱을 통한 정밀 계산 로직 구현

이전 계산 기록 히스토리 기능

디자인 개선: Material 3 스타일 적용
