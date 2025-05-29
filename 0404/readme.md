# 🧮 GhatGptNumber - 계산기 앱

세련된 UI와 정밀한 기능을 갖춘 안드로이드 계산기 앱입니다.  
숫자 입력, 소수점 처리, 사칙연산, 나머지(%) 연산까지 지원되며 깔끔한 디자인의 버튼 UI가 특징입니다.

---

## 📱 앱 주요 화면

<img src="screenshots/screenshot1.png" width="300"/>

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🔢 숫자 입력 | 0~9 숫자 및 `.` 소수점 처리 |
| ➕➖✖️➗ 연산 | 더하기, 빼기, 곱하기, 나누기, 나머지 연산 |
| 🧼 초기화 | AC 버튼으로 모든 입력 초기화 |
| ⌫ 삭제 | DEL 버튼으로 마지막 숫자 삭제 |
| 🟰 결과 계산 | = 버튼으로 계산 결과 출력 |

---

## 🎨 UI 구성 (`activity_main.xml`)

- `LinearLayout` + `GridLayout` 기반 버튼 배치
- 4열 × 5행 그리드로 숫자 및 연산자 정렬
- 텍스트뷰(`display`)로 결과 출력
- 모든 버튼에 `rounded_button.xml` 배경 적용

```xml
<TextView android:id="@+id/display" android:text="0" />

<GridLayout android:columnCount="4">
    <Button android:id="@+id/btn7" android:text="7" />
    <Button android:id="@+id/btn8" android:text="8" />
    ...
</GridLayout>
🧠 핵심 로직 요약 (MainActivity.java)
java
복사
편집
btnEqual.setOnClickListener(v -> {
    double secondNumber = Double.parseDouble(currentInput);
    double result = switch (operator) {
        case "+" -> firstNumber + secondNumber;
        case "-" -> firstNumber - secondNumber;
        case "*" -> firstNumber * secondNumber;
        case "/" -> firstNumber / secondNumber;
        case "%" -> firstNumber % secondNumber;
        default -> 0;
    };
    display.setText(String.valueOf(result));
});
🎨 버튼 스타일 (rounded_button.xml)
xml
복사
편집
<shape>
    <solid android:color="#6C63FF" />
    <corners android:radius="18dp" />
</shape>
버튼 배경은 부드러운 보라색

radius=18dp로 깔끔한 라운드 처리

📁 프로젝트 구조
pgsql
복사
편집
GhatGptNumber/
├── MainActivity.java
├── activity_main.xml
├── rounded_button.xml
├── styles.xml
├── /screenshots/screenshot1.png
├── readme.md
🚀 확장 아이디어
괄호 연산, 지수, 제곱근 등 공학용 확장

계산 히스토리 저장 기능

버튼 애니메이션/효과 추가

Jetpack Compose 버전 구현


