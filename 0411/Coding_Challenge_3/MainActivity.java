package com.example.coding_challenge_3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnIntro).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
        });

        findViewById(R.id.btnSettings).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        });
    }
}
