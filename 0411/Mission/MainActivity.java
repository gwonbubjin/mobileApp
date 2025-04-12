package com.example.mission;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnRegister, btnMyPage, btnCommunity, btnDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btnRegister);
        btnMyPage = findViewById(R.id.btnMyPage);
        btnCommunity = findViewById(R.id.btnCommunity);
        btnDetail = findViewById(R.id.btnDetail);

        btnRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        btnMyPage.setOnClickListener(v -> startActivity(new Intent(this, MyPageActivity.class)));
        btnCommunity.setOnClickListener(v -> startActivity(new Intent(this, CommunityActivity.class)));
        btnDetail.setOnClickListener(v -> startActivity(new Intent(this, DetailActivity.class)));
    }
}