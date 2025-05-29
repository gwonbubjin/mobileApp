# 🌈 ColorTest

안드로이드 앱 UI 연습을 위한 초간단 색상 실험 프로젝트입니다.  
앱 배경색을 지정하고 버튼을 배치하여 XML 레이아웃 구성 연습에 집중한 예제입니다.

---

## 🧾 프로젝트 개요

- **이름**: ColorTest  
- **개발환경**: Android Studio  
- **사용 언어**: XML (UI 정의 전용)  
- **주요 목적**: 색상 및 버튼 UI 실습  

---

## 🧩 구현 내용

| 구성 요소 | 설명 |
|-----------|------|
| 🎨 배경색 | `#48c34a` (초록색 계열)로 설정 |
| 🔘 버튼 | "버튼" 텍스트가 표시된 기본 버튼 1개 포함 |
| 🧱 레이아웃 | `LinearLayout` 수직 방향 사용 |

```xml
<LinearLayout
    android:orientation="vertical"
    android:background="#48c34a">
    
    <Button
        android:text="버튼"/>
</LinearLayout>
🧠 개발 목적 및 활용
XML 구조 학습에 적합

배경 색상 설정 방법 실습

버튼 UI 배치 기본 연습

⚙️ 향후 확장 아이디어
버튼 클릭 시 색상 변경 이벤트 추가 (Java/Kotlin 연동)

여러 버튼 추가 → 색상 전환 테스트

텍스트뷰 등 다른 UI 요소 조합 실험


