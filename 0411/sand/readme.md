# 🥪 Sand App - 샌드위치 재료 선택 앱

이 앱은 사용자가 **샌드위치 재료를 선택(체크)** 하면 해당 재료의 이미지를 보여주는 간단한 Android 실습 앱입니다.  
CheckBox를 활용한 사용자 입력과, 이미지 뷰(View)의 `setVisibility()`를 통해 동적으로 UI가 변하는 로직을 학습할 수 있습니다.

---

## 📱 기능 소개

| 기능                     | 설명                                                 |
|--------------------------|------------------------------------------------------|
| ✅ 재료 선택             | "meat", "cheese" 체크박스를 통해 재료 선택 가능       |
| 🖼 재료 이미지 출력       | 체크된 재료에 해당하는 이미지가 화면에 동적으로 표시됨 |
| ❌ 체크 해제 시 숨김      | 선택 해제하면 이미지가 다시 숨겨짐                   |

---

## 🛠 주요 코드 요약

### `MainActivity.java`

```java
checkMeat.setOnClickListener(v -> {
    if (checkMeat.isChecked()) {
        imageMeat.setVisibility(View.VISIBLE);
    } else {
        imageMeat.setVisibility(View.INVISIBLE);
    }
});
checkCheese.setOnClickListener(v -> {
    if (checkCheese.isChecked()) {
        imageCheese.setVisibility(View.VISIBLE);
    } else {
        imageCheese.setVisibility(View.INVISIBLE);
    }
});
🎨 레이아웃 구조 (activity_main.xml)
<LinearLayout orientation="vertical">
    <TextView android:text="샌드위치 선택" />
    <LinearLayout orientation="horizontal">
        <CheckBox android:id="@+id/checkMeat" android:text="meat" />
        <CheckBox android:id="@+id/checkCheese" android:text="cheese" />
    </LinearLayout>
    <ImageView android:id="@+id/imageMeat" android:visibility="invisible" />
    <ImageView android:id="@+id/imageCheese" android:visibility="invisible" />
</LinearLayout>
📁 파일 구조
sand/
├── MainActivity.java
├── activity_main.xml
├── (이미지 리소스: meat.png, cheese.png 등)
└── readme.md (본 문서)
🧠 SWOT 분석
✅ Strengths (강점)
입력 UI 요소와 뷰 요소의 상호작용 학습 가능

단순하면서도 동적 동작을 연습하기 좋은 구조

이미지 처리, 이벤트 리스너 개념 숙달에 유용

⚠️ Weaknesses (약점)
기능이 매우 제한적 (데이터 저장 X, 여러 재료 확장성 부족)

UI가 정적이고 반응형이나 애니메이션 요소 없음

💡 Opportunities (기회)
재료 목록을 RecyclerView로 확장하여 유연한 구성 가능

재료 조합 저장, 즐겨찾기 기능 추가로 발전 가능

Glide나 Picasso로 외부 이미지 로딩 연습 가능

🚫 Threats (위협)
실전 앱으로 사용되기엔 단순한 구조

이미지 파일이 없을 경우 앱 오류 발생 위험

🔧 확장 아이디어
선택된 재료 리스트를 Toast 또는 TextView로 출력

CheckBox → Switch 또는 ToggleButton으로 전환

앱 종료 시 선택 상태 저장/복원 기능 추가 (SharedPreferences)


