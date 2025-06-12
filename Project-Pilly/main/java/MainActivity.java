package com.example.pilly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQ_ADD_MEDICINE = 1001;

    private RecyclerView recyclerMedicine;
    private LinearLayout layoutEmpty;
    private Button btnAddTime;
    private ArrayList<MedicineTime> medicineTimeList;
    private MedicineCardAdapter adapter;

    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }


        // ✅ JVM 타임존을 한국으로 고정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavHelper.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        uid = user.getUid();
        db = FirebaseFirestore.getInstance();

        recyclerMedicine = findViewById(R.id.recyclerMedicine);
        layoutEmpty = findViewById(R.id.layout_empty);
        btnAddTime = findViewById(R.id.btnAddTime);
        medicineTimeList = new ArrayList<>();

        recyclerMedicine.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineCardAdapter(
                medicineTimeList, this, db, uid,
                (medicineTime, position) -> {
                    Intent intent = new Intent(MainActivity.this, EditMedicineTimeActivity.class);
                    String time = medicineTime.getTime();
                    String[] parts = time.split(":");
                    if (parts.length == 2) {
                        int hour = Integer.parseInt(parts[0]);
                        int minute = Integer.parseInt(parts[1]);
                        String amPm = (hour < 12) ? "오전" : "오후";
                        if (hour > 12) hour -= 12;
                        if (hour == 0) hour = 12;
                        intent.putExtra("amPm", amPm);
                        intent.putExtra("hour", hour);
                        intent.putExtra("minute", minute);
                    }
                    intent.putExtra("docId", medicineTime.getDocId());
                    intent.putExtra("position", position);
                    intent.putStringArrayListExtra("days", medicineTime.getDays());
                    intent.putExtra("items", medicineTime.getItems());
                    startActivityForResult(intent, REQ_ADD_MEDICINE);
                },
                (medicineTime, item, timeDocIndex, medIndex) -> {},
                (medicineTime, position) -> {
                    new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("삭제 확인")
                            .setMessage("이 약 시간을 삭제하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                String docId = medicineTime.getDocId();
                                if (docId != null && !docId.isEmpty()) {
                                    db.collection("users").document(uid)
                                            .collection("medicine_times")
                                            .document(docId)
                                            .delete()
                                            .addOnSuccessListener(aVoid -> {
                                                if (position >= 0 && position < medicineTimeList.size()) {
                                                    medicineTimeList.remove(position);
                                                    adapter.notifyItemRemoved(position);
                                                    updateView();
                                                }
                                                Toast.makeText(MainActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.e("MainActivity", "삭제 실패", e);
                                                Toast.makeText(MainActivity.this, "삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                            });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                }
        );
        recyclerMedicine.setAdapter(adapter);

        btnAddTime.setOnClickListener(v ->
                startActivityForResult(new Intent(this, AddMedicineTimeActivity.class), REQ_ADD_MEDICINE));

        listenToMedicines();
    }

    private void listenToMedicines() {
        resetTakenStatusIfNeeded();
        setupMedicineListener();
    }

    private void resetTakenStatusIfNeeded() {
        Log.d(TAG, "System.currentTimeMillis(): " + new Date(System.currentTimeMillis()));
        Log.d(TAG, "Calendar.getInstance(): " + Calendar.getInstance().getTime());
        Log.d(TAG, "LocalDate.now(): " + LocalDate.now());
        Log.d(TAG, "LocalDateTime.now(): " + LocalDateTime.now());
        Log.d(TAG, "TimeZone: " + TimeZone.getDefault().getID());

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String lastResetDate = prefs.getString("last_reset_date", "");
        String today = LocalDate.now().toString();

        Log.d(TAG, "resetTakenStatusIfNeeded - Last reset date: " + lastResetDate);
        Log.d(TAG, "resetTakenStatusIfNeeded - Today: " + today);

        if (!lastResetDate.equals(today)) {
            db.collection("users").document(uid).collection("medicine_times")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        WriteBatch batch = db.batch();
                        for (DocumentSnapshot doc : querySnapshot) {
                            List<Map<String, Object>> items = (List<Map<String, Object>>) doc.get("items");
                            if (items != null) {
                                for (Map<String, Object> item : items) {
                                    item.put("taken", false);
                                }
                                batch.update(doc.getReference(), "items", items);
                            }
                        }
                        batch.commit().addOnSuccessListener(aVoid -> {
                            prefs.edit().putString("last_reset_date", today).apply();
                            setupMedicineListener();
                        });
                    });
        } else {
            setupMedicineListener();
        }
    }

    private void setupMedicineListener() {
        LocalDate currentDate = LocalDate.now();
        Log.d(TAG, "Current date: " + currentDate);
        Log.d(TAG, "Current day of week: " + currentDate.getDayOfWeek());

        db.collection("users").document(uid).collection("medicine_times")
                .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null || querySnapshot == null) {
                        Log.e("MainActivity", "Firestore 리스너 오류", error);
                        return;
                    }

                    Log.d(TAG, "Firebase snapshot received at: " + LocalDateTime.now());

                    Map<String, MedicineTime> mergedMap = new LinkedHashMap<>();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        String time = doc.getString("time");
                        if (time == null) continue;

                        MedicineTime mt = new MedicineTime();
                        mt.setTime(time);
                        mt.setDocId(doc.getId());

                        Object daysObj = doc.get("days");
                        if (daysObj instanceof List) {
                            mt.setDays(new ArrayList<>((List<String>) daysObj));
                        }

                        Object itemsObj = doc.get("items");
                        if (itemsObj instanceof List) {
                            List<MedicineItem> items = new ArrayList<>();
                            for (Object obj : (List<?>) itemsObj) {
                                if (obj instanceof Map) {
                                    Map<String, Object> map = (Map<String, Object>) obj;
                                    String name = (String) map.get("name");
                                    String amount = (String) map.get("amount");
                                    boolean taken = Boolean.TRUE.equals(map.get("taken"));
                                    if (name != null && !name.trim().isEmpty()) {
                                        items.add(new MedicineItem(name, amount, taken));
                                    }
                                }
                            }
                            mt.setItems(new ArrayList<>(items));
                        }

                        mergedMap.put(time, mt);
                    }

                    List<MedicineTime> loadedList = new ArrayList<>(mergedMap.values());
                    loadedList.sort(Comparator.comparingInt(a -> parseTimeToMinutes(a.getTime())));
                    medicineTimeList.clear();
                    medicineTimeList.addAll(loadedList);

                    adapter.notifyDataSetChanged();
                    updateView();
                });
    }

    private int parseTimeToMinutes(String time) {
        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int min = Integer.parseInt(parts[1]);
            return hour * 60 + min;
        } catch (Exception e) {
            return 0;
        }
    }

    private void updateView() {
        boolean hasData = !medicineTimeList.isEmpty();
        layoutEmpty.setVisibility(hasData ? LinearLayout.GONE : LinearLayout.VISIBLE);
        recyclerMedicine.setVisibility(hasData ? RecyclerView.VISIBLE : RecyclerView.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ADD_MEDICINE && resultCode == RESULT_OK && data != null) {
            boolean isDelete = data.getBooleanExtra("delete", false);
            String docId = data.getStringExtra("docId");
            int position = data.getIntExtra("position", -1);

            if (isDelete) {
                if (docId != null && !docId.isEmpty()) {
                    db.collection("users").document(uid)
                            .collection("medicine_times")
                            .document(docId)
                            .delete()
                            .addOnSuccessListener(v -> listenToMedicines())
                            .addOnFailureListener(e -> {
                                Log.e("MainActivity", "삭제 실패", e);
                                Toast.makeText(this, "삭제 중 오류", Toast.LENGTH_SHORT).show();
                            });
                } else if (position >= 0 && position < medicineTimeList.size()) {
                    medicineTimeList.remove(position);
                    adapter.notifyItemRemoved(position);
                    updateView();
                }
                return;
            }

            String time = data.getStringExtra("time");
            ArrayList<String> days = data.getStringArrayListExtra("days");
            ArrayList<MedicineItem> items = (ArrayList<MedicineItem>) data.getSerializableExtra("items");

            if (time == null || days == null || items == null) return;

            MedicineTime medicineTime = new MedicineTime();
            medicineTime.setTime(time);
            medicineTime.setDays(days);
            medicineTime.setItems(items);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("time", time);
            dataMap.put("days", days);

            List<Map<String, Object>> itemList = new ArrayList<>();
            for (MedicineItem item : items) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("name", item.getName());
                itemMap.put("amount", item.getAmount());
                itemMap.put("taken", item.isTaken());
                itemList.add(itemMap);
            }
            dataMap.put("items", itemList);

            if (docId != null && !docId.isEmpty()) {
                db.collection("users").document(uid)
                        .collection("medicine_times")
                        .document(docId)
                        .update(dataMap)
                        .addOnSuccessListener(v -> {
                            if (position >= 0 && position < medicineTimeList.size()) {
                                medicineTime.setDocId(docId);
                                medicineTimeList.set(position, medicineTime);
                                adapter.notifyItemChanged(position);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("MainActivity", "업데이트 실패", e);
                            Toast.makeText(this, "업데이트 오류", Toast.LENGTH_SHORT).show();
                        });
            } else {
                db.collection("users").document(uid)
                        .collection("medicine_times")
                        .add(dataMap)
                        .addOnSuccessListener(ref -> {
                            medicineTime.setDocId(ref.getId());
                            medicineTimeList.add(medicineTime);
                            adapter.notifyItemInserted(medicineTimeList.size() - 1);
                            updateView();
                        })
                        .addOnFailureListener(e -> {
                            Log.e("MainActivity", "추가 실패", e);
                            Toast.makeText(this, "추가 오류", Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }

    public void refreshPage() {
        adapter.notifyDataSetChanged();
    }
}
