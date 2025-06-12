package com.example.pilly;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMedicineTimeActivity extends AppCompatActivity {

    private TextView tvAm, tvPm;
    private EditText etHour, etMinute;
    private LinearLayout layoutDays, layoutMedicineList;
    private Button btnAddMedicine, btnSave;
    private ImageButton btnDelete, btnBack;
    private final ArrayList<String> selectedDays = new ArrayList<>();
    private final ArrayList<MedicineItem> addedMedicines = new ArrayList<>();
    private String selectedAmPm = "오전";
    private final String[] days = {"일", "월", "화", "수", "목", "금", "토"};
    private final ArrayList<TextView> dayViews = new ArrayList<>();
    private FirebaseFirestore db;
    private String uid;
    private String docId; // 수정 모드 시 문서 ID 저장

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_time);

        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // 수정 모드인지 확인
        if (getIntent().hasExtra("docId")) {
            docId = getIntent().getStringExtra("docId");
            String time = getIntent().getStringExtra("time");
            ArrayList<String> days = getIntent().getStringArrayListExtra("days");
            ArrayList<String> medicineNames = getIntent().getStringArrayListExtra("medicineNames");
            ArrayList<String> medicineAmounts = getIntent().getStringArrayListExtra("medicineAmounts");

            // 시간 설정
            if (time != null && !time.isEmpty()) {
                try {
                    // 시간 형식이 "오전/오후 HH:MM" 또는 "HH:MM" 형식일 수 있음
                    String[] timeParts = time.split(" ");
                    String timeStr;
                    
                    if (timeParts.length >= 2 && (timeParts[0].equals("오전") || timeParts[0].equals("오후"))) {
                        // "오전/오후 HH:MM" 형식
                        String ampm = timeParts[0];
                        timeStr = timeParts[1];
                        setAmPm(ampm.equals("오전"));
                    } else {
                        // "HH:MM" 형식 (24시간제)
                        timeStr = time;
                    }
                    
                    // 시간과 분 분리
                    String[] hm = timeStr.split(":");
                    if (hm.length == 2) {
                        etHour.setText(hm[0]);
                        etMinute.setText(hm[1]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 기본값 설정
                    etHour.setText("");
                    etMinute.setText("");
                }
            }

            // 요일 설정
            if (days != null) {
                for (String day : days) {
                    int idx = java.util.Arrays.asList(this.days).indexOf(day);
                    if (idx >= 0) {
                        toggleDay(idx);
                    }
                }
            }

            // 약품 목록 설정
            if (medicineNames != null && medicineAmounts != null) {
                for (int i = 0; i < medicineNames.size(); i++) {
                    String name = medicineNames.get(i);
                    String amount = i < medicineAmounts.size() ? medicineAmounts.get(i) : "";
                    addMedicineItem(name, amount);
                }
            }

            // 제목 변경
            ((TextView)findViewById(R.id.tvTitle)).setText("약 수정");
            btnSave.setText("수정");
            btnDelete.setVisibility(android.view.View.VISIBLE);
        }

        tvAm = findViewById(R.id.tvAm);
        tvPm = findViewById(R.id.tvPm);
        etHour = findViewById(R.id.etHour);
        etMinute = findViewById(R.id.etMinute);
        layoutDays = findViewById(R.id.layoutDays);
        layoutMedicineList = findViewById(R.id.layoutMedicineList);

        btnAddMedicine = findViewById(R.id.btnAddMedicine);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        etHour.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 12) });
        etMinute.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 59) });

        tvAm.setOnClickListener(v -> setAmPm(true));
        tvPm.setOnClickListener(v -> setAmPm(false));
        setAmPm(true);

        layoutDays.removeAllViews();
        for (int i = 0; i < days.length; i++) {
            final int idx = i;
            TextView day = new TextView(this);
            day.setText(days[i]);
            day.setTextSize(16f);
            day.setGravity(Gravity.CENTER);
            day.setBackgroundResource(R.drawable.bg_btn_outline);
            day.setTextColor(Color.parseColor("#A9A9A9"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            lp.setMargins(4, 0, 4, 0);
            day.setLayoutParams(lp);
            day.setPadding(0, 18, 0, 18);
            day.setOnClickListener(v -> toggleDay(idx));
            layoutDays.addView(day);
            dayViews.add(day);
        }

        btnAddMedicine.setOnClickListener(v -> showAddMedicineDialog());

        btnSave.setOnClickListener(v -> saveMedicineTime());
        
        btnDelete.setOnClickListener(v -> {
            if (docId != null) {
                new android.app.AlertDialog.Builder(this)
                    .setTitle("삭제 확인")
                    .setMessage("이 약 시간을 삭제하시겠습니까?\n(기록은 유지됩니다)")
                    .setPositiveButton("삭제", (dialog, which) -> {
                        db.collection("users")
                            .document(uid)
                            .collection("medicine_times")
                            .document(docId)
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "약 시간이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> 
                                Toast.makeText(this, "삭제 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                    })
                    .setNegativeButton("취소", null)
                    .show();
            }
        });
        
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void saveMedicineTime() {
        if (selectedDays.isEmpty()) {
            Toast.makeText(this, "요일을 선택해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etHour.getText().toString().trim().isEmpty() || etMinute.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "시간을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<MedicineItem> validList = new ArrayList<>();
        for (MedicineItem m : addedMedicines) {
            if (m.getName() != null && !m.getName().trim().isEmpty()
                    && m.getAmount() != null && !m.getAmount().trim().isEmpty()) {
                validList.add(m);
            }
        }

        if (validList.isEmpty()) {
            Toast.makeText(this, "약 이름과 복용량을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = Integer.parseInt(etHour.getText().toString());
        if (selectedAmPm.equals("오후") && hour < 12) {
            hour += 12;
        } else if (selectedAmPm.equals("오전") && hour == 12) {
            hour = 0;
        }
        String timeStr = String.format("%02d:%02d", hour, Integer.parseInt(etMinute.getText().toString()));

        Map<String, Object> data = new HashMap<>();
        data.put("time", timeStr);
        data.put("ampm", selectedAmPm);
        data.put("days", selectedDays);

        ArrayList<Map<String, Object>> itemsList = new ArrayList<>();
        for (MedicineItem m : validList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("name", m.getName());
            itemMap.put("amount", m.getAmount());
            itemMap.put("taken", false);
            itemsList.add(itemMap);
        }
        data.put("items", itemsList);

        if (docId == null) {
            // 새로 추가하는 경우
            docId = db.collection("users").document(uid)
                    .collection("medicine_times").document().getId();
        }

        db.collection("users").document(uid)
                .collection("medicine_times").document(docId)
                .set(data)
                .addOnSuccessListener(doc -> {
                    // 알람 등록
                    AlarmHelper.setAlarm(this, timeStr, selectedAmPm, docId);

                    Toast.makeText(this, docId == null ? "저장했습니다!" : "수정했습니다!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, (docId == null ? "저장" : "수정") + " 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void setAmPm(boolean isAm) {
        selectedAmPm = isAm ? "오전" : "오후";
        tvAm.setBackgroundResource(isAm ? R.drawable.bg_selected : R.drawable.bg_btn_outline);
        tvPm.setBackgroundResource(isAm ? R.drawable.bg_btn_outline : R.drawable.bg_selected);
        tvAm.setTextColor(ContextCompat.getColor(this, isAm ? android.R.color.white : R.color.main_green));
        tvPm.setTextColor(ContextCompat.getColor(this, isAm ? R.color.main_green : android.R.color.white));
    }

    private void toggleDay(int idx) {
        String d = days[idx];
        TextView tv = dayViews.get(idx);
        if (selectedDays.contains(d)) {
            selectedDays.remove(d);
            tv.setBackgroundResource(R.drawable.bg_btn_outline);
            tv.setTextColor(Color.parseColor("#A9A9A9"));
        } else {
            selectedDays.add(d);
            tv.setBackgroundResource(R.drawable.bg_selected);
            tv.setTextColor(Color.WHITE);
        }
    }

    private void showAddMedicineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("약 정보 입력");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);

        final EditText editName = new EditText(this);
        editName.setHint("약 이름");
        editName.setPadding(pad, pad, pad, pad);

        final EditText editDose = new EditText(this);
        editDose.setHint("복용량 (예: 500mg)");
        editDose.setPadding(pad, pad, pad, pad);

        layout.addView(editName);
        layout.addView(editDose);

        builder.setView(layout);
        builder.setPositiveButton("추가", (dialog, which) -> {
            String name = editName.getText().toString().trim();
            String dose = editDose.getText().toString().trim();
            if (!name.isEmpty() && !dose.isEmpty()) {
                MedicineItem m = new MedicineItem(name, dose, false);
                addedMedicines.add(m);
                addMedicineCardToLayout(m);
            } else {
                Toast.makeText(this, "약 이름과 복용량을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void addMedicineItem(String name, String amount) {
        MedicineItem item = new MedicineItem(name, amount, false);
        addedMedicines.add(item);
        addMedicineCardToLayout(item);
    }

    private void addMedicineCardToLayout(MedicineItem item) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setBackgroundResource(R.drawable.bg_btn_outline);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (56 * getResources().getDisplayMetrics().density)
        );
        lp.setMargins(0, 0, 0, (int) (14 * getResources().getDisplayMetrics().density));
        card.setLayoutParams(lp);
        card.setPadding((int) (16 * getResources().getDisplayMetrics().density), 0, (int) (16 * getResources().getDisplayMetrics().density), 0);

        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_medicine);
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(
                (int) (24 * getResources().getDisplayMetrics().density),
                (int) (24 * getResources().getDisplayMetrics().density)
        );
        iconLp.setMargins(0, 0, (int) (12 * getResources().getDisplayMetrics().density), 0);
        icon.setLayoutParams(iconLp);

        TextView tvName = new TextView(this);
        tvName.setText(item.getName());
        tvName.setTextSize(18f);
        tvName.setTextColor(Color.parseColor("#222222"));
        tvName.setTypeface(tvName.getTypeface(), Typeface.BOLD);
        LinearLayout.LayoutParams nameLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        tvName.setLayoutParams(nameLp);

        TextView tvDose = new TextView(this);
        tvDose.setText(item.getAmount());
        tvDose.setTextSize(16f);
        tvDose.setTextColor(Color.parseColor("#45A37F"));
        tvDose.setTypeface(tvDose.getTypeface(), Typeface.BOLD);

        card.addView(icon);
        card.addView(tvName);
        card.addView(tvDose);

        layoutMedicineList.addView(card);
    }
}