package com.example.pilly;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // ✅ View 연결
        TextView tvBack = findViewById(R.id.tvBack);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        LinearLayout btnEditName = findViewById(R.id.btnEditName);
        TextView btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        // ✅ Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            uid = user.getUid();
            tvEmail.setText(user.getEmail());

            // ✅ Firestore에서 username 가져오기
            db.collection("users").document(uid).get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            String name = snapshot.getString("username");
                            if (name != null) tvName.setText(name);
                        }
                    });
        }

        // 1. 🔙 뒤로가기
        tvBack.setOnClickListener(v -> finish());

        // 3. ✏ 이름 수정
        btnEditName.setOnClickListener(v -> {
            EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(tvName.getText().toString());

            new AlertDialog.Builder(this)
                    .setTitle("이름 수정")
                    .setMessage("새로운 이름을 입력하세요.")
                    .setView(input)
                    .setPositiveButton("저장", (dialog, which) -> {
                        String newName = input.getText().toString().trim();
                        if (!newName.isEmpty()) {
                            db.collection("users").document(uid)
                                    .update("username", newName)
                                    .addOnSuccessListener(aVoid -> {
                                        tvName.setText(newName);
                                        Toast.makeText(this, "이름이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(this, "수정 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });

        // 4. ❌ 회원탈퇴
        btnDeleteAccount.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("회원탈퇴")
                    .setMessage("정말 탈퇴하시겠습니까?")
                    .setPositiveButton("탈퇴", (dialog, which) -> deleteAccount())
                    .setNegativeButton("취소", null)
                    .show();
        });
    }

    private void deleteAccount() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            db.collection("users").document(uid).delete(); // Firestore 문서 삭제
            user.delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finishAffinity(); // 앱 완전 종료
                } else {
                    Toast.makeText(this, "탈퇴 실패: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

