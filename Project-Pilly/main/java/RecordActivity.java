package com.example.pilly;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView tvSelectedDate, tvStatus, tvCurrentMonth;
    private LinearLayout layoutRecords;
    private LocalDate selectedDate = null;
    private YearMonth currentMonth;
    private FirebaseFirestore db;
    private String uid;

    private List<TimeMedicineGroup> allTimeMedicineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        BottomNavHelper.bind(this);

        // 뷰 초기화
        calendarView = findViewById(R.id.calendarView);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        layoutRecords = findViewById(R.id.layoutRecords);
        tvStatus = findViewById(R.id.tvStatus);
        tvCurrentMonth = findViewById(R.id.tvCurrentMonth);
        ImageView btnPrevMonth = findViewById(R.id.btnPrevMonth);
        ImageView btnNextMonth = findViewById(R.id.btnNextMonth);

        // Firebase 초기화
        db = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // 1. 현재 날짜로 초기화
        selectedDate = LocalDate.now();
        currentMonth = YearMonth.from(selectedDate);

        // 2. 캘린더 범위 설정 (올해 1월부터 12월까지)
        YearMonth startMonth = YearMonth.of(selectedDate.getYear(), 1);
        YearMonth endMonth = YearMonth.of(selectedDate.getYear(), 12);

        // 3. 캘린더 뷰 초기화
        calendarView.setup(startMonth, endMonth, DayOfWeek.SUNDAY);
        calendarView.setDayBinder(new MyMonthDayBinder());

        // 4. UI 초기화
        updateMonthText();

        // 5. 현재 날짜로 설정
        updateSelectedDate(selectedDate);

        // 6. 약간의 지연 후에 한 번 더 업데이트 (UI가 완전히 로드된 후 실행)
        calendarView.postDelayed(() -> {
            try {
                calendarView.scrollToDate(selectedDate);
                calendarView.notifyDateChanged(selectedDate);
                calendarView.notifyCalendarChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 100);

        btnPrevMonth.setOnClickListener(v -> {
            if (currentMonth.getMonthValue() > 1) {
                currentMonth = currentMonth.minusMonths(1);
                calendarView.scrollToMonth(currentMonth);
            }
        });
        btnNextMonth.setOnClickListener(v -> {
            if (currentMonth.getMonthValue() < 12) {
                currentMonth = currentMonth.plusMonths(1);
                calendarView.scrollToMonth(currentMonth);
            }
        });

        calendarView.setMonthScrollListener(month -> {
            currentMonth = month.getYearMonth();
            updateMonthText();
            return kotlin.Unit.INSTANCE;
        });
    }

    private void updateMonthText() {
        tvCurrentMonth.setText(String.format(Locale.KOREA, "%d년 %d월", currentMonth.getYear(), currentMonth.getMonthValue()));
    }

    public void refreshPage() {
        // 현재 날짜로 새로고침
        LocalDate today = LocalDate.now();

        // 선택된 날짜를 현재 날짜로 업데이트
        selectedDate = today;
        currentMonth = YearMonth.from(today);

        // UI 업데이트
        updateMonthText();

        // 캘린더 뷰 강제로 업데이트
        calendarView.post(() -> {
            try {
                // 현재 월로 스크롤
                calendarView.scrollToMonth(currentMonth);

                // 현재 날짜로 스크롤 및 선택
                calendarView.scrollToDate(selectedDate);
                calendarView.notifyDateChanged(selectedDate);
                calendarView.notifyCalendarChanged();

                // 약간의 지연 후에 한 번 더 업데이트 (안전을 위해)
                calendarView.postDelayed(() -> {
                    calendarView.scrollToDate(selectedDate);
                    calendarView.notifyDateChanged(selectedDate);
                    calendarView.notifyCalendarChanged();
                }, 50);

                // 로그 추가 (디버깅용)
                Log.d("RecordActivity", "Page refreshed to: " + selectedDate);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 선택된 날짜의 약품 정보 로드
        loadAllMedicineTimesAndShow(selectedDate);
    }

    private void updateSelectedDate(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }

        // 이전에 선택된 날짜 저장
        LocalDate previousDate = selectedDate;
        selectedDate = date;

        // 날짜 표시 업데이트 (예: 2025.06.02)
        String dateStr = String.format(Locale.KOREA, "%d.%02d.%02d",
            date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        tvSelectedDate.setText(dateStr);

        // 월이 변경된 경우
        YearMonth newMonth = YearMonth.from(date);
        if (!newMonth.equals(currentMonth)) {
            currentMonth = newMonth;
            updateMonthText();
        }

        // UI 업데이트
        calendarView.post(() -> {
            try {
                // 이전 날짜와 새 날짜 모두 업데이트
                if (previousDate != null) {
                    calendarView.notifyDateChanged(previousDate);
                }
                calendarView.notifyDateChanged(selectedDate);

                // 필요한 경우 월 스크롤
                if (!newMonth.equals(YearMonth.from(previousDate != null ? previousDate : LocalDate.now()))) {
                    calendarView.scrollToMonth(currentMonth);
                }

                // 선택된 날짜로 스크롤
                calendarView.scrollToDate(selectedDate);
                calendarView.notifyCalendarChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 약품 정보 로드
        loadAllMedicineTimesAndShow(date);
        Log.d("RecordActivity", "Selected date: " + selectedDate);
    }

    private int parseTimeToInt(String timeStr) {
        if (timeStr == null) return 0;
        try {
            String[] parts = timeStr.split(" ");
            if (parts.length != 2) return 0;
            String ampm = parts[0];
            String[] hm = parts[1].split(":");
            int hour = Integer.parseInt(hm[0]);
            int min = Integer.parseInt(hm[1]);
            if (ampm.equals("오후") && hour < 12) hour += 12;
            if (ampm.equals("오전") && hour == 12) hour = 0;
            return hour * 60 + min;
        } catch (Exception e) {
            return 0;
        }
    }

    private void loadAllMedicineTimesAndShow(LocalDate date) {
        db.collection("users").document(uid).collection("medicine_times").get()
                .addOnSuccessListener(medicineDocs -> {
                    allTimeMedicineList.clear();
                    for (DocumentSnapshot doc : medicineDocs) {
                        String time = doc.getString("time");
                        if (time == null) continue;

                        List<String> names = new ArrayList<>();
                        Object itemsObj = doc.get("items");
                        if (itemsObj instanceof List) {
                            for (Object obj : (List<?>) itemsObj) {
                                if (obj instanceof Map) {
                                    Map<?, ?> itemMap = (Map<?, ?>) obj;
                                    String name = itemMap.get("name") != null ? itemMap.get("name").toString().trim() : "";
                                    if (!name.isEmpty()) names.add(name);
                                }
                            }
                        }
                        allTimeMedicineList.add(new TimeMedicineGroup(time, doc.getId(), names));
                    }
                    allTimeMedicineList.sort((a, b) -> parseTimeToInt(a.time) - parseTimeToInt(b.time));
                    loadRecordsFromFirestore(date);
                })
                .addOnFailureListener(e -> {
                    allTimeMedicineList.clear();
                    showRecordsForDate(new ArrayList<>(), selectedDate);
                });
    }

    private void loadRecordsFromFirestore(LocalDate date) {
        String dateKey = date.toString();
        db.collection("users").document(uid).collection("medicineRecords").document(dateKey)
                .collection("records").get()
                .addOnSuccessListener(query -> {
                    // 기록된 모든 약품 정보를 저장할 맵
                    Map<String, Map<String, Object>> recordMap = new HashMap<>();
                    // 시간대별로 그룹화된 기록을 저장할 맵
                    Map<String, List<MedicineRecord>> timeGroupMap = new HashMap<>();

                    // 1. 모든 기록을 가져와서 맵에 저장
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        String key = doc.getId();
                        String time = doc.getString("time");
                        String medicineName = doc.getString("medicineName");
                        Boolean status = doc.getBoolean("status");
                        String timeId = key.split("_")[0];

                        if (time != null && medicineName != null && status != null) {
                            // 시간대별로 그룹화
                            timeGroupMap.putIfAbsent(time, new ArrayList<>());
                            timeGroupMap.get(time).add(new MedicineRecord(time, medicineName, status, timeId));

                            // 전체 기록 저장 (나중에 중복 확인용)
                            recordMap.put(key, new HashMap<>());
                            recordMap.get(key).put("time", time);
                            recordMap.get(key).put("name", medicineName);
                            recordMap.get(key).put("status", status);
                        }
                    }

                    // 2. 현재 등록된 약품 목록과 비교하여 누락된 기록이 있으면 추가
                    for (TimeMedicineGroup group : allTimeMedicineList) {
                        for (String name : group.names) {
                            String key = group.timeId + "_" + name;
                            if (!recordMap.containsKey(key)) {
                                // 기록이 없는 경우 기본값으로 추가
                                timeGroupMap.putIfAbsent(group.time, new ArrayList<>());
                                timeGroupMap.get(group.time).add(new MedicineRecord(group.time, name, false, group.timeId));
                            }
                        }
                    }

                    // 3. 시간대별로 정렬하여 표시
                    List<MedicineDisplayGroup> displayList = new ArrayList<>();
                    for (Map.Entry<String, List<MedicineRecord>> entry : timeGroupMap.entrySet()) {
                        displayList.add(new MedicineDisplayGroup(entry.getKey(), entry.getValue()));
                    }

                    // 시간 순으로 정렬
                    displayList.sort((a, b) -> parseTimeToInt(a.time) - parseTimeToInt(b.time));
                    showRecordsForDate(displayList, date);
                })
                .addOnFailureListener(e -> {
                    // 실패 시 빈 목록 표시
                    showRecordsForDate(new ArrayList<>(), date);
                });
    }

    private void showRecordsForDate(List<MedicineDisplayGroup> groupList, LocalDate date) {
        layoutRecords.removeAllViews();
        boolean allTaken = true;
        boolean anyData = false;

        boolean isToday = selectedDate.equals(LocalDate.now());
        boolean isFuture = selectedDate.isAfter(LocalDate.now());

        if (isFuture) {
            tvStatus.setVisibility(View.GONE);
            return;
        }

        for (MedicineDisplayGroup group : groupList) {
            TextView tvTime = new TextView(this);
            tvTime.setText("\u23F0 " + group.time);
            tvTime.setTextSize(16);
            tvTime.setPadding(0, 20, 0, 0);
            layoutRecords.addView(tvTime);

            if (group.medicineList.isEmpty()) continue;

            anyData = true;

            for (MedicineRecord rec : group.medicineList) {
                if (isToday) {
                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setPadding(40, 4, 0, 4);
                    row.setGravity(Gravity.CENTER_VERTICAL);

                    TextView tvToggle = new TextView(this);
                    tvToggle.setText(rec.taken ? "\u2705" : "\u274C");
                    tvToggle.setTextSize(20);
                    tvToggle.setPadding(0, 0, 12, 0);

                    TextView tvMed = new TextView(this);
                    tvMed.setText(rec.name);
                    tvMed.setTextSize(16);
                    tvMed.setTextColor(rec.taken ? 0xFF4CAF50 : 0xFFF44336);

                    row.addView(tvToggle);
                    row.addView(tvMed);

                    row.setOnClickListener(v -> {
                        boolean newTaken = !rec.taken;
                        rec.taken = newTaken;
                        tvToggle.setText(newTaken ? "\u2705" : "\u274C");
                        tvMed.setTextColor(newTaken ? 0xFF4CAF50 : 0xFFF44336);

                        String dateKey = selectedDate.toString();
                        String docId = rec.timeId + "_" + rec.name;
                        Map<String, Object> data = new HashMap<>();
                        data.put("status", newTaken);
                        data.put("checkedAt", com.google.firebase.firestore.FieldValue.serverTimestamp());

                        db.collection("users").document(uid).collection("medicineRecords")
                                .document(dateKey).collection("records").document(docId)
                                .set(data, com.google.firebase.firestore.SetOptions.merge())
                                .addOnFailureListener(e -> Toast.makeText(this, "업데이트 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    });

                    layoutRecords.addView(row);

                } else {
                    TextView tvMed = new TextView(this);
                    tvMed.setText((rec.taken ? "\u2705 " : "\u274C ") + rec.name + (rec.taken ? "" : " (복용 안함)"));
                    tvMed.setTextSize(16);
                    tvMed.setPadding(40, 4, 0, 4);
                    tvMed.setTextColor(rec.taken ? 0xFF4CAF50 : 0xFFF44336);
                    layoutRecords.addView(tvMed);
                }
                if (!rec.taken) allTaken = false;
            }
        }

        if (anyData) {
            tvStatus.setVisibility(View.VISIBLE);
            tvStatus.setBackgroundColor(allTaken ? 0xFF6BC48F : 0xFFF44336);
            tvStatus.setText(allTaken ? "복약을 완료했어요!" : "복약을 완료하지 못했어요.");
        } else {
            tvStatus.setVisibility(View.GONE);
        }


    }

    public static class MedicineDisplayGroup {
        public String time;
        public List<MedicineRecord> medicineList;
        public MedicineDisplayGroup(String time, List<MedicineRecord> medicineList) {
            this.time = time;
            this.medicineList = medicineList;
        }
    }

    public static class TimeMedicineGroup {
        public String time, timeId;
        public List<String> names;
        public TimeMedicineGroup(String time, String timeId, List<String> names) {
            this.time = time;
            this.timeId = timeId;
            this.names = names;
        }
    }

    public static class MedicineRecord {
        public String time, name, timeId;
        public boolean taken;
        public MedicineRecord(String time, String name, boolean taken, String timeId) {
            this.time = time;
            this.name = name;
            this.taken = taken;
            this.timeId = timeId;
        }
    }

    class MyMonthDayBinder implements MonthDayBinder<DayViewContainer> {
        @Override
        public DayViewContainer create(View view) {
            return new DayViewContainer(view);
        }
        @Override
        public void bind(DayViewContainer container, CalendarDay day) {
            TextView textView = container.textView;
            LocalDate date = day.getDate();
            textView.setText(String.valueOf(date.getDayOfMonth()));
            textView.setVisibility(View.VISIBLE); // 항상 보이도록 설정

            // 선택된 날짜 스타일
            if (selectedDate != null && date.equals(selectedDate)) {
                textView.setTypeface(null, Typeface.BOLD);
                textView.setBackgroundResource(R.drawable.bg_circle_green);
                textView.setTextColor(0xFFFFFFFF);
            } else {
                // 오늘 날짜 스타일
                if (date.equals(LocalDate.now())) {
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextColor(0xFF4CAF50);
                } else {
                    textView.setTypeface(null, Typeface.NORMAL);
                    textView.setTextColor(0xFF222222);
                }
                textView.setBackground(null);
            }

            // 날짜 클릭 리스너
            textView.setOnClickListener(v -> updateSelectedDate(date));
        }
    }

    static class DayViewContainer extends ViewContainer {
        final TextView textView;
        DayViewContainer(View view) {
            super(view);
            textView = view.findViewById(R.id.tvDayCell);
        }
    }
}
