package com.example.pilly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String docId = intent.getStringExtra("docId");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String dateKey = LocalDate.now().toString();

        DocumentReference docRef = db.collection("users").document(uid)
                .collection("medicine_times").document(docId);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) documentSnapshot.get("items");
                if (items != null) {

                    // ✅ [1] 메인페이지 반영용 → taken = true 로 갱신
                    for (Map<String, Object> item : items) {
                        item.put("taken", true);
                    }
                    Map<String, Object> timeUpdate = new HashMap<>();
                    timeUpdate.put("items", items);
                    docRef.set(timeUpdate, SetOptions.merge()); // 메인화면 반영

                    // ✅ [2] 기록탭/챌린지용 → medicineRecords에도 저장
                    for (Map<String, Object> item : items) {
                        String name = item.get("name").toString();
                        Map<String, Object> record = new HashMap<>();
                        record.put("time", documentSnapshot.getString("time"));
                        record.put("medicineName", name);
                        record.put("status", true);
                        record.put("checkedAt", com.google.firebase.firestore.FieldValue.serverTimestamp());

                        String recordDocId = docId + "_" + name;

                        db.collection("users").document(uid)
                                .collection("medicineRecords").document(dateKey)
                                .collection("records").document(recordDocId)
                                .set(record, SetOptions.merge());
                    }

                    Toast.makeText(context, "복용 완료 처리됨!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "약 정보 불러오기 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
