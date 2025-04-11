package com.example.bgchangeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FrameLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageContainer = findViewById(R.id.imageContainer);

        Button btnColor1 = findViewById(R.id.btnColor1);
        Button btnColor2 = findViewById(R.id.btnColor2);
        Button btnColor3 = findViewById(R.id.btnColor3);
        Button btnColor4 = findViewById(R.id.btnColor4);

        btnColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageContainer.setBackgroundColor(Color.parseColor("#A5D6A7")); // 연녹색
            }
        });

        btnColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageContainer.setBackgroundColor(Color.parseColor("#FFF176")); // 노랑
            }
        });

        btnColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageContainer.setBackgroundColor(Color.parseColor("#5C6BC0")); // 파랑
            }
        });

        btnColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageContainer.setBackgroundColor(Color.parseColor("#FFCDD2")); // 연분홍
            }
        });
    }
}
