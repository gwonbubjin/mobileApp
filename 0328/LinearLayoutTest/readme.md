# 🧱 Layout 비교 실습 - LinearLayoutTest

안드로이드의 다양한 레이아웃 구조(`LinearLayout`, `RelativeLayout`, `GridLayout`, `TableLayout`, `ConstraintLayout`)를 비교 실습하는 프로젝트입니다.

각 레이아웃은 동일한 UI 구성 요소(로고, 아이디/비번 입력창, 버튼 등)를 기반으로 구현되었으며, 각 방식의 배치 차이를 학습하기 위해 설계되었습니다.

---

## 🧩 포함된 레이아웃 파일

| 파일명 | 레이아웃 종류 | 주요 특징 |
|--------|----------------|------------|
| `activity_main.xml` | `LinearLayout + ScrollView` | 수직 정렬, 스크롤 가능, 구조가 가장 직관적 |
| `activity_main2.xml` | `RelativeLayout` | 요소 간 상대 위치 설정 가능 |
| `activity_main3.xml` | `GridLayout` | 열 기반 정렬, 열 병합(span) 기능 활용 |
| `activity_main4.xml` | `TableLayout` | 행-열 구성, `TableRow` 단위 정렬 |
| `activity_main5.xml` | `ConstraintLayout` | 제약 기반 정렬, 유연한 UI 배치 지원 |

---

## 🎯 공통 UI 요소

- 📷 **로고 이미지** (`ImageView`)
- 🧾 **아이디/비밀번호 입력창** (`EditText`)
- 🔘 **로그인 버튼** (`Button`)
- 🗃️ **기억하기, 자동 로그인** 체크박스 (`CheckBox`) *(파일에 따라 포함 여부 다름)*

---

## 📐 예시: `LinearLayout` 구조 (`activity_main.xml`)

```xml
<ScrollView>
  <LinearLayout orientation="vertical">
    <ImageView android:src="@drawable/guess" />
    <EditText android:hint="아이디 입력" />
    <EditText android:hint="비밀번호 입력" />
    <Button android:text="로그인" />
  </LinearLayout>
</ScrollView>
🧠 학습 포인트
각각의 레이아웃이 어떤 UI에 적합한지 체험 가능

같은 UI를 다양한 구조로 구현해보며 구조적 이해 강화

ConstraintLayout은 가장 유연하지만 설정이 복잡함

📁 프로젝트 구성
bash
복사
편집
LinearLayoutTest/
├── activity_main.xml         # LinearLayout + ScrollView
├── activity_main2.xml        # RelativeLayout
├── activity_main3.xml        # GridLayout
├── activity_main4.xml        # TableLayout
├── activity_main5.xml        # ConstraintLayout
├── readme.md                 # 설명 문서 (본 파일)
💡 확장 아이디어
동일한 UI를 FrameLayout, FlexboxLayout 등으로도 구현해보기

각 레이아웃의 성능/유지보수성 비교 분석

Jetpack Compose 버전으로 재구성
