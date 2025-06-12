# 📱 Pilly - Java & XML 파일 설명서

안드로이드 복약 알림 앱 **Pilly**의 전체 Java 및 XML 파일 구조를 기능 중심으로 정리한 문서입니다. GitHub, 발표, 문서화 등에 그대로 사용할 수 있도록 깔끔하게 구성했습니다.

---

## ⚙️ Java 파일 설명 (총 35개)

| 파일명                             | 역할                                              |
| ------------------------------- | ----------------------------------------------- |
| `AddCareActivity.java`          | 돌봄 요청을 보낼 수 있는 화면. 사용자 목록에서 대상 선택 후 요청 전송       |
| `AddMedicineTimeActivity.java`  | 복용 시간과 약 정보를 등록하고 Firebase에 저장하는 화면             |
| `AlarmHelper.java`              | 알람 등록/해제 유틸 클래스 (`AlarmManager` 사용)             |
| `AlarmReceiver.java`            | 예약된 시간에 알림(Notification)을 띄우는 BroadcastReceiver |
| `AlarmSettingActivity.java`     | 사용자가 복용 알림 시간을 설정하는 화면                          |
| `BottomNavHelper.java`          | 하단 네비게이션바의 탭 전환 로직 처리 클래스                       |
| `CareActivity.java`             | 돌봄 요청 수락/관리 및 대상자 복약 상태를 실시간 확인하는 화면            |
| `CareTarget.java`               | 돌봄 대상자 정보를 담는 모델 클래스                            |
| `CareTargetAdapter.java`        | 돌봄 대상자 리스트를 출력하는 RecyclerView 어댑터               |
| `Challenge.java`                | 챌린지 항목(복약 횟수, 목표 등)을 정의하는 모델                    |
| `ChallengeActivity.java`        | 챌린지 목록을 UI로 구성하고 달성률을 확인하는 화면                   |
| `ChallengeAdapter.java`         | 챌린지 카드를 출력하는 RecyclerView 어댑터                   |
| `ConfirmReceiver.java`          | 알림에서 "복용 완료" 클릭 시 Firebase에 taken 상태 업데이트       |
| `EditMedicineTimeActivity.java` | 기존 약 복용 시간 및 약 정보 수정 화면                         |
| `EditProfileActivity.java`      | 사용자 이름 등 프로필 수정 화면                              |
| `InputFilterMinMax.java`        | EditText 입력값의 최소/최대 범위를 제한하는 필터 클래스             |
| `LoginActivity.java`            | 로그인 화면, Firebase 인증 연동                          |
| `MainActivity.java`             | 메인화면. 약 카드 리스트 표시, 알람 등록, 하단 탭 포함               |
| `MedicineCardAdapter.java`      | 시간대별 약 복용 카드 리스트 출력용 어댑터                        |
| `MedicineItem.java`             | 약 이름, 복용량 등 개별 약 정보를 담는 모델                      |
| `MedicineRow.java`              | 하나의 복용 시간에 포함된 여러 약의 묶음을 담는 클래스                 |
| `MedicineRowAdapter.java`       | `MedicineRow` 객체 리스트를 표시하는 어댑터                  |
| `MedicineTime.java`             | 복약 시간(예: AM 08:00) 정보를 담는 모델 클래스                |
| `ProfileActivity.java`          | 내정보 화면. 사용자 정보 표시 및 수정, 돌봄 요청 이동                |
| `ReceivedRequestItem.java`      | 받은 돌봄 요청 하나의 정보 모델                              |
| `ReceivedRequestsActivity.java` | 받은 요청들을 리스트로 보여주는 화면                            |
| `ReceivedRequestsAdapter.java`  | 받은 요청 리스트 출력용 어댑터                               |
| `RecordActivity.java`           | 복약 완료 기록을 날짜별로 확인하는 화면                          |
| `RequestAdapter.java`           | 돌봄 요청을 보낼 사용자 리스트 출력 어댑터                        |
| `RequestItem.java`              | 보낼 요청 정보를 담는 모델 클래스                             |
| `SignupActivity.java`           | 회원가입 처리, 중복 확인 및 Firebase 저장 기능 포함              |
| `SplashActivity.java`           | 앱 시작 시 로고를 띄우고 자동 로그인 처리하는 초기 화면                |
| `User.java`                     | 사용자 ID, 이메일, 이름 등을 담은 사용자 모델 클래스                |
| `ExampleInstrumentedTest.java`  | Android 기기에서의 테스트 클래스                           |
| `ExampleUnitTest.java`          | Java 로직 단위 테스트 클래스                              |

---

## 🧩 XML 레이아웃 파일 설명 (총 25개)

| 파일명                               | 역할                               |
| --------------------------------- | -------------------------------- |
| `activity_add_care.xml`           | 돌봄 요청 보내기 UI. 사용자 목록과 전송 버튼 포함   |
| `activity_add_medicine_time.xml`  | 약 시간, 이름, 복용량 입력 화면 UI 구성        |
| `activity_alarm_setting.xml`      | 알림 시간 설정을 위한 입력 필드 구성            |
| `activity_care.xml`               | 수락한 대상자 리스트, 복약 완료 상태 확인 UI 포함   |
| `activity_challenge.xml`          | 챌린지 카드 리스트 구성. RecyclerView 포함   |
| `activity_edit_profile.xml`       | 사용자 이름 수정 입력 필드와 저장 버튼 포함        |
| `activity_login.xml`              | 로그인 화면. 이메일, 비밀번호 입력 및 로그인 버튼 포함 |
| `activity_main.xml`               | 메인화면. 약 카드 리스트, +버튼, 하단탭 구성 포함   |
| `activity_profile.xml`            | 내정보 표시 화면. 사용자 이름, 이메일, 수정 버튼 포함 |
| `activity_received_requests.xml`  | 받은 돌봄 요청 리스트 UI 구성               |
| `activity_record.xml`             | 복약 기록 리스트 출력 화면 구성               |
| `activity_signup.xml`             | 회원가입 입력 필드 구성. 중복 확인 및 가입 버튼 포함  |
| `activity_splash.xml`             | 앱 로딩 시 로고 표시. 자동 로그인 처리 포함       |
| `day_cell.xml`                    | 달력 구성에 사용되는 셀 하나의 UI 요소          |
| `dialog_edit_care_target.xml`     | 돌봄 대상 수정용 다이얼로그 레이아웃             |
| `edit_medicine_time_activity.xml` | 복용 시간 수정 화면 구성                   |
| `item_care_target.xml`            | 돌봄 대상자 리스트 항목 UI 구성              |
| `item_challenge_card.xml`         | 챌린지 카드 하나의 UI 구성                 |
| `item_medicine_card.xml`          | 시간대별 약 리스트 카드 UI 구성              |
| `item_medicine_item.xml`          | 개별 약 이름, 복용량 UI 항목 구성            |
| `item_medicine_row.xml`           | 약 리스트를 시간대별로 묶은 UI 구성            |
| `item_received_request.xml`       | 받은 요청 항목 하나의 리스트 구성 UI           |
| `item_request.xml`                | 요청 보낼 사용자 항목 UI                  |
| `layout_bottom_nav.xml`           | 하단 네비게이션바 공통 UI                  |
| `layout_top_logo.xml`             | 상단 로고 공통 레이아웃                    |

---

📝 위 파일들은 Android Studio에서의 전체 기능 흐름과 UI 구성을 명확하게 나누어 개발되었으며, Firebase Firestore와 AlarmManager, RecyclerView 등을 활용한 구조로 되어 있습니다. 발표 및 문서화 시 이 문서를 기반으로 설명하면 아주 효과적입니다.

