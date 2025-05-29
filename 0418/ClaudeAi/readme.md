# 🎨 ClaudeAi - 배경색 변경 컨텍스트 메뉴 앱

이 앱은 사용자가 **TextView를 길게 눌렀을 때 Context Menu를 띄우고**,  
선택한 메뉴 항목에 따라 앱의 배경색이 동적으로 변경되는 안드로이드 실습 프로젝트입니다.

---

## 📱 기능 개요

| 기능                           | 설명                                                    |
|--------------------------------|-----------------------------------------------------------|
| 🟰 컨텍스트 메뉴 생성          | TextView에 길게 클릭 시 팝업 메뉴 생성                   |
| 🎨 배경색 변경 기능            | 빨강, 초록, 파랑 등의 배경색으로 즉시 전환               |
| 🖼 UI 요소 연동                | 메뉴 선택 → RelativeLayout 배경색 변경                    |

---

## 🧠 핵심 코드 요약

### `MainActivity.java`

```java
// 텍스트뷰에 컨텍스트 메뉴 등록
registerForContextMenu(textView);

// 메뉴 생성
@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.context_menu, menu);
}

// 메뉴 항목 선택 처리
@Override
public boolean onContextItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
        case R.id.menu_red:
            mainLayout.setBackgroundColor(Color.RED);
            return true;
        case R.id.menu_green:
            mainLayout.setBackgroundColor(Color.GREEN);
            return true;
        case R.id.menu_blue:
            mainLayout.setBackgroundColor(Color.BLUE);
            return true;
    }
    return super.onContextItemSelected(item);
}
🖼 레이아웃 구성
activity_main.xml
<RelativeLayout android:id="@+id/main_layout">
    <TextView
        android:id="@+id/text_view"
        android:text="길게 눌러서 배경색 변경하기"
        android:layout_centerInParent="true"
        android:padding="16dp" />
</RelativeLayout>
context_menu.xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/menu_red" android:title="Red" />
    <item android:id="@+id/menu_green" android:title="Green" />
    <item android:id="@+id/menu_blue" android:title="Blue" />
</menu>
📁 폴더 구조
ClaudeAi/
├── MainActivity.java
├── activity_main.xml
├── context_menu.xml
└── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
안드로이드 컨텍스트 메뉴(ContextMenu)의 전형적인 구조 구현

이벤트 등록과 처리 흐름이 명확함

UI와 로직의 상호작용을 직관적으로 배울 수 있음

⚠️ Weaknesses (약점)
기능이 단일 목적(색상 변경)으로 제한적임

메뉴 아이템 외 추가 동작 없음 (예: 상태 저장 X)

💡 Opportunities (기회)
다른 위젯에 context menu 확장 가능

색상 설정을 저장하여 앱 재시작 후에도 유지 가능 (SharedPreferences)

Long Click 외에 Popup Menu, Dialog Menu 등으로 확장

🚫 Threats (위협)
실전 앱 기능으로는 활용성 낮음

다양한 상황에서 사용할 수 있는 메뉴는 아님

🔧 확장 아이디어
배경색 변경 외에 텍스트 크기 조정 메뉴 추가

사용자 선택 값을 SharedPreferences에 저장해 앱 재실행 시 반영

AppCompat 기반 다크모드 스타일 대응
