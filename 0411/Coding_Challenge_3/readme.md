# 🚀 Coding Challenge 3 - 다중 화면 전환 앱 (Intro/Start/Settings)

이 앱은 Android의 **다중 액티비티 전환 구조**를 학습하는 실습용 프로젝트입니다.  
메인 화면에서 "INTRODUCTION", "START", "SETTINGS" 세 가지 버튼을 통해 각기 다른 화면으로 이동할 수 있으며,  
각 액티비티는 독립적인 XML 레이아웃을 통해 구성됩니다.

---

## 📱 앱 구조

- **MainActivity**  
  └ 세 개의 버튼을 통해 다른 액티비티로 전환

- **IntroActivity**  
  └ 앱 소개 화면

- **StartActivity**  
  └ 앱 실행/메인 컨텐츠 화면

- **SettingsActivity**  
  └ 설정 관련 화면 (예: 사용자 설정, 테마 등)

---

## 🧩 주요 기능

| 기능             | 설명                                                       |
|------------------|------------------------------------------------------------|
| ▶️ 화면 전환      | 버튼 클릭 시 각각의 액티비티로 전환 (`startActivity`)         |
| 🖼️ 배경 이미지    | 메인 화면 배경에 전면 이미지 (`main_bg`) 삽입                 |
| 📁 레이아웃 분리   | 각 액티비티별로 독립된 XML 레이아웃 구성 (`activity_*.xml`) |

---

## 🛠️ 핵심 코드 요약

### `MainActivity.java`

```java
findViewById(R.id.btnIntro).setOnClickListener(v -> {
    startActivity(new Intent(MainActivity.this, IntroActivity.class));
});

findViewById(R.id.btnStart).setOnClickListener(v -> {
    startActivity(new Intent(MainActivity.this, StartActivity.class));
});

findViewById(R.id.btnSettings).setOnClickListener(v -> {
    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
});
🎨 메인화면 레이아웃 (activity_main.xml)
xml
복사
편집
<RelativeLayout>
    <ImageView android:src="@drawable/main_bg" android:scaleType="centerCrop" />
    
    <LinearLayout android:orientation="vertical" android:layout_centerInParent="true">
        <Button android:id="@+id/btnIntro" android:text="INTRODUCTION" />
        <Button android:id="@+id/btnStart" android:text="START" />
        <Button android:id="@+id/btnSettings" android:text="SETTINGS" />
    </LinearLayout>
</RelativeLayout>
배경 이미지 전체 채움 (centerCrop)

버튼들은 세로 정렬 후 화면 중앙 정렬

📁 프로젝트 구조
pgsql
복사
편집
CodingChallenge3/
├── MainActivity.java
├── IntroActivity.java
├── StartActivity.java
├── SettingsActivity.java
├── activity_main.xml
├── activity_intro.xml
├── activity_start.xml
├── activity_settings.xml
├── res/drawable/main_bg.png (예시)
├── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
액티비티 전환 구조의 기본 원리 체득 가능

XML 레이아웃을 화면별로 독립 구성, 구조적 이해 용이

앱 설계 시 가장 많이 사용되는 메뉴 구조 실습용으로 매우 적합

⚠️ Weaknesses (약점)
각 액티비티 내용이 비어있어 기능적인 콘텐츠는 없음

액티비티 간 데이터 전달(Intent extras 등) 미구현

화면 전환 외에는 인터랙션이나 이벤트 처리 없음

💡 Opportunities (기회)
각각의 액티비티에 실제 콘텐츠를 구성하며 기능 확장 가능

버튼 클릭 시 애니메이션 추가 / 데이터 전달 연습 등으로 고도화 가능

로그인 → 메인 → 설정 → 종료 플로우 구축 연습 가능

🚫 Threats (위협)
너무 단순하여 실서비스에는 적합하지 않음

구성만 있고 기능이 없으면 금방 한계에 도달할 수 있음

한 화면에서 View 전환(프래그먼트 등)으로도 구현 가능한 구조임

🚀 확장 아이디어
각 액티비티에 실제 컨텐츠 추가 (예: 프로필, 설정 옵션, 앱 소개 등)

액티비티 간 데이터 전달 (Intent.putExtra)

Jetpack Navigation Component 활용해 화면 전환 재구성

BottomNavigationView 또는 DrawerMenu로 화면 전환 방식 변경
