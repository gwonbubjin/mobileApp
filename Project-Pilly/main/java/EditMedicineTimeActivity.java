package com.example.pilly;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class EditMedicineTimeActivity extends AppCompatActivity {

    private Button btnAm, btnPm, btnSave, btnAddMedicine;
    private EditText etHour, etMinute;
    private LinearLayout layoutDays, layoutMedicineList;
    private ArrayList<String> selectedDays = new ArrayList<>();
    private String amPm = "오전";
    private String docId;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medicine_time_activity);

        btnAm = findViewById(R.id.btnAm);
        btnPm = findViewById(R.id.btnPm);
        btnSave = findViewById(R.id.btnSave);
        btnAddMedicine = findViewById(R.id.btnAddMedicine);
        etHour = findViewById(R.id.etHour);
        etMinute = findViewById(R.id.etMinute);
        layoutDays = findViewById(R.id.layoutDays);
        layoutMedicineList = findViewById(R.id.layoutMedicineList);

        etHour.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 12) });
        etMinute.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 59) });

        // 인텐트 데이터 받기
        Intent intent = getIntent();
        amPm = intent.getStringExtra("amPm") != null ? intent.getStringExtra("amPm") : "오전";
        docId = intent.getStringExtra("docId");
        position = intent.getIntExtra("position", -1);
        int oldHour = intent.getIntExtra("hour", 8);
        int oldMinute = intent.getIntExtra("minute", 0);
        ArrayList<String> oldDays = intent.getStringArrayListExtra("days");
        @SuppressWarnings("unchecked")
        ArrayList<MedicineItem> oldMedicines = (ArrayList<MedicineItem>) intent.getSerializableExtra("items");
        if (oldMedicines == null) oldMedicines = new ArrayList<>();

        setAmPmUI("오후".equals(amPm));
        etHour.setText(String.valueOf(oldHour));
        etMinute.setText(String.format("%02d", oldMinute));

        // 요일 UI
        String[] daysKor = {"일","월","화","수","목","금","토"};
        for (String day : daysKor) {
            Button btn = new Button(this);
            btn.setText(day);
            btn.setBackgroundResource(R.drawable.btn_white_circle);
            btn.setTextColor(getColor(R.color.gray));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            lp.setMargins(8, 0, 8, 0);
            btn.setLayoutParams(lp);

            if (oldDays != null && oldDays.contains(day)) {
                btn.setBackgroundResource(R.drawable.btn_green_circle);
                btn.setTextColor(getColor(R.color.white));
                selectedDays.add(day);
            }

            btn.setOnClickListener(v -> {
                if (selectedDays.contains(day)) {
                    btn.setBackgroundResource(R.drawable.btn_white_circle);
                    btn.setTextColor(getColor(R.color.gray));
                    selectedDays.remove(day);
                } else {
                    btn.setBackgroundResource(R.drawable.btn_green_circle);
                    btn.setTextColor(getColor(R.color.white));
                    selectedDays.add(day);
                }
            });

            layoutDays.addView(btn);
        }

        for (MedicineItem item : oldMedicines) {
            addMedicineRow(item.getName(), item.getAmount());
        }

        btnAddMedicine.setOnClickListener(v -> addMedicineRow("", ""));
        btnAm.setOnClickListener(v -> setAmPmUI(false));
        btnPm.setOnClickListener(v -> setAmPmUI(true));
        btnSave.setOnClickListener(v -> saveMedicineTime());
    }

    // 삭제 메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_medicine_menu, menu);
        return true;
    }

    // 삭제 동작 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("삭제 확인")
                    .setMessage("이 약 시간을 삭제하시겠습니까?")
                    .setPositiveButton("삭제", (dialog, which) -> {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("delete", true);
                        resultIntent.putExtra("docId", docId);
                        resultIntent.putExtra("position", position);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    })
                    .setNegativeButton("취소", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMedicineTime() {
        if (selectedDays.isEmpty()) {
            Toast.makeText(this, "요일을 선택해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        String hourStr = etHour.getText().toString();
        String minuteStr = etMinute.getText().toString();
        if (hourStr.isEmpty() || minuteStr.isEmpty()) {
            Toast.makeText(this, "시간을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = Integer.parseInt(hourStr);
        int minute = Integer.parseInt(minuteStr);
        if (amPm.equals("오후") && hour < 12) hour += 12;
        if (amPm.equals("오전") && hour == 12) hour = 0;
        String time24 = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        ArrayList<MedicineItem> items = new ArrayList<>();
        Set<String> nameSet = new HashSet<>();

        for (int i = 0; i < layoutMedicineList.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) layoutMedicineList.getChildAt(i);
            EditText etName = (EditText) row.getChildAt(0);
            EditText etAmount = (EditText) row.getChildAt(1);

            String name = etName.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();

            if (name.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "약 이름과 복용량을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (nameSet.contains(name)) {
                Toast.makeText(this, "중복된 약 이름이 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            nameSet.add(name);
            items.add(new MedicineItem(name, amount, false));
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("amPm", amPm);
        resultIntent.putExtra("hour", hour);
        resultIntent.putExtra("minute", minute);
        resultIntent.putExtra("time", time24);
        resultIntent.putStringArrayListExtra("days", new ArrayList<>(selectedDays));

        Bundle bundle = new Bundle();
        bundle.putSerializable("items", items);
        resultIntent.putExtras(bundle);

        if (docId != null) {
            resultIntent.putExtra("docId", docId);
            resultIntent.putExtra("position", position);
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void setAmPmUI(boolean isPm) {
        amPm = isPm ? "오후" : "오전";
        btnAm.setBackgroundResource(isPm ? R.drawable.btn_white : R.drawable.btn_green_selected);
        btnAm.setTextColor(getColor(isPm ? R.color.gray : R.color.white));
        btnPm.setBackgroundResource(isPm ? R.drawable.btn_green_selected : R.drawable.btn_white);
        btnPm.setTextColor(getColor(isPm ? R.color.white : R.color.gray));
    }

    private void addMedicineRow(String name, String amount) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);

        EditText etName = new EditText(this);
        etName.setHint("약 이름");
        etName.setText(name);
        etName.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));

        EditText etAmount = new EditText(this);
        etAmount.setHint("복용량");
        etAmount.setText(amount);
        etAmount.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        Button btnDelete = new Button(this);
        btnDelete.setText("삭제");
        btnDelete.setOnClickListener(v -> layoutMedicineList.removeView(row));

        row.addView(etName);
        row.addView(etAmount);
        row.addView(btnDelete);

        layoutMedicineList.addView(row);
    }
}
