# 🗓️ Android 실습 프로젝트 모음 (0516)

> 이번 실습에서는 `파일 기반 메모장 앱`과 `SQLite 기반 할 일 관리 앱`을 개발하였습니다.  
> 각각 **파일 입출력**, **메모 CRUD**, **투두리스트 UI/DB 연동** 등을 통해 앱 실전 기능을 학습하였습니다.

---

## 📁 프로젝트 구성 요약

| 폴더명 | 핵심 기능 요약 |
|--------|----------------|
| `darknotepad` | 파일 저장 기반 다크모드 메모장 (편집/삭제 포함) |
| `todoapp` | SQLite 기반 할 일 등록/삭제 앱 (5월 9일과 다른 구조) |

---

## 📌 프로젝트별 설명 + SWOT 분석

### 1. `darknotepad` – 다크모드 메모장 앱

**📌 구현 기능**
- 메모 추가 및 삭제 (리스트에 표시)
- `NoteEditorActivity`에서 메모 작성/수정
- 내부 저장소 파일 기반 저장 (`FileStorageHelper.java`)
- 다크 테마 스타일 적용

**🧠 학습 포인트**
- 파일 입출력 (`FileOutputStream`, `FileInputStream`)
- `Intent`로 메모 내용 전달 및 결과 반환 처리
- 사용자 정의 클래스 (`Note`) 기반 List 구성

**🔍 SWOT 분석**
- **S:** DB 없이 파일 기반 저장 방식 실습 가능  
- **W:** 동시 메모 수정 불가, 백업 기능 없음  
- **O:** 클라우드 저장 기능, 알림 연동 등 확장 여지 큼  
- **T:** 파일 이름 중복/손상 등 예외 처리 주의 필요

---

### 2. `todoapp` – SQLite 할 일 관리 앱

**📌 구현 기능**
- 할 일 추가 다이얼로그
- SQLite 연동 (자동 ID, CRUD 구현)
- 리스트뷰 커스터마이징 (`TaskAdapter`)

**🧠 학습 포인트**
- `SQLiteOpenHelper`로 데이터베이스 관리
- `RecyclerView` 없이 `ListView`로 UI 구성
- `ContentValues`, `Cursor`를 통한 DB 조작

**🔍 SWOT 분석**
- **S:** 경량화된 DB 앱 구성법 학습 가능  
- **W:** UI가 RecyclerView에 비해 제약적  
- **O:** 날짜 기반 분류, 필터링 기능 추가 가능  
- **T:** ListView 방식은 대용량 데이터에 비효율적

---

## ✅ 마무리

- 이번 실습을 통해 **파일 기반 저장**과 **SQLite 연동 방식**의 차이점을 실감할 수 있었으며,  
- 추후에는 **클라우드 저장소**, **Dark/Light Theme 전환**, **알림 기능**, **데이터 동기화** 등을 추가하여 실제 사용자 환경에 가까운 앱을 제작할 수 있습니다.

---

## 🛠️ 사용 기술

- Java
- Android XML
- SQLite (todoapp)
- File I/O (darknotepad)
- Android Studio
