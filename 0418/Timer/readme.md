# ⏰ Timer App - 알림 기능이 포함된 카운트다운 타이머

이 앱은 사용자가 설정한 시간을 기준으로 **타이머를 작동**시키고,  
시간이 다 되면 **알림(Notification)** 을 통해 사용자에게 알림을 주는 안드로이드 앱입니다.

---

## 🧩 핵심 기능 요약

| 기능                         | 설명                                                                 |
|------------------------------|----------------------------------------------------------------------|
| ⏱ 카운트다운 타이머         | 사용자 입력을 기반으로 초 단위 타이머 작동                          |
| 🔔 알림 전송                 | 타이머 종료 시 Notification으로 사용자에게 알림 전송                 |
| 📲 권한 요청 (Android 13+)   | `POST_NOTIFICATIONS` 권한 요청 포함 (권한 미승인 시 알림 제한됨)     |
| ⌨️ 사용자 입력               | `EditText`를 통한 mm:ss 형식 시간 입력 지원                         |

---

## 📱 UI 레이아웃 구성 (`activity_main.xml`)

```xml
<ConstraintLayout>
    <TextView
        android:text="Timer"
        android:textSize="34sp"
        android:textStyle="bold" />

    <EditText
        android:id="@+id/edit"
        android:hint="예: 05:00"
        android:inputType="text" />

    <Button
        android:id="@+id/btn"
        android:text="알람 설정" />
</ConstraintLayout>
🔧 주요 동작 흐름 (MainActivity.java)
String[] times = mEditText.getText().toString().split(":");
int min = Integer.parseInt(times[0]);
int sec = Integer.parseInt(times[1]);

new CountDownTimer((min * 60 + sec) * 1000, 1000) {
    public void onTick(long millisUntilFinished) {}

    public void onFinish() {
        showNotification("타이머 종료", "설정한 시간이 끝났습니다.");
    }
}.start();
🔔 알림(Notification) 처리
NotificationChannel channel = new NotificationChannel(
    NOTIFICATION_CHANNEL_ID,
    "Timer Channel",
    NotificationManager.IMPORTANCE_HIGH
);
manager.createNotificationChannel(channel);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
    .setContentTitle(title)
    .setContentText(message)
    .setSmallIcon(R.drawable.ic_launcher_background)
    .setSound(defaultSoundUri);

manager.notify(0, builder.build());
📁 프로젝트 파일 구조
Timer/
├── MainActivity.java
├── activity_main.xml
├── AndroidManifest.xml
└── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
타이머 + 알림 기능을 직접 체험 가능한 실용적인 예제

Android 13 이상에서의 알림 권한 처리 포함

사용자 입력 기반의 UI-로직 연동 구조가 명확

⚠️ Weaknesses (약점)
잘못된 입력값 처리(예외처리) 미흡

앱이 종료되면 타이머도 함께 종료됨 (백그라운드 유지 불가)

💡 Opportunities (기회)
백그라운드 알람 유지 기능 추가 (Foreground Service or AlarmManager)

설정한 시간 저장 및 반복 알람 기능 추가

다양한 사용자 알림 설정(진동, 커스텀 사운드 등) 제공

🚫 Threats (위협)
Android 13 미만과 이상 권한 처리 로직의 호환성 문제 발생 가능성

알림 ID 고정 시 중복 알림 문제 발생 가능성

🌱 개선 및 확장 아이디어
TimePickerDialog 활용한 시각적 시간 선택 UI 제공

알람 반복 기능 (매일 아침 7시 알람 등)

앱 종료 후에도 알람 유지되는 구조 적용 (Foreground Service)

