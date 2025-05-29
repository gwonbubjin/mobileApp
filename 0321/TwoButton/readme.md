# ⌨️ TwoButton

안드로이드 앱 UI 연습을 위한 기본 구조 예제입니다.  
현재는 `Hello World!` 텍스트만 출력되는 ConstraintLayout 기반 프로젝트입니다.

---

## 📌 프로젝트 개요

- **이름**: TwoButton  
- **개발환경**: Android Studio  
- **사용 언어**: XML (UI 정의)  
- **기능 구현**: 아직 없음 (UI 구조만 있음)  

---

## 🧩 레이아웃 구성

| 요소 | 설명 |
|------|------|
| 📄 TextView | "Hello World!" 텍스트를 화면 정중앙에 배치 |
| 📐 ConstraintLayout | 유연한 화면 배치를 위한 구조 사용 |

### activity_main.xml 요약

```xml
<ConstraintLayout>
    <TextView
        android:text="Hello World!"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
</ConstraintLayout>
🧠 개발 의도
ConstraintLayout 학습 및 기본 배치 이해

추후 버튼 추가 및 클릭 이벤트 연동을 위한 기초 작업

🌱 향후 발전 방향
버튼 2개 추가: 예) "시작", "종료" 또는 "확인", "취소"

버튼 클릭 시 텍스트뷰 변경 또는 토스트 메시지 출력

Java 또는 Kotlin 코드와 연결하여 실제 동작 구현

📂 프로젝트 구조
bash
복사
편집
TwoButton/
├── activity_main.xml         # 기본 UI 구조 (Hello World 텍스트 포함)
├── readme.md                 # 설명 문서 (본 파일)

