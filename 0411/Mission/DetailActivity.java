package com.example.mission;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    TextView textTotalDonations, textRecentDonation;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textTotalDonations = findViewById(R.id.textTotalDonations);
        textRecentDonation = findViewById(R.id.textRecentDonation);
        btnBack = findViewById(R.id.btnBack);

        SharedPreferences prefs = getSharedPreferences("donations", MODE_PRIVATE);
        String jsonData = prefs.getString("donationList", "[]");

        try {
            JSONArray donationArray = new JSONArray(jsonData);
            int total = donationArray.length();
            textTotalDonations.setText("총 기부 횟수: " + total + "회");

            if (total > 0) {
                String recent = donationArray.getJSONObject(total - 1).getString("title");
                textRecentDonation.setText("최근 기부: " + recent);
            } else {
                textRecentDonation.setText("최근 기부: 없음");
            }

        } catch (JSONException e) {
            textTotalDonations.setText("기부 정보를 불러올 수 없습니다.");
            textRecentDonation.setText("");
        }

        btnBack.setOnClickListener(v -> finish());
    }
}
