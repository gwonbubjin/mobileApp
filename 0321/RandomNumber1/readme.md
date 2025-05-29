# 🎲 RandomNumber1

안드로이드 앱에서 버튼을 누르면 0~99 사이의 무작위 숫자를 생성해 화면에 표시하는 간단한 **랜덤 숫자 생성기** 앱입니다.

---

## 📌 프로젝트 개요

- **앱 이름**: RandomNumber1
- **개발 언어**: Java
- **레이아웃 구성**: XML 기반 LinearLayout
- **기능 요약**: 버튼 클릭 시 난수를 생성하여 텍스트뷰에 출력

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🔘 난수 생성 버튼 | "랜덤 생성" 버튼을 누르면 0~99 사이 정수가 생성됨 |
| 📤 결과 출력 | 생성된 숫자는 `TextView`에 "난수 : XX" 형식으로 표시됨 |

---

## 🛠️ 핵심 코드

### 📄 MainActivity.java

```java
public void generateRandomNumber(View view){
    Random random = new Random();
    int randomNumber = random.nextInt(100);
    textViewRandomNumber.setText("난수 : " + randomNumber);
}
🧱 activity_main.xml
xml
복사
편집
<TextView
    android:id="@+id/textViewRandomNumber"
    android:text="난수" />

<Button
    android:id="@+id/buttonGenerateRandom"
    android:text="랜덤 생성"
    android:onClick="generateRandomNumber"/>
🧠 SWOT 분석
✅ Strength
매우 간단한 구조, 초보자 실습에 최적

클릭 이벤트 → 데이터 처리 → 결과 출력의 기본 흐름 체험

⚠ Weakness
디자인 단조로움

범위 설정, 정밀도 조절 등 기능 없음

💡 Opportunity
숫자 범위 조정, 시드 고정 등 옵션 추가 가능

애니메이션 효과나 배경 테마 적용 확장 가능

🚫 Threat
기능이 단순해 상용 활용에는 제한적

📂 프로젝트 구조
bash
복사
편집
RandomNumber1/
├── MainActivity.java         # 버튼 클릭 시 난수 생성 및 출력
├── activity_main.xml         # 텍스트뷰 + 버튼 UI 정의
├── readme.md                 # 설명 문서 (본 파일)
🌱 발전 방향
숫자 범위 사용자 지정 기능 추가

생성된 숫자 히스토리 리스트로 저장

배경색 변경, 사운드 등 시각/청각 효과 추가
