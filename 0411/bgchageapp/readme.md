# 🎨 배경색 변경 앱 (BgChangeApp)

이미지 영역의 배경색을 버튼 클릭으로 자유롭게 변경할 수 있는 **Android UI 실습용 앱**입니다.  
`FrameLayout` 내에 이미지가 배치되어 있고, 버튼을 누르면 배경색이 실시간으로 변경됩니다.

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🖼️ 이미지 표시 | 이미지(`img_boy`)를 `FrameLayout` 안에 표시 |
| 🔘 버튼 클릭 | 각 버튼을 클릭하면 배경색이 변경됨 |
| 🎨 색상 전환 | 연녹색, 하늘색, 살구색, 노랑 등 지정 색상 적용

---

## 🖥️ UI 구성 요약

```xml
<FrameLayout android:id="@+id/imageContainer">
    <ImageView android:src="@drawable/img_boy" />
</FrameLayout>

<Button android:id="@+id/btnColor1" />
<Button android:id="@+id/btnColor2" />
<Button android:id="@+id/btnColor3" />
<Button android:id="@+id/btnColor4" />
버튼은 세로 또는 가로 정렬 가능 (LinearLayout)

배경은 코드에서 setBackgroundColor()로 지정됨

🛠️ 주요 코드 (MainActivity.java)
java
복사
편집
btnColor1.setOnClickListener(v -> {
    imageContainer.setBackgroundColor(Color.parseColor("#A5D6A7")); // 연녹색
});

btnColor2.setOnClickListener(v -> {
    imageContainer.setBackgroundColor(Color.parseColor("#90CAF9")); // 하늘색
});

btnColor3.setOnClickListener(v -> {
    imageContainer.setBackgroundColor(Color.parseColor("#FFCC80")); // 살구색
});

btnColor4.setOnClickListener(v -> {
    imageContainer.setBackgroundColor(Color.parseColor("#FFF176")); // 노란색
});
📁 프로젝트 구조
bash
복사
편집
BgChangeApp/
├── MainActivity.java           # 버튼 클릭 이벤트 처리
├── activity_main.xml           # UI 정의 (이미지 + 버튼)
├── res/drawable/img_boy.png    # 중앙에 표시되는 이미지 (예시)
├── readme.md                   # 설명 문서 (본 파일)
💡 확장 아이디어
🎨 사용자 색상 선택 (컬러 피커 다이얼로그)

🔄 배경 이미지 전환 기능 추가

💾 선택한 배경색 저장 (SharedPreferences)

🌙 다크모드 배경 색 대응

