# 🔢 Number Guessing Game App

1부터 100 사이의 숫자 중에서 컴퓨터가 랜덤으로 선택한 숫자를 맞추는 **심플한 숫자 추측 게임**입니다.  
텍스트 입력창에 숫자를 입력하고 버튼을 클릭하면 결과 메시지가 출력됩니다.

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🎲 랜덤 숫자 생성 | 앱 시작 시 1~100 사이 무작위 숫자 생성 |
| 🔤 사용자 입력 | `EditText`에 사용자가 숫자 입력 |
| 📣 결과 출력 | 입력값이 정답보다 크거나 작을 경우 힌트 제공 |
| ✅ 정답 | 정확히 일치 시 “정답입니다!” 메시지 표시 |

---

## 🖥️ 핵심 UI 구성

```xml
<LinearLayout orientation="vertical">
    <ImageView android:id="@+id/bannerImage" />
    <TextView android:text="컴퓨터가 정한 1~100 숫자를 맞춰보세요." />
    <EditText android:id="@+id/inputGuess" />
    <Button android:id="@+id/btnGuess" android:text="Guess" />
    <TextView android:id="@+id/resultText" />
</LinearLayout>
중앙 정렬, 적절한 여백(padding)

배너 이미지 삽입 가능 (@drawable/bannerImage)

🛠️ 주요 로직 (MainActivity.java)
java
복사
편집
answer = new Random().nextInt(100) + 1;

btnGuess.setOnClickListener(v -> {
    int guess = Integer.parseInt(inputGuess.getText().toString());

    if (guess == answer) {
        resultText.setText("정답입니다!");
    } else if (guess > answer) {
        resultText.setText("너무 큽니다.");
    } else {
        resultText.setText("너무 작습니다.");
    }
});
앱 실행 시 정답 숫자를 1회만 생성

버튼 클릭 시 입력값과 비교하여 피드백 제공

📁 프로젝트 구조
bash
복사
편집
NumberGuess/
├── MainActivity.java         # 숫자 비교 및 결과 처리 로직
├── activity_main.xml         # UI 구성 요소 정의
├── res/drawable/bannerImage  # 배너 이미지 (강아지 등)
├── readme.md                 # 설명 문서 (본 파일)
🚀 확장 아이디어
🔁 정답 맞춘 후 “다시 시작” 기능 추가

📊 시도 횟수 카운트 및 기록 저장

🎨 정답 시 애니메이션/사운드 효과 적용

📱 Jetpack Compose로 UI 재구성
