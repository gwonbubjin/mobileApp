package com.example.pilly;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextId, editTextPassword;
    private Button btnLogin;
    private ImageView btnPasswordToggle;
    private boolean isPasswordVisible = false;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnPasswordToggle = findViewById(R.id.btnPasswordToggle);

        // 비밀번호 보이기/숨기기
        btnPasswordToggle.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                btnPasswordToggle.setImageResource(R.drawable.ic_visibility);
            } else {
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                btnPasswordToggle.setImageResource(R.drawable.ic_visibility_off);
            }
            editTextPassword.setSelection(editTextPassword.getText().length());
        });

        // 로그인 버튼 클릭
        btnLogin.setOnClickListener(v -> {
            String id = editTextId.getText().toString().trim();
            String pw = editTextPassword.getText().toString().trim();

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Firestore에서 아이디로 이메일 찾기
            db.collection("users")
                    .whereEqualTo("userid", id)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (!snapshot.isEmpty()) {
                            String email = snapshot.getDocuments().get(0).getString("email");
                            // 2. 찾은 이메일로 Auth 로그인 시도
                            mAuth.signInWithEmailAndPassword(email, pw)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(this, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "서버 오류: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });

        // 회원가입 이동
        TextView tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
