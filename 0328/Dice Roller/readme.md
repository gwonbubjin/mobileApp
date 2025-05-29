# 🎲 Dice Roller

버튼을 클릭하면 주사위가 회전 애니메이션과 함께 무작위로 1~6 사이 숫자를 보여주는 **안드로이드 주사위 앱**입니다.

---

## 📱 앱 미리보기

> UI 구성:
- 🎯 상단 제목 "Dice Roller"
- 🎲 주사위 이미지 (기본 1로 시작)
- 🔘 ROLL 버튼 클릭 시 주사위가 회전하며 숫자 변경

---

## 🧩 주요 기능

| 기능 | 설명 |
|------|------|
| 🎲 주사위 굴리기 | ROLL 버튼 클릭 시 1~6 사이 랜덤 숫자 주사위 표시 |
| 🔁 회전 애니메이션 | 클릭 시 주사위 이미지가 360도 회전 |
| 🖼️ 이미지 교체 | `dice_1`부터 `dice_6`까지 주사위 그림 자동 변경 |

---

## 🛠️ 핵심 코드 요약

### `MainActivity.java`

```java
int[] diceImages = {
    R.drawable.dice_1,
    R.drawable.dice_2,
    R.drawable.dice_3,
    R.drawable.dice_4,
    R.drawable.dice_5,
    R.drawable.dice_6
};

btnRoll.setOnClickListener(v -> {
    RotateAnimation rotate = new RotateAnimation(
        0, 360,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    );
    rotate.setDuration(500);
    diceImage.startAnimation(rotate);

    new Handler().postDelayed(() -> {
        int randomIndex = new Random().nextInt(6);
        diceImage.setImageResource(diceImages[randomIndex]);
    }, 500);
});
🎨 UI 구성 (activity_main.xml)
xml
복사
편집
<TextView android:text="Dice Roller" />
<ImageView android:id="@+id/diceImage" android:src="@drawable/dice_1" />
<Button android:id="@+id/btnRoll" android:text="ROLL" />
LinearLayout 중심 정렬 (gravity="center")

주사위 크기: 200dp

버튼 배경색: 보라색 (#673AB7)

📁 프로젝트 구조
bash
복사
편집
DiceRoller/
├── MainActivity.java         # 주사위 회전 및 이미지 변경 로직
├── activity_main.xml         # UI 구성 (텍스트, 이미지, 버튼)
├── res/drawable/dice_1~6     # 주사위 이미지 리소스 (추정)
├── readme.md                 # 설명 문서 (본 파일)
🚀 확장 아이디어
두 개의 주사위 추가 → 합산 결과 표시

주사위 눈 결과 음성 출력

흔들어서 굴리는 기능 (Sensor 연동)

이전 결과 기록 기능


