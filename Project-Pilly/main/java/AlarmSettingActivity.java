package com.example.pilly;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmSettingActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AlarmPrefs";
    private static final String MARKETING_KEY = "marketing_agree";

    private ImageView btnBack;
    private Switch switchMarketing;
    private Button btnAlreadyAllowed;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        // 💾 SharedPreferences 초기화
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 🔗 XML 연결
        btnBack = findViewById(R.id.btnBack);
        switchMarketing = findViewById(R.id.switchMarketing);
        btnAlreadyAllowed = findViewById(R.id.btnAlreadyAllowed);

        // 🔙 뒤로가기 동작
        btnBack.setOnClickListener(v -> finish());

        // 📩 마케팅 스위치 초기 상태
        boolean isAgreed = prefs.getBoolean(MARKETING_KEY, false);
        switchMarketing.setChecked(isAgreed);

        // 📩 스위치 상태 변경 리스너
        switchMarketing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(MARKETING_KEY, isChecked).apply();
            if (isChecked) {
                Toast.makeText(this, "마케팅 정보 수신에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔔 "이미 허용했습니다" 버튼은 기본 비활성 (디자인용)
        btnAlreadyAllowed.setEnabled(false);
    }
}
