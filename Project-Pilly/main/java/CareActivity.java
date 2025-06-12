package com.example.pilly;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CareActivity extends AppCompatActivity {

    private Button btnAddTarget, btnRequest;
    private RecyclerView recyclerTargets;
    private TextView tvEmpty;

    private List<CareTarget> careTargetList = new ArrayList<>();
    private CareTargetAdapter adapter;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private final List<ListenerRegistration> medicineListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);

        BottomNavHelper.bind(this);

        btnAddTarget = findViewById(R.id.btnAddTarget);
        btnRequest = findViewById(R.id.btnRequest);
        recyclerTargets = findViewById(R.id.recyclerTargets);
        tvEmpty = findViewById(R.id.tvEmpty);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        adapter = new CareTargetAdapter(
                this,
                careTargetList,
                new CareTargetAdapter.OnCardListener() {
                    @Override
                    public void onEdit(CareTarget target) {
                        if (target == null) {
                            Log.e("CareActivity", "onEdit: target is null");
                            Toast.makeText(CareActivity.this, "대상 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("CareActivity", "onEdit: target=" + target.name + ", id=" + target.id);
                        showEditDialog(target);
                    }

                    @Override
                    public void onDelete(CareTarget target) {
                        showDeleteConfirm(target);
                    }

                    @Override
                    public void onMedicineCheck(CareTarget target, MedicineRow medicine, int timeDocIndex, int medIdx) {
                        String dateKey = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        String docId = medicine.timeId + "_" + medicine.name;
                        boolean newTaken = !medicine.taken;
                        medicine.taken = newTaken;

                        Map<String, Object> data = new HashMap<>();
                        data.put("status", newTaken);
                        data.put("checkedAt", com.google.firebase.firestore.FieldValue.serverTimestamp());

                        db.collection("users")
                                .document(target.id)
                                .collection("medicineRecords")
                                .document(dateKey)
                                .collection("records")
                                .document(docId)
                                .set(data)
                                .addOnSuccessListener(unused -> adapter.notifyDataSetChanged())
                                .addOnFailureListener(e -> Toast.makeText(CareActivity.this, "업데이트 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                },
                true // enableCheck = true
        );
        recyclerTargets.setLayoutManager(new LinearLayoutManager(this));
        recyclerTargets.setAdapter(adapter);

        btnAddTarget.setOnClickListener(v -> {
            Intent intent = new Intent(CareActivity.this, AddCareActivity.class);
            startActivity(intent);
        });

        btnRequest.setOnClickListener(v -> {
            Intent intent = new Intent(CareActivity.this, ReceivedRequestsActivity.class);
            startActivity(intent);
        });

        loadCareTargetsWithRealtime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (ListenerRegistration reg : medicineListeners) reg.remove();
        medicineListeners.clear();
    }

    private void loadCareTargetsWithRealtime() {
        String myUID = auth.getCurrentUser().getUid();
        db.collection("requests")
                .whereEqualTo("fromUID", myUID)
                .whereEqualTo("status", "accepted")
                .addSnapshotListener((requests, e) -> {
                    careTargetList.clear();
                    for (ListenerRegistration reg : medicineListeners) reg.remove();
                    medicineListeners.clear();

                    if (requests == null || requests.isEmpty()) {
                        showEmptyView();
                        return;
                    }

                    final int[] processed = {0};
                    int total = requests.size();

                    for (DocumentSnapshot doc : requests) {
                        String toUID = doc.getString("toUID");
                        if (toUID == null) {
                            processed[0]++;
                            if (processed[0] == total) showTargetList();
                            continue;
                        }

                        db.collection("users").document(toUID).get()
                                .addOnSuccessListener(userDoc -> {
                                    String name = userDoc.getString("username");
                                    String relation = userDoc.getString("relation");

                                    ListenerRegistration medListener = db.collection("users")
                                            .document(toUID)
                                            .collection("medicine_times")
                                            .addSnapshotListener((medSnapshot, medErr) -> {
                                                List<MedicineRow> medicines = new ArrayList<>();
                                                int timeDocIdx = 0;
                                                if (medSnapshot != null && !medSnapshot.isEmpty()) {
                                                    for (DocumentSnapshot medDoc : medSnapshot) {
                                                        Object itemsObj = medDoc.get("items");
                                                        // 모든 약품을 요일과 상관없이 추가
                                                        if (itemsObj instanceof List) {
                                                            List<?> items = (List<?>) itemsObj;
                                                            for (int i = 0; i < items.size(); i++) {
                                                                Object itemObj = items.get(i);
                                                                if (itemObj instanceof Map) {
                                                                    @SuppressWarnings("unchecked")
                                                                    Map<String, Object> item = (Map<String, Object>) itemObj;
                                                                    String medName = item.get("name") != null ? item.get("name").toString().trim() : "";
                                                                    if (medName.isEmpty()) continue; // 이름 없는 약품은 무시
                                                                    String medTime = item.get("time") != null ? item.get("time").toString() : medDoc.getString("time");
                                                                    boolean taken = item.get("taken") != null && Boolean.TRUE.equals(item.get("taken"));
                                                                    medicines.add(new MedicineRow(medTime, medName, taken, timeDocIdx, i));
                                                                }
                                                            }
                                                        } else if (itemsObj instanceof Map) {
                                                            // 단일 항목인 경우
                                                            @SuppressWarnings("unchecked")
                                                            Map<String, Object> item = (Map<String, Object>) itemsObj;
                                                            String medName = item.get("name") != null ? item.get("name").toString().trim() : "";
                                                            if (!medName.isEmpty()) {
                                                                String medTime = item.get("time") != null ? item.get("time").toString() : medDoc.getString("time");
                                                                boolean taken = item.get("taken") != null && Boolean.TRUE.equals(item.get("taken"));
                                                                medicines.add(new MedicineRow(medTime, medName, taken, timeDocIdx, 0));
                                                            }
                                                        }
                                                        timeDocIdx++;
                                                    }
                                                }

                                                int rate = 0;
                                                if (!medicines.isEmpty()) {
                                                    int cnt = 0;
                                                    for (MedicineRow m : medicines)
                                                        if (m.taken) cnt++;
                                                    rate = (int) ((cnt * 100.0) / medicines.size());
                                                }

                                                CareTarget exist = null;
                                                for (CareTarget t : careTargetList) {
                                                    if (t.id != null && t.id.equals(toUID)) {
                                                        exist = t;
                                                        break;
                                                    }
                                                }

                                                if (exist != null) {
                                                    exist.name = name != null ? name : "";
                                                    exist.relation = relation != null ? relation : "";
                                                    exist.rate = rate;
                                                    exist.medicines = medicines;
                                                    adapter.notifyDataSetChanged();
                                                } else {
                                                    careTargetList.add(new CareTarget(
                                                            toUID,
                                                            name != null ? name : "",
                                                            relation != null ? relation : "",
                                                            rate,
                                                            medicines
                                                    ));
                                                    adapter.notifyDataSetChanged();
                                                }

                                                showTargetList();
                                            });

                                    medicineListeners.add(medListener);
                                    processed[0]++;
                                    if (processed[0] == total) showTargetList();
                                })
                                .addOnFailureListener(err -> {
                                    processed[0]++;
                                    if (processed[0] == total) showTargetList();
                                });
                    }
                });
    }

    private void showEditDialog(CareTarget target) {
        if (target == null) {
            Toast.makeText(this, "대상 정보를 불러오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Context ctx = this;
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_edit_care_target, null, false);
        EditText etName = view.findViewById(R.id.etEditName);
        EditText etRelation = view.findViewById(R.id.etEditRelation);
        
        // null 체크 추가
        String name = target.name != null ? target.name : "";
        String relation = target.relation != null ? target.relation : "";
        
        etName.setText(name);
        etRelation.setText(relation);

        new AlertDialog.Builder(ctx)
                .setTitle("이름/관계 수정")
                .setView(view)
                .setPositiveButton("저장", (dialog, which) -> {
                    String newName = etName.getText().toString().trim();
                    String newRelation = etRelation.getText().toString().trim();
                    if (newName.isEmpty() || newRelation.isEmpty()) {
                        Toast.makeText(ctx, "이름/관계 모두 입력", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db.collection("users").document(target.id)
                            .update("username", newName, "relation", newRelation)
                            .addOnSuccessListener(aVoid -> {
                                target.name = newName;
                                target.relation = newRelation;
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ctx, "수정 완료", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Toast.makeText(ctx, "수정 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showDeleteConfirm(CareTarget target) {
        Context ctx = this;
        new AlertDialog.Builder(ctx)
                .setTitle("연결 해제")
                .setMessage("정말로 '" + target.name + "'님과의 돌봄 연결을 해제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    String myUID = auth.getCurrentUser().getUid();
                    db.collection("requests")
                            .whereEqualTo("fromUID", myUID)
                            .whereEqualTo("toUID", target.id)
                            .whereEqualTo("status", "accepted")
                            .get()
                            .addOnSuccessListener(querySnapshot -> {
                                for (DocumentSnapshot doc : querySnapshot) {
                                    doc.getReference().delete();
                                }
                                Toast.makeText(ctx, "삭제 완료", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> Toast.makeText(ctx, "삭제 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showTargetList() {
        recyclerTargets.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        recyclerTargets.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }
}
