# 🧮 Coding Challenge 3 - Calculator App

안드로이드 기반의 심플한 계산기 앱입니다.  
두 숫자를 입력한 뒤 버튼을 클릭하면 덧셈, 뺄셈, 곱셈, 나눗셈 결과를 확인할 수 있습니다.

---

## 📱 앱 미리보기

> **기본 UI 구성**
- 입력 필드 2개 (숫자 입력용)
- 결과 출력 텍스트뷰 1개
- 연산 버튼 4개: ➕ ➖ ✖️ ➗

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| ➕ 덧셈 | 입력한 두 숫자를 더해 출력 |
| ➖ 뺄셈 | 첫 번째 숫자에서 두 번째 숫자를 빼서 출력 |
| ✖️ 곱셈 | 두 수를 곱한 결과 출력 |
| ➗ 나눗셈 | 첫 번째 숫자를 두 번째 숫자로 나눈 결과 출력 (예외처리 포함) |

---

## 🛠️ 핵심 코드 요약

### `MainActivity.java`

```java
btnAdd.setOnClickListener(listener);
btnSubtract.setOnClickListener(listener);
btnMultiply.setOnClickListener(listener);
btnDivide.setOnClickListener(listener);

View.OnClickListener listener = v -> {
    double num1 = Double.parseDouble(number1.getText().toString());
    double num2 = Double.parseDouble(number2.getText().toString());
    double output = switch (v.getId()) {
        case R.id.btnAdd -> num1 + num2;
        case R.id.btnSubtract -> num1 - num2;
        case R.id.btnMultiply -> num1 * num2;
        case R.id.btnDivide -> num1 / num2;
        default -> 0;
    };
    result.setText("결과: " + output);
};
🎨 UI 구성 (activity_main.xml)
xml
복사
편집
<EditText android:id="@+id/number1" />
<EditText android:id="@+id/number2" />
<Button android:id="@+id/btnAdd" android:text="+" />
<Button android:id="@+id/btnSubtract" android:text="-" />
<Button android:id="@+id/btnMultiply" android:text="*" />
<Button android:id="@+id/btnDivide" android:text="/" />
<TextView android:id="@+id/result" />
LinearLayout을 이용한 수직 정렬

상단에 초록색 배경 TextView로 앱 제목 "Calculator" 표시

여백(padding, margin)과 정렬(gravity)이 잘 구성됨

💡 학습 포인트
EditText를 통한 숫자 입력 처리

View.OnClickListener 하나로 여러 버튼 처리하는 방식

switch-case 구조로 이벤트 처리 간결화

기본적인 예외처리 흐름 학습 가능

📁 프로젝트 구조
bash
복사
편집
Coding_Challenge3/
├── MainActivity.java         # 계산 로직 처리
├── activity_main.xml         # UI 정의
├── readme.md                 # 설명 문서 (본 파일)
🔭 확장 아이디어
입력값 유효성 검사 (빈 입력, 숫자 이외의 값 등)

결과값 포맷 조정 (소수점 자리 수 제한)

연산 이력 저장 기능 추가

디자인 개선 (Material Design 적용)
