# 🔄 intentEx - 액티비티 전환 연습 앱

이 앱은 Android에서 **Intent를 통해 액티비티를 전환**하는 기본 구조를 학습하기 위한 실습 프로젝트입니다.  
첫 번째 화면(MainActivity)에서 버튼을 누르면 두 번째 화면(SecondActivity)으로 이동하며,  
두 번째 화면에서 버튼을 누르면 다시 첫 번째 화면으로 돌아오는 흐름입니다.

---

## 📱 화면 구조

| 화면            | 주요 구성                                                  |
|------------------|-----------------------------------------------------------|
| MainActivity     | 텍스트 + "액티비티 열기" 버튼 → `SecondActivity`로 이동     |
| SecondActivity   | "닫기" 버튼 → 현재 액티비티 종료 (`finish()` → 이전 화면 복귀) |

---

## 🧩 주요 기능

| 기능                  | 설명                                                  |
|-----------------------|-------------------------------------------------------|
| 🔁 액티비티 전환         | `startActivity()` 메서드로 화면 전환 구현               |
| ❌ 현재 액티비티 종료     | `finish()` 호출하여 이전 액티비티로 복귀                  |
| 📱 심플한 UI 구성        | LinearLayout 기반의 간단한 버튼 및 텍스트 구성             |

---

## 🛠️ 핵심 코드 요약

### `MainActivity.java`

```java
btnOpenSecond.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
});
SecondActivity.java
btnClose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();  // 현재 액티비티 종료 → MainActivity로 복귀
    }
});
🎨 XML 레이아웃
activity_main.xml
<LinearLayout>
    <TextView android:text="여기는 액티비티1입니다." />
    <Button android:id="@+id/btnOpenSecond" android:text="이미지 표시 액티비티 열기" />
</LinearLayout>
activity_second.xml
<LinearLayout>
    <TextView android:text="두 번째 액티비티입니다." />
    <Button android:id="@+id/btnClose" android:text="닫기" />
</LinearLayout>
📁 프로젝트 구조
intentEx/
├── MainActivity.java
├── SecondActivity.java
├── activity_main.xml
├── activity_second.xml
├── readme.md (본 문서)
🧠 SWOT 분석
✅ Strengths (강점)
가장 기본적인 Intent 화면 전환 실습이 가능

두 액티비티 간 이동 및 복귀 흐름을 직접 경험

매우 간단한 UI/코드로 구조 이해에 집중할 수 있음

⚠️ Weaknesses (약점)
데이터 전달(Intent extras) 기능이 없음

화면 간 애니메이션 효과나 UI 다양성이 부족

Back 버튼으로도 동일한 기능 가능 → 학습용에 특화됨

💡 Opportunities (기회)
Intent.putExtra() 등을 추가해 데이터 전달 기능 확장 가능

화면 전환 시 애니메이션 추가, 프래그먼트 구조로 전환 연습 가능

실전 앱에서 자주 쓰이는 구조로 발전시킬 수 있음

🚫 Threats (위협)
너무 간단한 구조로 실사용 앱엔 적용 어려움

코드 흐름만 익히고 실전 확장하지 않으면 학습 효과 제한적

🔧 확장 아이디어
데이터 전달 (intent.putExtra → getIntent().getXxxExtra)

ThirdActivity 추가하여 다중 화면 전환 구성

startActivityForResult() 구조까지 확장

버튼 클릭 시 애니메이션 적용 (overridePendingTransition())
