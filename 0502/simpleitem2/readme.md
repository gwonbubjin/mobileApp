# 📋 SimpleItemApp  
> 📱 기본적인 리스트뷰를 활용한 연락처 리스트 표시 앱

---

## 🧩 프로젝트 개요

이 앱은 Android의 `ListView`와 `SimpleAdapter`를 활용해,  
간단한 **이름 + 전화번호 형태의 연락처 목록**을 리스트 형태로 보여주는 데모입니다.

- ✅ 이름과 번호를 갖는 아이템 리스트 구현
- ✅ `SimpleAdapter`를 사용한 UI 바인딩
- ✅ 재사용 가능한 데이터 구조 기반 설계

---

## ✨ 핵심 기능

| 기능                     | 설명                                                |
|--------------------------|-----------------------------------------------------|
| 📜 리스트뷰 구성         | 이름과 번호를 포함한 아이템을 화면에 나열           |
| 🧠 SimpleAdapter 사용     | Key-Value 데이터 맵핑으로 커스텀 UI 없이 간단 구현   |
| 📁 데이터 관리            | `HashMap<String, String>` 리스트로 구조화된 데이터 |

---

## 📄 주요 코드 요약

### 📌 `MainActivity.java`

```java
ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

HashMap<String, String> map1 = new HashMap<>();
map1.put("title", "홍길동");
map1.put("subtitle", "010-1234-5678");
dataList.add(map1);

HashMap<String, String> map2 = new HashMap<>();
map2.put("title", "임꺽정");
map2.put("subtitle", "010-2345-6789");
dataList.add(map2);

SimpleAdapter adapter = new SimpleAdapter(
    this,
    dataList,
    android.R.layout.simple_list_item_2,
    new String[] { "title", "subtitle" },
    new int[] { android.R.id.text1, android.R.id.text2 }
);
listView.setAdapter(adapter);
🖼 XML 레이아웃
activity_main.xml
<ConstraintLayout>
    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</ConstraintLayout>
🗂️ 프로젝트 구조
SimpleItemApp/
├── MainActivity.java
├── activity_main.xml
└── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
🔧 Android 기본 UI 컴포넌트만으로 구성 → 매우 가볍고 빠름

📘 Adapter 구성법의 좋은 예시로 활용 가능

🧱 데이터 구조가 단순하여 확장성 있음

⚠️ Weaknesses (약점)
💄 커스터마이징이 어려운 기본 simple_list_item_2 사용

🧑‍🎨 디자인 요소가 거의 없음 (사용자 경험 제한)

💡 Opportunities (기회)
✨ 커스텀 레이아웃 적용 → BaseAdapter or RecyclerView 확장 가능

🔍 클릭 이벤트, 전화걸기, 삭제 등 다양한 기능 연동 가능

🚫 Threats (위협)
🧾 아이템 수 증가 시 성능 한계 (ListView 한계)

📲 최신 앱 설계에선 RecyclerView가 더 권장됨

🚀 확장 아이디어
전화번호 클릭 시 다이얼 연결

커스텀 리스트뷰 아이템으로 UI 개선

데이터베이스 연동으로 연락처 CRUD 기능 추가
