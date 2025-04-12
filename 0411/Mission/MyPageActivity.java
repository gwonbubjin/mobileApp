package com.example.mission;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        LinearLayout listLayout = findViewById(R.id.listLayout);
        ImageButton btnBack = findViewById(R.id.btnBack);

        SharedPreferences prefs = getSharedPreferences("donations", MODE_PRIVATE);
        String jsonData = prefs.getString("donationList", "[]");

        try {
            JSONArray donationArray = new JSONArray(jsonData);
            for (int i = donationArray.length() - 1; i >= 0; i--) {
                JSONObject donation = donationArray.getJSONObject(i);
                String title = donation.getString("title");

                TextView tv = new TextView(this);
                tv.setText("• " + title);
                tv.setTextSize(16);
                listLayout.addView(tv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnBack.setOnClickListener(v -> finish());
    }
}