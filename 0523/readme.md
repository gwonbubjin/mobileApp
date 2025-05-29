# 📝 todo-alarm – 알람 기능 포함 Android 투두앱 (0523)

![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![API](https://img.shields.io/badge/API-31%2B-blue)
![기술](https://img.shields.io/badge/기능-AlarmManager-orange)

---

## 📌 프로젝트 개요

- 이 앱은 **할 일 등록 시 정확하게 30초 뒤 알림이 울리는 Android 투두앱**입니다.
- **앱을 종료하거나 백그라운드** 상태여도 알림이 정상적으로 작동합니다.
- Android 12 이상에서는 `SCHEDULE_EXACT_ALARM` 권한 설정이 필요합니다.

---

## 🚀 주요 기능

- ✅ 할 일 추가/삭제
- 🔔 알람 자동 등록 (추가 시 30초 뒤 알림 발생)
- 📦 SQLite를 이용한 데이터 저장
- 🕰️ AlarmManager로 정확한 알람 구현

---

## 🧠 학습 포인트

- `AlarmManager`와 `PendingIntent`를 활용한 정확한 알림 스케줄링
- `BroadcastReceiver` (`AlarmReceiver.java`)를 통해 Notification 전송
- `SQLiteOpenHelper`로 할 일 저장 및 불러오기 구현
- `AlertDialog`, `EditText`, `RecyclerView` 등 UI 요소 통합

---

## 🔍 SWOT 분석

| 항목 | 분석 |
|------|------|
| **S** | 앱 종료 상태에서도 작동하는 정확한 알람 기능 구현 |
| **W** | 반복 알람/사용자 설정 시간 미지원 |
| **O** | 정기 알림, 알림음 설정, snooze 기능으로 확장 가능 |
| **T** | Android 12 이상에서 권한 이슈 발생 가능성 존재 |

---

## 🛠️ 사용 기술

- Java
- Android XML
- SQLite
- AlarmManager
- NotificationManager
- Android Studio

---


## ✅ 마무리

이 프로젝트는 **알림 기능을 포함한 Android 앱** 구조를 실제로 구현해볼 수 있는 훌륭한 실습 예제입니다.  
실제 서비스에 적용하기 위해선 **사용자 지정 시간**, **반복 주기 설정**, **알림 커스터마이징** 등 기능 확장이 필요합니다.

