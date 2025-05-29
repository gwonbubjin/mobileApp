# 🌟 Mission App - 기부/나눔 커뮤니티 앱

이 앱은 사용자가 **기부 등록, 커뮤니티 소통, 마이페이지 관리** 등을 할 수 있도록 구성된 다중 액티비티 기반 안드로이드 애플리케이션입니다.  
직관적인 아이콘 버튼을 통해 메인 화면에서 각 기능별 화면으로 전환할 수 있으며, 실전 앱 구조에 가까운 형태로 구성되어 있습니다.

---

## 📱 화면 구성

| 액티비티             | 설명                                                       |
|----------------------|------------------------------------------------------------|
| `MainActivity`       | 홈 화면. 네 개의 기능 버튼으로 구성되어 있으며, 각 화면으로 이동 |
| `RegisterActivity`   | 기부를 등록하는 화면                                        |
| `MyPageActivity`     | 사용자 마이페이지, 내가 등록한 기부 보기 등                  |
| `CommunityActivity`  | 커뮤니티 피드, 글 목록 등의 기능을 포함                     |
| `DetailActivity`     | 기부 상세 보기 화면 (리스트 → 상세 전환 구조 예시)           |

---

## 🧩 주요 기능 요약

| 기능                 | 설명 |
|----------------------|------|
| 🔄 액티비티 전환     | 메인에서 버튼 클릭 시 각 기능 액티비티로 이동 |
| ➕ 기부 등록 기능     | `RegisterActivity`에서 데이터 입력 UI 제공 |
| 🧑‍💼 마이페이지        | 유저의 기부 내역 및 개인정보 확인 |
| 💬 커뮤니티 기능      | 커뮤니티 글 목록 등 다양한 사용자 피드 구현 가능성 |
| 👁 상세 보기 기능     | 등록된 기부 정보 상세 확인 |

---

## 🛠 핵심 코드

### `MainActivity.java`

```java
btnRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
btnMyPage.setOnClickListener(v -> startActivity(new Intent(this, MyPageActivity.class)));
btnCommunity.setOnClickListener(v -> startActivity(new Intent(this, CommunityActivity.class)));
btnDetail.setOnClickListener(v -> startActivity(new Intent(this, DetailActivity.class)));
activity_main.xml
<LinearLayout ...>
    <ImageButton android:id="@+id/btnRegister" android:src="@android:drawable/ic_menu_add" />
    <ImageButton android:id="@+id/btnMyPage" android:src="@android:drawable/ic_menu_myplaces" />
    <ImageButton android:id="@+id/btnCommunity" android:src="@android:drawable/ic_menu_share" />
    <ImageButton android:id="@+id/btnDetail" android:src="@android:drawable/ic_menu_info_details" />
</LinearLayout>
📁 프로젝트 구조
MissionApp/
├── MainActivity.java
├── RegisterActivity.java
├── MyPageActivity.java
├── CommunityActivity.java
├── DetailActivity.java
├── activity_main.xml
├── activity_register.xml
├── activity_my_page.xml
├── activity_community.xml
├── activity_detail.xml
├── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
실전 프로젝트처럼 구성된 다중 화면 액티비티 구조

홈 화면에서 기능별 분리로 UI 흐름 명확

등록, 상세 보기, 마이페이지 등 다양한 기초 앱 기능 포함

⚠️ Weaknesses (약점)
데이터 저장 및 실제 동작 구현은 없음 (예: DB 연동)

커뮤니티나 상세보기 화면은 목업 수준

디자인이 단순하며 비즈니스 로직은 없음

💡 Opportunities (기회)
Firebase나 SQLite 연동하여 실제 기능 구현 가능

커뮤니티 → 댓글 기능, 좋아요, 검색 등 확장 가능

정식 배포용 앱으로 발전시키기 용이한 기초 설계

🚫 Threats (위협)
기능이 비어있으면 단순 UI 앱으로 보일 수 있음

사용자 인증, 보안, 데이터 백업 미비

🔧 확장 아이디어
Firebase Firestore 또는 SQLite 연동하여 기부 등록 저장

RecyclerView로 커뮤니티 글 목록 표시 및 클릭 시 상세 보기

Navigation Component 도입하여 프래그먼트 기반으로 구조 개선

사용자 로그인/회원가입 기능 추가
