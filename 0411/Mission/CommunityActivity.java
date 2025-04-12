package com.example.mission;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommunityActivity extends AppCompatActivity {

    LinearLayout communityList;
    EditText editComment;
    Button btnAddComment;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        communityList = findViewById(R.id.communityList);
        editComment = findViewById(R.id.editComment);
        btnAddComment = findViewById(R.id.btnAddComment);
        btnBack = findViewById(R.id.btnBack);

        SharedPreferences prefs = getSharedPreferences("donations", MODE_PRIVATE);
        String jsonData = prefs.getString("donationList", "[]");

        try {
            JSONArray donationArray = new JSONArray(jsonData);
            for (int i = donationArray.length() - 1; i >= 0; i--) {
                JSONObject donation = donationArray.getJSONObject(i);
                String title = donation.getString("title");

                TextView tv = new TextView(this);
                tv.setText("• " + title);
                tv.setTextSize(18);
                tv.setPadding(0, 16, 0, 8);
                communityList.addView(tv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnAddComment.setOnClickListener(v -> {
            String comment = editComment.getText().toString();
            if (!comment.isEmpty()) {
                TextView tv = new TextView(this);
                tv.setText("→ " + comment);
                tv.setTextSize(14);
                communityList.addView(tv);
                editComment.setText("");
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}