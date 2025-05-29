# 🎨 ContextMenuApp - 텍스트 길게 누르면 배경색 변경

이 앱은 사용자가 **TextView를 롱클릭하면 컨텍스트 메뉴(Context Menu)** 를 띄우고,  
메뉴에서 선택한 항목에 따라 **전체 배경색이 변경**되는 Android 앱입니다.

---

## 📱 핵심 기능 요약

| 기능                          | 설명                                                       |
|-------------------------------|------------------------------------------------------------|
| 🟰 컨텍스트 메뉴 동적 생성    | XML이 아닌 자바 코드로 메뉴 항목을 생성                     |
| 🎨 배경색 실시간 변경         | 빨강, 초록, 파랑 중 하나 선택 시 전체 레이아웃 색 변경      |
| 🖱 롱클릭 이벤트 적용         | TextView 길게 누르면 `onCreateContextMenu()` 호출됨         |

---

## 🛠 주요 코드 요약

### `MainActivity.java`

```java
text = (TextView) findViewById(R.id.Textview01);
registerForContextMenu(text); // 텍스트뷰에 컨텍스트 메뉴 등록

@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    menu.setHeaderTitle("컨텐스트메뉴");
    menu.add(0, 1, 0, "배경색 : RED");
    menu.add(0, 2, 0, "배경색 : GREEN");
    menu.add(0, 3, 0, "배경색 : BLUE");
}

@Override
public boolean onContextItemSelected(MenuItem item) {
    LinearLayout layout = findViewById(R.id.LinearLayout01);
    switch (item.getItemId()) {
        case 1:
            layout.setBackgroundColor(Color.RED);
            return true;
        case 2:
            layout.setBackgroundColor(Color.GREEN);
            return true;
        case 3:
            layout.setBackgroundColor(Color.BLUE);
            return true;
    }
    return false;
}
🧱 레이아웃 구조
<LinearLayout
    android:id="@+id/LinearLayout01"
    android:orientation="vertical">

    <TextView
        android:id="@+id/Textview01"
        android:text="Only I can change my life. No one can do it for me."
        android:textSize="200px"
        android:typeface="serif" />
</LinearLayout>
📁 프로젝트 구조
ContextMenuApp/
├── MainActivity.java
├── activity_main.xml
├── themes.xml
└── readme.md (본 문서)
🧠 SWOT 분석
✅ Strengths (강점)
컨텍스트 메뉴를 Java 코드로 직접 생성하는 방식 학습 가능

Android UI 이벤트 처리의 흐름(등록 → 생성 → 선택 처리)을 명확히 익힐 수 있음

⚠️ Weaknesses (약점)
메뉴 항목이 하드코딩되어 유지보수에 불리

기능이 단순한 배경색 변경에 국한됨

💡 Opportunities (기회)
PopupMenu, AlertDialog 등 다른 메뉴 방식으로의 확장 가능

여러 View에 대해 각각의 메뉴 항목 다르게 적용 가능

색상 선택 외에도 폰트 변경, 정렬, 크기 조정 등의 옵션 추가로 기능 강화 가능

🚫 Threats (위협)
UI 요소가 하나(TextView)에만 적용되어 유연성이 낮음

색상 선택만 가능한 구조로 사용자 입장에선 기능 부족

🔧 발전 방향
메뉴 항목을 menu.xml 리소스로 분리하여 코드 간결화

SharedPreferences를 사용해 사용자가 선택한 색상 유지

선택 가능한 색상 테마를 이미지 또는 버튼 등으로 대체


