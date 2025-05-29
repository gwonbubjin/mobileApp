# 📱 CalculatingMachine

간단하고 직관적인 안드로이드 계산기 앱입니다. 두 숫자를 입력한 후 덧셈, 뺄셈, 곱셈, 나눗셈 버튼을 눌러 결과를 확인할 수 있습니다.

---

## 📌 프로젝트 개요

- **앱 이름**: CalculatingMachine
- **개발환경**: Android Studio (Java 기반)
- **대상 OS**: Android
- **UI 구성**: XML 레이아웃
- **핵심 기능**: 사칙연산 (덧셈, 뺄셈, 곱셈, 나눗셈)

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| ➕ 덧셈 | 두 수를 더한 결과를 출력 |
| ➖ 뺄셈 | 두 수를 뺀 결과를 출력 |
| ✖ 곱셈 | 두 수를 곱한 결과를 출력 |
| ➗ 나눗셈 | 두 수를 나눈 결과를 출력 |

---

## 🧠 SWOT 분석

### ✅ Strength (강점)
- 매우 간단한 구조로 누구나 이해 가능
- 깔끔한 UI로 직관적인 사용
- Java와 Android 기본 문법 학습용으로 적합

### ⚠ Weakness (약점)
- 음수, 소수, 0 나누기 예외처리 없음
- 디자인이 단조로움
- 결과값 정밀도 부족 (정수 연산만 처리)

### 💡 Opportunity (기회)
- 예외처리 및 고급 UI 적용으로 확장 가능
- 기능 추가: 공학용 계산기, 메모리 저장 등
- Kotlin 또는 Jetpack Compose 기반으로 전환 가능

### 🚫 Threat (위협)
- 이미 수많은 계산기 앱 존재
- 기능이 단순하여 차별점 부족

---

## 🛠️ 구현 방식

- **UI**: `activity_main.xml`을 통해 LinearLayout으로 입력 필드와 버튼 구성
- **로직**: `MainActivity.java`에서 각 버튼 클릭 이벤트를 처리하여 결과 계산

### 코드 주요 구조

```java
bt1.setOnClickListener(v -> calculate('+'));
bt2.setOnClickListener(v -> calculate('-'));
bt3.setOnClickListener(v -> calculate('*'));
bt4.setOnClickListener(v -> calculate('/'));
java
복사
편집
private void calculate(char op) {
    int num1 = Integer.parseInt(edit1.getText().toString());
    int num2 = Integer.parseInt(edit2.getText().toString());
    int result = switch (op) {
        case '+' -> num1 + num2;
        case '-' -> num1 - num2;
        case '*' -> num1 * num2;
        case '/' -> num1 / num2;
        default -> 0;
    };
    edit3.setText(String.valueOf(result));
}
📂 폴더 구성
bash
복사
편집
CalculatingMachine/
├── MainActivity.java         # 메인 로직 파일
├── activity_main.xml         # UI 레이아웃
├── readme.md                 # 설명 문서 (바로 이 파일!)
