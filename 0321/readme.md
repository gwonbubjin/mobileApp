# 📘 Day0321 - Android 앱 개발 학습 일지

> 📅 **날짜**: 2024년 3월 21일  
> 🛠 **학습 목표**: 안드로이드 기본 컴포넌트 및 이벤트 처리 이해  
> 🧠 **핵심 키워드**: `Button`, `EditText`, `TextView`, `Intent`, `setVisibility()`, `Random`, `onClickListener`

---

## ✅ 오늘 배운 내용 요약

오늘은 Android 앱 개발의 기초를 다지기 위해 다음의 핵심 주제들을 실습하고 이해했습니다:

- **버튼 이벤트 처리**
- **EditText 입력값 읽기**
- **간단한 계산기 구현**
- **랜덤 숫자 생성**
- **색상 변경 테스트**
- **버튼을 통한 뷰 가시성 조절**
- **액티비티 간 이동 연습**

총 **5개의 미니 프로젝트**를 진행하며 각 개념을 코드로 구현해보았습니다.

---

## 🧩 실습 목록 및 설명

### 1. 🧮 CalculatingMachine  
**주요 기능**:  
- 두 개의 EditText에서 숫자를 입력받아 더하기, 빼기, 곱하기, 나누기를 수행  
- 결과는 세 번째 EditText에 표시  

**학습 포인트**:
- `EditText.getText().toString()`으로 문자열 추출
- `Integer.parseInt()`를 통한 정수형 변환
- `Button.setOnClickListener()`의 사용법

**관련 클래스**: `MainActivity.java`  
**레이아웃 파일**: `activity_main.xml`

---

### 2. 🎨 ColorTest  
**주요 기능**:  
- 버튼 클릭 시 배경 색상을 변경  

**학습 포인트**:
- `setBackgroundColor(Color.색상)` 활용
- 리니어 레이아웃의 색상 변경 적용

---

### 3. 🔢 RandomNumber1  
**주요 기능**:  
- 버튼 클릭 시 0~99 사이의 무작위 숫자를 `TextView`에 출력  

**학습 포인트**:
- `Random random = new Random();`
- `random.nextInt(100);`
- `TextView.setText(String.valueOf(number))`

---

### 4. 🔘 TwoButton  
**주요 기능**:  
- 두 개의 버튼을 사용해 텍스트 뷰에 다른 문장을 표시

**학습 포인트**:
- 하나의 액티비티 안에서 여러 버튼 처리
- `if(view.getId() == R.id.버튼ID)` 방식 조건 처리

---

### 5. 👁️‍🗨️ VisisbleTest  
**주요 기능**:  
- 버튼 클릭 시 텍스트뷰의 보임/숨김 상태 토글  

**학습 포인트**:
- `View.VISIBLE`, `View.INVISIBLE`, `View.GONE` 개념 비교
- `setVisibility()` 함수 활용

---

## 📂 디렉터리 구성 예시

Day0321/
├── CalculatingMuchine/
│ ├── MainActivity.java
│ ├── activity_main.xml
│ └── readme.md
├── ColorTest/
│ ├── activity_main.xml
│ └── readme.md
├── RandomNumber1/
│ ├── MainActivity.java
│ ├── activity_main.xml
│ └── readme.md
├── TwoButton/
│ ├── activity_main.xml
│ └── readme.md
├── VisisbleTest/
│ ├── activity_main.xml
│ └── readme.md
└── readme.md (← 이 파일)


---

## 💡 오늘의 회고

- Java에서 **UI 요소 바인딩**하는 기본 구조를 익혔다 (`findViewById`, 이벤트 등록 등)
- `setOnClickListener()`와 `setText()` 등의 **핵심 메서드**를 숙달함
- 레이아웃 배치, 배경색 변경, 랜덤 숫자 출력 등 단순하지만 중요한 요소를 **직접 구현하면서 감각을 익힘**
- XML과 Java의 연계 방식을 체험하면서 **레이아웃과 로직 분리**의 중요성을 이해함

---

## 📊 SWOT 분석

| 항목        | 설명                                                                 |
|-------------|----------------------------------------------------------------------|
| ✅ Strengths | 다양한 기본 위젯과 이벤트를 실습하며 핵심 개념들을 빠르게 습득함              |
| ⚠️ Weaknesses | 아직 액티비티 전환, 데이터 전달 등은 익숙하지 않음                             |
| 🌱 Opportunities | 이후 `Intent`, `Activity Lifecycle`, `RecyclerView` 등 심화 주제 확장 가능 |
| 🚫 Threats     | 기초 구조를 반복하지 않으면 금방 잊어버릴 수 있음 → 정리 및 복습이 중요함     |

---

## ✨ 다음 단계

- 화면 전환 연습 (다음 주차로 예정)
- 여러 개의 `EditText`, `Spinner`, `RadioButton` 조합 연습
- 데이터 전달 및 상태 저장 방식 학습 예정

---

> _"오늘의 작은 앱들이 내일의 완성형 프로젝트를 만든다."_  
> _계속해서 손으로 직접 만들며 익혀 나가자!_ 💪💻
