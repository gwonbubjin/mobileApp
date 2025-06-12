# ⚙️ Pilly - Java 기능 클래스 구성

본 문서는 Pilly 앱의 기능을 담당하는 Java 클래스 파일을 정리한 내용입니다.

## 📂 Java 파일 설명 (총 35개)

파일명

설명

AddCareActivity.java

돌봄 요청을 보낼 수 있는 화면, 사용자 목록에서 선택해 요청 전송

AddMedicineTimeActivity.java

복용 시간과 약 정보 등록 화면, Firebase에 저장

AlarmHelper.java

AlarmManager를 통해 알림 등록/해제 담당하는 유틸 클래스

AlarmReceiver.java

예약된 알람이 울릴 때 실행되어 알림(Notification)을 띄움

AlarmSettingActivity.java

사용자가 알림 시간을 설정하는 화면 구성

BottomNavHelper.java

하단 네비게이션바 클릭 이벤트 및 화면 전환 처리 클래스

CareActivity.java

돌봄 요청 수락 및 등록된 대상 복약 상태 확인 화면

CareTarget.java

돌봄 대상 정보를 담는 모델 클래스

CareTargetAdapter.java

돌봄 대상 리스트 RecyclerView 출력용 어댑터

Challenge.java

챌린지 항목 데이터 클래스 (예: 목표 복약 횟수 등)

ChallengeActivity.java

챌린지 카드 표시 및 달성률 UI 구성 화면

ChallengeAdapter.java

챌린지 RecyclerView 카드 어댑터

ConfirmReceiver.java

알림에서 복용 완료 누르면 호출되어 Firebase 상태 업데이트

EditMedicineTimeActivity.java

기존 약 정보를 편집할 수 있는 화면 구성

EditProfileActivity.java

사용자 이름 등 프로필 정보 수정 화면

InputFilterMinMax.java

숫자 입력 범위 제한 필터 (EditText 용 유틸 클래스)

LoginActivity.java

로그인 화면, Firebase 인증 처리 포함

MainActivity.java

앱 메인화면, 약 복용 카드 표시 및 알람 설정, 탭 전환 기능 포함

MedicineCardAdapter.java

시간대별 약 카드(예: 오전 8시) 출력용 RecyclerView 어댑터

MedicineItem.java

개별 약의 이름, 복용량 등 정보를 담는 모델 클래스

MedicineRow.java

한 시간대에 포함된 여러 약 정보를 리스트로 보유하는 클래스

MedicineRowAdapter.java

MedicineRow를 출력하는 RecyclerView 어댑터

MedicineTime.java

사용자의 복용 시간(08:00, AM/PM 등) 정보 모델

ProfileActivity.java

내 정보 탭, 사용자 이름, 이메일, 돌봄 요청 진입 가능

ReceivedRequestItem.java

받은 요청 항목 하나를 표현하는 데이터 모델

ReceivedRequestsActivity.java

받은 돌봄 요청 전체 리스트를 보여주는 화면

ReceivedRequestsAdapter.java

돌봄 요청 RecyclerView 어댑터

RecordActivity.java

사용자가 복용 완료한 기록을 출력하는 화면

RequestAdapter.java

돌봄 요청을 보내는 사용자 목록을 출력하는 어댑터

RequestItem.java

보낼 요청 항목 하나를 표현하는 데이터 클래스

SignupActivity.java

회원가입 화면, Firebase 저장 및 중복 확인 기능 포함

SplashActivity.java

앱 실행 시 로고 표시 후 자동 로그인 처리 화면

User.java

사용자 ID, 이름, 이메일 등 Firebase에 저장되는 유저 정보 모델

ExampleInstrumentedTest.java

Android 기본 테스트 클래스

ExampleUnitTest.java

Java 로직 단위 테스트 클래스

## 🔐 Firebase 연동 구조

- Firestore 사용 (Users 컬렉션, 사용자 UID 기준으로 약/기록 분리)
- FirebaseAuth로 로그인/회원가입 처리
- 알림 설정 시 `setExactAndAllowWhileIdle()` 사용으로 Android 12+ 대응

## 🧠 핵심 로직 흐름

1. 로그인 성공 → MainActivity에서 UID 기반 약 데이터 불러오기
2. + 버튼 → AddMedicineTimeActivity → 시간 및 약 입력 → Firestore 저장
3. 저장된 데이터는 알림 설정 및 RecyclerView에 표시됨
4. 알림 수신 시 `AlarmReceiver` → Notification → ConfirmReceiver 처리
5. 복약 완료 기록은 `RecordActivity`에서 확인 가능

