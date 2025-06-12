package com.example.pilly;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChallengeActivity extends AppCompatActivity {

    private RecyclerView rvActiveChallenges, rvCompletedChallenges;
    private ArrayList<Challenge> activeChallengeList = new ArrayList<>();
    private ArrayList<Challenge> completedChallengeList = new ArrayList<>();
    private ChallengeAdapter activeAdapter, completedAdapter;

    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        BottomNavHelper.bind(this);

        rvActiveChallenges = findViewById(R.id.rvActiveChallenges);
        rvCompletedChallenges = findViewById(R.id.rvCompletedChallenges);

        rvActiveChallenges.setLayoutManager(new LinearLayoutManager(this));
        rvCompletedChallenges.setLayoutManager(new LinearLayoutManager(this));

        activeAdapter = new ChallengeAdapter(this, activeChallengeList, false);
        completedAdapter = new ChallengeAdapter(this, completedChallengeList, true);

        rvActiveChallenges.setAdapter(activeAdapter);
        rvCompletedChallenges.setAdapter(completedAdapter);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadChallengesFromFirestore();
    }

    private void loadChallengesFromFirestore() {
        db.collection("users")
                .document(uid)
                .collection("challenges")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    activeChallengeList.clear();
                    completedChallengeList.clear();

                    if (queryDocumentSnapshots.isEmpty()) {
                        createInitialChallenges();
                        return;
                    }

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        Challenge challenge = doc.toObject(Challenge.class);
                        if (challenge != null) {
                            if (challenge.isCompleted() && doc.getId().startsWith("med_")) {
                                completedChallengeList.add(challenge);

                                String currentId = doc.getId();
                                String nextId = getNextChallengeId(currentId);

                                if (nextId != null) {
                                    int continuedStreak = challenge.getCurrentStreak();
                                    int nextTargetDays = extractTargetDays(nextId);

                                    db.collection("users")
                                            .document(uid)
                                            .collection("challenges")
                                            .document(nextId)
                                            .get()
                                            .addOnSuccessListener(nextDoc -> {
                                                if (!nextDoc.exists()) {
                                                    Challenge nextChallenge = new Challenge(
                                                            "연속 복약 성공하기",
                                                            nextTargetDays,
                                                            continuedStreak,
                                                            false,
                                                            ""
                                                    );
                                                    db.collection("users")
                                                            .document(uid)
                                                            .collection("challenges")
                                                            .document(nextId)
                                                            .set(nextChallenge)
                                                            .addOnSuccessListener(v2 -> {
                                                                Log.d("Challenge", nextId + " 챌린지 자동 생성됨");
                                                                activeChallengeList.add(nextChallenge);
                                                                activeAdapter.notifyDataSetChanged();
                                                            });
                                                }
                                            });
                                }
                            } else {
                                activeChallengeList.add(challenge);
                            }
                        }
                    }

                    activeAdapter.notifyDataSetChanged();
                    completedAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "챌린지 불러오기 실패: ", e));
    }

    private void createInitialChallenges() {
        Challenge visitChallenge = new Challenge("연속 앱 방문하기", 3, 1, false, "");
        Challenge medChallenge = new Challenge("연속 복약 성공하기", 3, 0, false, "");

        db.collection("users").document(uid).collection("challenges")
                .document("visit_3")
                .set(visitChallenge);

        db.collection("users").document(uid).collection("challenges")
                .document("med_3")
                .set(medChallenge);

        activeChallengeList.add(visitChallenge);
        activeChallengeList.add(medChallenge);
        activeAdapter.notifyDataSetChanged();
    }

    // 다음 챌린지 ID 계산: med_3 → med_6 → med_12 → med_24 → ...
    private String getNextChallengeId(String currentId) {
        if (!currentId.startsWith("med_")) return null;
        try {
            int currentDay = Integer.parseInt(currentId.replace("med_", ""));
            int nextDay = currentDay < 30 ? currentDay * 2 : currentDay + 30;
            return "med_" + nextDay;
        } catch (Exception e) {
            return null;
        }
    }

    // 챌린지 ID에서 숫자만 추출
    private int extractTargetDays(String id) {
        try {
            return Integer.parseInt(id.replace("med_", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}
