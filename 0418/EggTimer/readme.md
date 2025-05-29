# 🥚 EggTimer - 알람 타이머 앱

이 앱은 사용자가 설정한 시간 후에 알림을 보내주는 **간단한 타이머 앱**입니다.  
`EditText`로 시간을 설정하고, 시간이 끝나면 Notification을 통해 알람을 보내는 구조입니다.  
**권한 요청, 알림 채널 생성, 타이머 동작**까지 모두 포함되어 있습니다.

---

## 🧩 핵심 기능 요약

| 기능                       | 설명                                                                 |
|----------------------------|----------------------------------------------------------------------|
| ⏱ 타이머 기능              | 사용자가 입력한 시간(분, 초)에 따라 CountDownTimer 작동               |
| 🔔 알림(Notification)      | 시간이 끝나면 Android Notification으로 사용자에게 알림 전송           |
| 📄 권한 요청               | Android 13 이상에서 POST_NOTIFICATIONS 권한 요청 처리 포함            |
| 👀 사용자 입력 지원        | EditText를 통해 시간 설정 (형식: "mm:ss")                              |

---

## 📱 주요 UI 구성 (`activity_main.xml`)

```xml
<ConstraintLayout>
    <ImageView android:src="@drawable/boiled_egg" />
    
    <EditText
        android:id="@+id/edit"
        android:hint="예: 03:00" />

    <Button
        android:id="@+id/btn"
        android:text="알람 설정" />
</ConstraintLayout>
🔧 핵심 로직 설명 (MainActivity.java)
java
복사
편집
mEditText = findViewById(R.id.edit);
findViewById(R.id.btn).setOnClickListener(view -> {
    String time = mEditText.getText().toString();
    String[] times = time.split(":");
    int min = Integer.parseInt(times[0]);
    int sec = Integer.parseInt(times[1]);

    new CountDownTimer((min * 60 + sec) * 1000, 1000) {
        public void onFinish() {
            showNotification("알람", "계란 삶을 시간이에요!");
        }
        public void onTick(long millisUntilFinished) {}
    }.start();
});
🔔 알림(Notification) 생성
java
복사
편집
NotificationManager manager = getSystemService(NotificationManager.class);
NotificationChannel channel = new NotificationChannel(
    NOTIFICATION_CHANNEL_ID,
    "Timer Channel",
    NotificationManager.IMPORTANCE_DEFAULT
);
manager.createNotificationChannel(channel);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
    .setContentTitle(title)
    .setContentText(message)
    .setSmallIcon(R.drawable.boiled_egg);

manager.notify(0, builder.build());
📁 프로젝트 구조
pgsql
복사
편집
EggTimer/
├── MainActivity.java
├── activity_main.xml
├── AndroidManifest.xml
└── readme.md
🧠 SWOT 분석
✅ Strengths (강점)
CountDownTimer와 Notification의 기본 사용법을 익히기 좋은 예제

Android 13 이상에서의 권한 처리까지 포함되어 실무 대응 가능

사용자 입력 → 시간 파싱 → 알람 처리 흐름이 잘 정리됨

⚠️ Weaknesses (약점)
입력값 검증 없음 (예: 잘못된 시간 형식 예외처리 부족)

앱 종료 시 알람 유지 기능 미구현 (백그라운드 처리 없음)

💡 Opportunities (기회)
AlarmManager + BroadcastReceiver로 앱 종료 시에도 알람 유지 가능

Notification Action 추가하여 중지/반복 기능 확장 가능

음성/진동 등 알림 방식 사용자 선택 기능 추가 가능

🚫 Threats (위협)
Android 13 미만에서는 알림 권한 코드 불필요 → 분기 처리 필요

Notification ID를 고정값(0)으로 사용하면 중복 알림 덮어쓰기 위험

🌱 향후 개선 방향
알람 반복 옵션 (ex. 매일 아침 8시)

알림 설정값 저장 (SharedPreferences)

시간 설정을 SeekBar나 TimePicker로 변경하여 UI 개선


