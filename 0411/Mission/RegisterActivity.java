package com.example.mission;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText editTitle;
    Button btnSubmit;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTitle = findViewById(R.id.editTitle);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);

        btnSubmit.setOnClickListener(v -> {
            String donationTitle = editTitle.getText().toString().trim();
            if (!donationTitle.isEmpty()) {
                SharedPreferences prefs = getSharedPreferences("donations", MODE_PRIVATE);
                String oldData = prefs.getString("donationList", "[]");
                try {
                    JSONArray donationArray = new JSONArray(oldData);
                    JSONObject donation = new JSONObject();
                    donation.put("title", donationTitle);
                    donation.put("timestamp", System.currentTimeMillis());
                    donation.put("comments", new JSONArray());
                    donationArray.put(donation);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("donationList", donationArray.toString());
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "기부 등록 완료!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}