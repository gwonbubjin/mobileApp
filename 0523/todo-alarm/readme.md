# 📝 todoapp - Android 정확한 알람(30초 후 알림) 지원 투두앱

![todoapp](https://img.shields.io/badge/Android-AlarmManager-green?logo=android)
![LICENSE](https://img.shields.io/github/license/your-repo/todoapp)
![API Level](https://img.shields.io/badge/API%20Level-31%2B-blue)
<br>

## 📌 **소개**
- 할 일을 추가하면 **30초 뒤 정확하게 알림(Notification)** 이 울리는 안드로이드 투두앱입니다.
- 앱이 **종료/백그라운드**여도 알림이 정상 도착합니다.
- Android 12 (API 31) 이상에서는 **정확한 알람 예약 권한(SCHEDULE_EXACT_ALARM)** 허용이 필요합니다.

---

## 🚀 **주요 기능**
- 할 일 추가, 삭제, 완료 체크
- 마감일/우선순위 설정
- 할 일 등록 시 30초 후 정확히 알림 발송
- 최신 Android 알람/노티피케이션 권한 대응

---

## 🛠️ **설치 & 실행 방법**

### 1. **프로젝트 클론**
```bash
git clone https://github.com/your-repo/todoapp.git
2. Android Studio에서 열기
Open an Existing Project → 폴더 선택

3. 의존성 설치
build.gradle 자동 동기화

주요 라이브러리

androidx.core

androidx.appcompat

com.google.android.material

4. 실행
에뮬레이터/실기기에 설치 후 실행

5. 정확한 알람 예약 권한 허용
Android 12(API 31)+ 부터
설정 → 앱 → todoapp → "알람 및 알림" → 허용(Allow)

이거 반드시 허용해야 앱이 꺼져 있어도 30초 뒤 알림이 울림!

🔑 주요 코드 설명
알람 예약

java
복사
편집
// AddTaskDialog.java
AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
    // 사용자에게 권한 안내 or 설정 페이지 이동 유도
} else {
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        alarmTime.getTimeInMillis(),
        pendingIntent
    );
}
알람 수신 & 노티

java
복사
편집
// AlarmReceiver.java
public void onReceive(Context context, Intent intent) {
    // POST_NOTIFICATIONS 권한 체크 후 NotificationManagerCompat.notify(...)
}
⚠️ TroubleShooting
알람이 안 울림?

[알람 및 알림]에서 정확한 알람 허용 여부 확인!

[알림 권한]도 ON 상태인지 체크

기기 제조사에 따라 백그라운드 차단 정책(배터리 최적화 등) 해제 필요할 수 있음

📄 License
MIT

💡 참고
Android 13+는 알림 권한(POST_NOTIFICATIONS) &
Android 12+는 정확한 알람 예약(SCHEDULE_EXACT_ALARM)
이 모두 필요합니다.
