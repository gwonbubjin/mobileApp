package com.example.pilly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        updateVisitChallengeIfNeeded();

        // 3초 후 로그인화면으로 이동
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    // 🔁 오늘 날짜 가져오기
    private String getTodayDate() {
        return LocalDate.now().toString(); // yyyy-MM-dd
    }

    // 🔁 어제인지 비교
    private boolean isYesterday(String lastDateStr) {
        try {
            LocalDate last = LocalDate.parse(lastDateStr);
            LocalDate today = LocalDate.now();
            return ChronoUnit.DAYS.between(last, today) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 연속 앱 방문 챌린지 자동 업데이트
    private void updateVisitChallengeIfNeeded() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(uid)
                .collection("challenges")
                .document("visit_3")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.exists()) return;

                    Challenge challenge = documentSnapshot.toObject(Challenge.class);
                    if (challenge == null || challenge.isCompleted()) return;

                    String today = getTodayDate();
                    String last = documentSnapshot.getString("lastSuccessDate");

                    if (last == null || last.isEmpty()) {
                        // 최초 방문 등록
                        db.collection("users").document(uid)
                                .collection("challenges")
                                .document("visit_3")
                                .update("lastSuccessDate", today);
                        return;
                    }

                    if (last.equals(today)) {
                        // 오늘 이미 체크됨
                        return;
                    }

                    if (isYesterday(last)) {
                        int newStreak = challenge.getCurrentStreak() + 1;

                        if (newStreak >= challenge.getTargetDays()) {
                            // 챌린지 완료 처리
                            db.collection("users").document(uid)
                                    .collection("challenges")
                                    .document("visit_3")
                                    .update(
                                            "currentStreak", newStreak,
                                            "lastSuccessDate", today,
                                            "isCompleted", true
                                    );
                        } else {
                            // 정상 진행 중
                            db.collection("users").document(uid)
                                    .collection("challenges")
                                    .document("visit_3")
                                    .update(
                                            "currentStreak", newStreak,
                                            "lastSuccessDate", today
                                    );
                        }

                    } else {
                        // 연속 실패 → streak 리셋
                        db.collection("users").document(uid)
                                .collection("challenges")
                                .document("visit_3")
                                .update(
                                        "currentStreak", 1,
                                        "lastSuccessDate", today
                                );
                    }
                })
                .addOnFailureListener(e -> Log.e("Splash", "챌린지 갱신 실패: ", e));
    }
}
