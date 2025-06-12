package com.example.pilly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavHelper.bind(this); // ✅ 하단 네비게이션 연결

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvEmail.setText(user.getEmail());

        uid = user.getUid();
        loadUserName(); // 🔄 Firestore에서 이름 불러오기

        // 메뉴 클릭 처리
        findViewById(R.id.menuNotification).setOnClickListener(v ->
                startActivity(new Intent(this, AlarmSettingActivity.class)));

        findViewById(R.id.menuEditInfo).setOnClickListener(v ->
                startActivity(new Intent(this, EditProfileActivity.class)));

        findViewById(R.id.menuTerms).setOnClickListener(v ->
                Toast.makeText(this, "이용약관입니다.", Toast.LENGTH_SHORT).show());

        findViewById(R.id.menuHelp).setOnClickListener(v ->
                Toast.makeText(this, "도움말입니다.", Toast.LENGTH_SHORT).show());

        findViewById(R.id.menuLogout).setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserName(); // ✅ 이름 변경 시 다시 불러옴
    }

    private void loadUserName() {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        String name = snapshot.getString("username");
                        tvName.setText(name != null ? name : "사용자");
                    }
                })
                .addOnFailureListener(e -> {
                    tvName.setText("오류");
                });
    }
}
