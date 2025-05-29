# ☰ OptionMenuApp - 옵션 메뉴로 배경색 바꾸기

이 앱은 Android의 **옵션 메뉴(Option Menu)** 를 활용해  
사용자가 선택한 항목에 따라 배경색을 **파랑 / 초록 / 빨강**으로 변경하는 간단한 앱입니다.  
ActionBar의 메뉴 시스템을 학습하기에 좋은 예제입니다.

---

## 🧩 핵심 기능 요약

| 기능                  | 설명                                                          |
|-----------------------|---------------------------------------------------------------|
| ☰ 옵션 메뉴 표시       | ActionBar의 메뉴 버튼을 통해 메뉴 생성                        |
| 🎨 배경색 변경         | 메뉴 항목 선택 시 레이아웃의 배경색을 실시간으로 변경          |
| 📄 XML 기반 메뉴 구성 | `res/menu/mymenu.xml` 파일에서 메뉴 항목 정의                 |

---

## 📱 UI 구성 (activity_main.xml)

```xml
<ConstraintLayout>
    <LinearLayout
        android:id="@+id/layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal" />
</ConstraintLayout>
📂 메뉴 정의 (mymenu.xml)
<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">

    <item
        android:id="@+id/blue"
        android:title="파랑색"
        android:icon="@android:drawable/btn_star_big_on"
        app:showAsAction="never" />

    <item
        android:id="@+id/green"
        android:title="초록색"
        android:icon="@android:drawable/ic_btn_speak_now"
        app:showAsAction="never" />

    <item
        android:id="@+id/red"
        android:title="빨강색"
        android:icon="@android:drawable/checkbox_on_background"
        app:showAsAction="never" />
</menu>
🔧 핵심 로직 (MainActivity.java)
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.mymenu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.blue) {
        view1.setBackgroundColor(Color.BLUE);
        return true;
    } else if (id == R.id.green) {
        view1.setBackgroundColor(Color.GREEN);
        return true;
    } else if (id == R.id.red) {
        view1.setBackgroundColor(Color.RED);
        return true;
    }
    return super.onOptionsItemSelected(item);
}
📁 프로젝트 구조
OptionMenu/
├── MainActivity.java
├── activity_main.xml
├── mymenu.xml
└── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
Android 옵션 메뉴 사용법을 가장 간단하게 체험 가능

XML 리소스를 활용한 메뉴 구성으로 유지보수 용이

실시간 UI 변화 (색상 변경)로 즉각적 피드백

⚠️ Weaknesses (약점)
메뉴 항목이 고정적으로 제한됨 (동적 추가 미지원)

레이아웃 외 요소 변경은 없음 (텍스트, 폰트 등 비포함)

💡 Opportunities (기회)
다국어 메뉴 번역, 메뉴 항목 조건부 표시 등 기능 확장 가능

메뉴 클릭 시 Dialog, Toast, Activity 이동 등으로 응용 가능

🚫 Threats (위협)
옵션 메뉴는 ActionBar가 없는 앱에서는 보이지 않을 수 있음

향후 Toolbar, PopupMenu 등으로 대체될 가능성 존재

🌱 발전 방향
다이내믹 메뉴 생성: Java 코드로 항목 동적으로 추가

배경색 외에 폰트 크기 / 정렬 등 사용자 설정 기능 추가

메뉴 클릭 시 Dialog로 사용자 확인 받기
