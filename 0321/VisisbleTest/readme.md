# 👀 VisisbleTest

안드로이드의 `View` 요소에서 `visibility` 속성의 차이를 실험하는 UI 테스트 앱입니다.  
버튼들을 다양한 `visibility` 상태로 설정하여 화면에 어떤 영향을 주는지를 확인할 수 있습니다.

---

## 📌 프로젝트 개요

- **이름**: VisisbleTest  
- **개발환경**: Android Studio  
- **사용 언어**: XML (UI 정의)  
- **핵심 주제**: `visibility` 속성 (`visible`, `invisible`, `gone`)  

---

## 🧩 레이아웃 구성 및 visibility 비교

| 버튼 | 텍스트 | visibility 상태 | 설명 |
|------|--------|------------------|------|
| 버튼1 | "버튼1" | 기본값 (`visible`) | 화면에 표시됨 |
| 버튼2 | "버튼2" | `invisible` | **자리 차지O**, 보이지 않음 |
| 버튼3 | "버튼3" | 기본값 (`visible`) | 화면에 표시됨 |
| 버튼4 | "버튼4" | `gone` | **자리 차지X**, 완전히 사라짐 |
| 버튼5 | "버튼5" | 기본값 (`visible`) | 화면에 표시됨 |

---

## 🛠️ 핵심 XML 코드

```xml
<Button
    android:text="버튼2"
    android:visibility="invisible" />

<Button
    android:text="버튼4"
    android:visibility="gone" />
🧠 학습 포인트
invisible: 요소는 보이지 않지만 레이아웃 상 자리를 차지함

gone: 요소는 보이지도 않고, 공간도 사라짐

visible: 기본값으로, 보이고 자리를 차지함

📂 프로젝트 구조
bash
복사
편집
VisisbleTest/
├── activity_main.xml         # 버튼 5개와 visibility 속성 실험
├── readme.md                 # 설명 문서 (본 파일)
🌱 발전 방향
버튼 클릭 시 visibility 변경 이벤트 추가 (Java 연동)

텍스트뷰나 이미지뷰 등 다양한 요소로 테스트 확장

UI 학습용 예제로 반복 활용 가능


