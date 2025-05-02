
# 📱 Android ViewPager 이미지 갤러리 앱

이미지와 제목을 함께 보여주는 **슬라이드형 이미지 갤러리 앱**입니다.  
`ViewPager`를 사용하여 사용자가 좌우로 스와이프하며 이미지를 넘길 수 있습니다.

---

## ✅ 기능 소개

- ViewPager를 이용한 이미지 슬라이드
- 각 이미지에 해당하는 텍스트 제목 표시
- XML 레이아웃과 커스텀 PagerAdapter 구성

---

## 🖼️ 화면 예시

| 이미지1 (스포츠) | 이미지2 (RadioWorld) | 이미지3 (야구입문) |
|------------------|----------------------|--------------------|
| ![스포츠](./app/src/main/res/drawable/sports.png) | ![RadioWorld](./app/src/main/res/drawable/radioworld.png) | ![야구](./app/src/main/res/drawable/baseball.png) |

---

## 🗂️ 프로젝트 구조

├── MainActivity.java
├── MyPagerAdapter.java
├── res/
│ ├── layout/
│ │ ├── activity_main.xml
│ │ └── pager_item.xml
│ └── drawable/
│ ├── sports.png
│ ├── radioworld.png
│ └── baseball.png

yaml
복사
편집

---

## 🛠️ 주요 코드

### `MainActivity.java`
```java
int[] images = { R.drawable.sports, R.drawable.radioworld, R.drawable.baseball };
String[] titles = { "스포츠 뉴스", "RadioWorld", "야구 입문 가이드" };
pager_item.xml
xml
복사
편집
<ImageView android:id="@+id/imageView" ... />
<TextView android:id="@+id/titleText" ... />
▶️ 실행 방법
Android Studio로 프로젝트 열기

drawable 폴더에 이미지 추가 (소문자 이름만 가능)

Run 버튼 클릭 or 에뮬레이터에서 실행

💡 참고사항
ViewPager2로 확장도 가능

이미지 이름은 반드시 소문자/숫자/언더바만 사용해야 함 (Baseball.png ❌, baseball.png ✅)

자동 슬라이드 기능은 Handler로 추가 구현 가능

🙋‍♂️ 만든 이유
뷰페이저 연습용으로 제작

이미지 갤러리와 텍스트를 함께 구성하는 앱 구조 학습
