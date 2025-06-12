# 🧩 Pilly - Android XML 레이아웃 구성

본 문서는 Pilly 앱의 UI 구성 요소인 XML 레이아웃 파일을 설명합니다.

## 📁 주요 레이아웃 파일 목록

| 파일명 | 설명 |
|--------|------|
| activity_main.xml | 메인화면, 약 리스트 카드 출력 및 하단 네비게이션 포함 |
| activity_login.xml | 로그인 화면, 이메일/비밀번호 입력 필드와 로그인 버튼 |
| activity_signup.xml | 회원가입 화면, 아이디/비번/이름 등 입력 |
| activity_add_medicine_time.xml | 약 복용 시간 추가 화면 (시간/오전오후 선택) |
| activity_alarm_setting.xml | 알림 설정 화면 |
| activity_profile.xml | 내정보 확인 및 수정 화면 |
| activity_care.xml | 복약 돌봄 요청 및 상태 관리 화면 |
| activity_record.xml | 복용 완료 기록 확인 화면 |
| activity_splash.xml | 앱 시작 시 로고 표시 (Splash) |
| dialog_edit_care_target.xml | 돌봄 대상 수정 다이얼로그 |
| item_medicine_card.xml | 시간대별 약 카드 레이아웃 |
| layout_bottom_nav.xml | 하단 네비게이션 공통 레이아웃 |
| layout_top_logo.xml | 상단 Pilly 로고 공통 레이아웃 |

## 🖌️ 특징 및 디자인 방향

- Material Design 기반의 간결하고 보기 쉬운 레이아웃 구성
- `ConstraintLayout`, `LinearLayout` 적절히 활용
- 커스텀 버튼 및 배경은 `@drawable/rounded_btn` 등으로 지정
- 시간 입력, 알림 설정, 돌봄 요청 등 사용자 상호작용 중심 레이아웃 제공

## 💡 참고

- 모든 레이아웃은 Java Activity와 1:1로 매핑되어 있어 동적 연결이 쉬움
- RecyclerView 항목은 `item_*.xml` 형식으로 분리 구성됨

  
