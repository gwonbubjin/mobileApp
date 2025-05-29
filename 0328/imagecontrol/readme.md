# 🖼️ Image Control App

사용자가 버튼을 클릭하여 이미지의 **스케일타입**, **회전**, **투명도**를 조절할 수 있는 Android 앱입니다.

---

## 📱 앱 구성

> UI 요소:
- 🐶 이미지 뷰 (기본: 강아지 이미지)
- 🎛️ 3개 버튼: Scale Type 변경 / 회전 / 투명도 조절

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🔄 스케일 타입 변경 | CENTER, FIT_XY, CENTER_CROP 등 7가지 스케일 타입 순환 적용 |
| ↩️ 이미지 회전 | 버튼 클릭 시 30도씩 회전 |
| 👻 투명도 조절 | 클릭마다 0.1씩 감소, 0.1 미만이면 다시 1.0으로 리셋 |

---

## 🛠️ 주요 코드 요약

### `MainActivity.java`

```java
private ScaleType[] scaleTypes = {
    ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE,
    ScaleType.FIT_CENTER, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_END
};

btnScaleType.setOnClickListener(v -> {
    scaleTypeIndex = (scaleTypeIndex + 1) % scaleTypes.length;
    imageView.setScaleType(scaleTypes[scaleTypeIndex]);
});

btnRotate.setOnClickListener(v -> {
    currentRotation += 30f;
    imageView.setRotation(currentRotation);
});

btnAlpha.setOnClickListener(v -> {
    currentAlpha -= 0.1f;
    if (currentAlpha < 0.1f) currentAlpha = 1.0f;
    imageView.setAlpha(currentAlpha);
});
🎨 UI 구성 (activity_main.xml)
xml
복사
편집
<ImageView
    android:id="@+id/imageView"
    android:src="@drawable/img_dog"
    android:scaleType="fitCenter"
    android:layout_width="250dp"
    android:layout_height="250dp" />

<Button android:id="@+id/btnScaleType" android:text="Scale Type 변경"/>
<Button android:id="@+id/btnRotate" android:text="회전"/>
<Button android:id="@+id/btnAlpha" android:text="투명도 조절"/>
LinearLayout으로 수직 정렬

이미지뷰 기본 크기: 250dp × 250dp

각 버튼은 수평으로 나란히 배치됨

📁 폴더 구조
bash
복사
편집
ImageControl/
├── MainActivity.java         # 이미지 제어 로직
├── activity_main.xml         # UI 정의
├── res/drawable/img_dog.png  # 표시되는 강아지 이미지 (추정)
├── readme.md                 # 설명 문서 (본 파일)
🚀 발전 방향
여러 이미지 추가 (화살표 버튼으로 이미지 전환)

애니메이션 효과 적용 (fade in/out, slide 등)

확대/축소(Pinch Zoom) 기능 추가

터치 이벤트로 회전/이동 제어


