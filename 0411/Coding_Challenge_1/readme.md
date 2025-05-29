# ✅ Coding Challenge 1 - Android 버전 선택 앱

사용자가 라디오 버튼을 통해 안드로이드 버전을 선택하고,  
선택 후 버튼을 클릭하면 해당 버전에 맞는 이미지가 화면에 표시되는 간단한 실습 앱입니다.

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🔘 버전 선택 | `RadioButton`을 통해 안드로이드 버전 선택 (예: 2.3.3, 4.0, 5.0) |
| 📸 이미지 출력 | [표시] 버튼 클릭 시 `ImageView`에 해당 이미지 출력 |
| 🧼 초기 상태 | 앱 시작 시 이미지 숨김 상태 유지

---

## 🖥️ UI 구성 요약 (`activity_main.xml`)

```xml
<TextView android:text="현재 사용중인 안드로이드 버전은?" />

<RadioGroup android:id="@+id/radioGroup">
    <RadioButton android:id="@+id/radioVersion1" android:text="2.3.3" />
    <RadioButton android:id="@+id/radioVersion2" android:text="4.0" />
    <RadioButton android:id="@+id/radioVersion3" android:text="5.0" />
</RadioGroup>

<Button android:id="@+id/btnDisplay" android:text="표시" />
<ImageView android:id="@+id/imageView" android:visibility="invisible" />
수직 LinearLayout 기반

배경색은 흰색 (#ffffff)

선택 전에는 ImageView가 보이지 않음

🛠️ 주요 로직 (MainActivity.java)
java

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
📁 프로젝트 구조

CodingChallenge1/
├── MainActivity.java           # 버튼 클릭 및 이미지 설정 로직
├── activity_main.xml           # 라디오 버튼 + 버튼 + 이미지 UI
├── res/drawable/               # android23, android40, android50 이미지 리소스
├── readme.md                   # 설명 문서 (본 파일)
💡 확장 아이디어
라디오 버튼 대신 Spinner로 구현해보기

선택 시 Toast 메시지도 함께 출력

Glide 등 라이브러리 활용해 네트워크 이미지 연동
