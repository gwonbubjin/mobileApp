package com.example.coding_challenge_3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // 뒤로가기 누르면 MainActivity로 자동 복귀
    }
}
