# ✅ Coding Challenge 1 - Android 버전 선택 이미지 출력 앱

사용자가 안드로이드 버전 중 하나를 **라디오 버튼으로 선택**하고,  
"표시" 버튼을 누르면 **해당 버전에 맞는 이미지가 화면에 출력**되는 간단한 Android 실습 앱입니다.

이 앱은 초보자를 위한 UI 및 이벤트 처리 학습에 적합하며, RadioGroup과 ImageView 조합을 직관적으로 익힐 수 있도록 설계되었습니다.

---

## 📱 화면 구성

> 기본 UI:
- 안내 텍스트: "현재 사용 중인 안드로이드 버전은?"
- 버전 선택 라디오 버튼: 2.3.3 / 4.0 / 5.0
- 이미지 출력 버튼
- 이미지 영역 (기본은 숨김)

---

## 🧩 주요 기능

| 기능             | 설명                                                                 |
|------------------|----------------------------------------------------------------------|
| 🔘 버전 선택      | RadioButton으로 안드로이드 버전 중 하나를 선택                       |
| ✅ 이미지 출력    | 버튼 클릭 시 선택한 버전에 대응하는 이미지 출력 (visibility 조절 포함) |
| 🧼 초기 상태 유지 | 앱 실행 시 ImageView는 `invisible` 상태, 버튼 클릭 시 `visible` 전환  |

---

## 🛠️ 핵심 코드 요약

```java
btnDisplay.setOnClickListener(v -> {
    int selectedId = radioGroup.getCheckedRadioButtonId();

    if (selectedId == R.id.radioVersion1) {
        imageView.setImageResource(R.drawable.android23);
    } else if (selectedId == R.id.radioVersion2) {
        imageView.setImageResource(R.drawable.android40);
    } else if (selectedId == R.id.radioVersion3) {
        imageView.setImageResource(R.drawable.android50);
    }

    imageView.setVisibility(View.VISIBLE);
});

🎨 UI 구성 (XML)
xml
복사
편집
<RadioGroup android:id="@+id/radioGroup">
    <RadioButton android:id="@+id/radioVersion1" android:text="2.3.3"/>
    <RadioButton android:id="@+id/radioVersion2" android:text="4.0"/>
    <RadioButton android:id="@+id/radioVersion3" android:text="5.0"/>
</RadioGroup>

<Button android:id="@+id/btnDisplay" android:text="표시"/>
<ImageView android:id="@+id/imageView" android:visibility="invisible"/>
전체 LinearLayout 수직 배치

ImageView는 초기엔 안 보이도록 설정

🧠 SWOT 분석
✅ Strengths (강점)
구조가 매우 간단해 초보자가 UI와 이벤트 처리를 빠르게 익히기 좋음

RadioGroup과 ImageView의 기본 동작 원리 체득 가능

불필요한 코드 없이 핵심 기능에 집중되어 있음

⚠️ Weaknesses (약점)
이미지 외에 텍스트 정보나 애니메이션 효과가 없음

정답 피드백이나 입력 확인 로직이 없어 기능적 확장성 부족

정적 리소스 기반이라 확장 시 유지보수가 어려울 수 있음

💡 Opportunities (기회)
선택값 기반 설명 출력, Toast, AlertDialog 등 기능 추가로 확장 가능

네트워크 이미지 적용, 버전별 특징 소개 기능 연결 가능

UI 디자인 고도화 → 앱 전환 연습용으로 사용 가능

🚫 Threats (위협)
너무 간단한 구조로 인해 실제 앱 개발에는 한계

실습 목적 외에는 활용도가 낮고, 사용자 유지율이 낮음

다양한 화면 사이즈나 다크모드 대응이 되어있지 않음

📁 프로젝트 구조
bash
복사
편집
CodingChallenge1/
├── MainActivity.java           # 라디오 버튼과 이미지 출력 처리
├── activity_main.xml           # 전체 UI 구성
├── res/drawable/               # android23.png, android40.png, android50.png
├── readme.md                   # 본 설명 문서

🚀 확장 아이디어
이미지와 함께 해당 버전에 대한 설명 TextView 출력

앱 실행 시 랜덤 선택 유도 → 사용자의 추측 맞히기 게임화

사용자가 추가한 이미지 업로드 기능 추가 (Storage 연결)

