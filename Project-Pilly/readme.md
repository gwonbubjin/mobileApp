# 📱 Pilly - Android 앱 프로젝트

![Pilly Icon](https://img.shields.io/badge/platform-Android-green?logo=android)
![Language](https://img.shields.io/badge/language-Java-blue?logo=java)
![Database](https://img.shields.io/badge/Firebase-Firestore-orange?logo=firebase)

---

## 🧠 소개

**Pilly**는 사용자의 **약 복용 시간 알림**, **복용 체크**, **기록 관리** 기능이 포함된 Android 기반 복약 관리 앱입니다.  
Firebase를 활용하여 사용자 데이터가 실시간으로 저장/관리되며, 깔끔한 UI와 알림 기능으로 복약 습관을 도와줍니다.

---

## 🔧 주요 기능

- ⏰ 아침 / 점심 / 저녁 등 **복용 시간별 알림 설정**
- 💊 **약 이름, 복용량 등록** 및 시간별 리스트 관리
- 🔔 지정 시간에 알림 발송 → 앱 실행 없이 복용 체크 가능
- ✅ 알림에서 "복용 완료" 클릭 시 Firebase에 자동 반영
- 🗂️ 기록, 오늘, 내정보 탭을 포함한 **하단 네비게이션 UI**
- ☁️ Firebase Firestore를 통한 사용자별 데이터 저장

---

## 📂 프로젝트 구성

```bash
📦 Pilly.zip
├── 📁 app/src
│   ├── java/...
│   └── res/layout, drawable, values
├── 📁 Firebase 설정 파일 포함
├── 📄 AndroidManifest.xml
├── 📄 발표자료, README.md 포함
🔽 다운로드
👉 Pilly.zip 다운로드 (Google Drive)

전체 소스 코드, 실행 가능한 APK, 발표자료 포함

🛠️ 개발환경
항목	내용
IDE	Android Studio (Arctic Fox 이상)
언어	Java
DB	Firebase Firestore
빌드 도구	Gradle
테스트 환경	Android API 33+ 에뮬레이터 및 실제 기기

👨‍💻 제작자
권법진

동의과학대학교 컴퓨터소프트웨어과

✉️ gwonbubjin@gmail.com

⚠️ 사용 시 유의사항
앱 사용 시 알림 권한 허용 필요

APK는 최신 Android 버전(API 33+) 기준으로 빌드됨

Firebase 연동 기능은 개인 계정으로 구성되어 있어 직접 배포 시 변경 필요
