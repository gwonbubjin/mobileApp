# 📘 Day0328 - 안드로이드 앱 개발 학습 일지

> 📅 **날짜**: 2024년 3월 28일  
> 🛠 **학습 목표**: 다양한 UI 요소들을 활용하여 사용자 상호작용 기능 개발  
> 🧠 **핵심 키워드**: `TextView`, `EditText`, `Button`, `ImageView`, `Random`, `if`, `switch`, `setVisibility`, `LinearLayout`, `계산기`

---

## ✅ 오늘 배운 내용 요약

오늘은 다양한 앱 예제를 통해 **레이아웃 구성 능력**과 **버튼 클릭 이벤트 처리**, **조건문 활용**, **이미지 처리**, **랜덤값 생성**, **계산기 로직 구현** 등을 직접 실습하였습니다.

총 6개의 실습 프로젝트를 진행했습니다:

- 사칙연산 계산기 (Coding_Challenge3)
- 주사위 이미지 변경 (Dice Roller)
- 이미지뷰 제어 (imagecontrol)
- 복잡한 계산기 UI 배치 (layout_heavy_calculator)
- 리니어 레이아웃 실습 (LinearLayoutTest)
- 숫자 맞추기 게임 (NumberGuess)

---

## 🧩 실습 목록 및 설명

### 1. ✖️➕ Coding_Challenge3 - 사칙연산 계산기

**기능 요약**:
- 두 수를 입력받아 덧셈, 뺄셈, 곱셈, 나눗셈 결과를 출력

**주요 학습 내용**:
- `EditText`로 숫자 입력받기
- `Double.parseDouble()`을 이용한 변환
- 공통 `OnClickListener` 활용으로 중복 제거
- switch-case나 if문으로 연산 분기

---

### 2. 🎲 Dice Roller - 랜덤 주사위

**기능 요약**:
- 버튼 클릭 시 1~6의 주사위 이미지 무작위로 표시

**주요 학습 내용**:
- `Random().nextInt(6) + 1`
- 조건문으로 `ImageView.setImageResource()` 제어
- 다양한 `drawable` 이미지 리소스 등록 및 활용

---

### 3. 🖼 imagecontrol - 이미지뷰 제어 앱

**기능 요약**:
- 버튼 클릭 시 이미지 보여주기, 숨기기, 교체

**주요 학습 내용**:
- `ImageView`의 `setVisibility()` 및 `setImageResource()` 사용
- 사용자 클릭에 따라 이미지 동적 변경 처리

---

### 4. 🧮 layout_heavy_calculator - 계산기 레이아웃 집중 실습

**기능 요약**:
- 실제 계산 기능은 없음, 복잡한 버튼 구성의 UI 배치

**주요 학습 내용**:
- `Grid`, `LinearLayout` 등 레이아웃 배치 능력 향상
- 계산기처럼 숫자 키패드 형식의 UI 구성
- 버튼 사이즈 조절, 가중치(`layout_weight`) 활용

---

### 5. 🔲 LinearLayoutTest - 리니어레이아웃 구조 실험

**기능 요약**:
- 다양한 XML 배치를 테스트한 5개의 화면 구조 실험

**주요 학습 내용**:
- 수평 / 수직 방향의 `LinearLayout` 조합 실습
- 중첩 레이아웃, 비율 조절, `layout_weight`의 실습
- 뷰 간 정렬, 배치 능력 향상

---

### 6. 🔢 NumberGuess - 숫자 맞추기 게임

**기능 요약**:
- 1~100 사이의 랜덤 숫자를 맞추는 게임
- “더 큽니다”, “더 작습니다”, “정답입니다!” 출력

**주요 학습 내용**:
- `Random`, `EditText`, `TextView`, `조건문 if/else` 활용
- 사용자 입력값 받아서 정수 비교하기
- 추측 게임 구현을 통해 앱의 논리 처리 감각 향상

---

## 📂 디렉터리 구조 예시

Day0328/
├── Coding_Challenge3/
│ ├── MainActivity.java
│ └── activity_main.xml
├── Dice Roller/
│ ├── MainActivity.java
│ └── activity_main.xml
├── imagecontrol/
│ ├── MainActivity.java
│ └── activity_main.xml
├── layout_heavy_calculator/
│ ├── MainActivity.java
│ └── activity_main.xml
├── LinearLayoutTest/
│ ├── activity_main.xml ~ activity_main5.xml
├── NumberGuess/
│ ├── MainActivity.java
│ └── activity_main.xml
└── readme.md (← 이 파일)

yaml
복사
편집

---

## 💡 오늘의 회고

- 레이아웃 배치는 숙련이 필요한 작업임을 느꼈고, 다양한 실험을 통해 배치를 익혔다.
- 이미지 제어와 랜덤 처리를 접목해 앱에 재미를 더할 수 있음을 알게 되었다.
- `OnClickListener`를 반복해서 사용하며 **리스너 설계**에 대한 이해가 깊어졌다.

---

## 📊 SWOT 분석

| 항목        | 설명 |
|-------------|------|
| ✅ Strengths | 다양한 기능들을 실습하며 레이아웃, 이벤트, 랜덤, 이미지 등을 고루 익힘 |
| ⚠️ Weaknesses | UI 구성과 코드 분리가 부족하여 추후 유지보수에는 어려움이 예상됨 |
| 🌱 Opportunities | 계산기 앱을 발전시켜 기능 추가 및 RecyclerView 기반 목록 등으로 확장 가능 |
| 🚫 Threats     | 조건문과 변환 처리 오류가 발생할 수 있으므로 예외처리 학습이 필요함 |

---

## ✨ 다음 단계 제안

- 복잡한 UI 구성 시의 재사용성과 컴포넌트 분리 학습
- `Intent`를 활용한 화면 간 전환
- 사용자 입력을 저장하고 로딩하는 SharedPreferences 실습
- 나중에는 DB 활용까지 고려해보기

---

> _"오늘의 실습은 작지만, 내일의 프로젝트를 위한 기초이다."_  
> _코드는 많을수록 익숙해지고, UI는 그릴수록 감각이 생긴다._ 🎯
