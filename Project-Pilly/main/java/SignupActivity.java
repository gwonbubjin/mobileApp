package com.example.pilly;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private EditText etId, etPw, etPwCheck, etName, etBirth, etEmail;
    private Button btnCheckId, btnMale, btnFemale, btnSignup;
    private TextView tvGoLogin;
    private ImageView btnPwToggle, btnPwToggle2;
    private String selectedGender = "";
    private boolean pwVisible = false, pwCheckVisible = false;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etId = findViewById(R.id.etId);
        btnCheckId = findViewById(R.id.btnCheckId);
        etPw = findViewById(R.id.etPw);
        etPwCheck = findViewById(R.id.etPwCheck);
        etName = findViewById(R.id.etName);
        etBirth = findViewById(R.id.etBirth);
        etEmail = findViewById(R.id.etEmail);
        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
        btnSignup = findViewById(R.id.btnSignup);
        tvGoLogin = findViewById(R.id.tvGoLogin);
        btnPwToggle = findViewById(R.id.btnPwToggle);
        btnPwToggle2 = findViewById(R.id.btnPwToggle2);

        // 비밀번호 숨기기/보이기
        btnPwToggle.setOnClickListener(v -> {
            pwVisible = !pwVisible;
            etPw.setTransformationMethod(pwVisible ?
                    HideReturnsTransformationMethod.getInstance() :
                    PasswordTransformationMethod.getInstance());
            btnPwToggle.setImageResource(pwVisible ? R.drawable.ic_visibility : R.drawable.ic_visibility_off);
            etPw.setSelection(etPw.length());
        });
        btnPwToggle2.setOnClickListener(v -> {
            pwCheckVisible = !pwCheckVisible;
            etPwCheck.setTransformationMethod(pwCheckVisible ?
                    HideReturnsTransformationMethod.getInstance() :
                    PasswordTransformationMethod.getInstance());
            btnPwToggle2.setImageResource(pwCheckVisible ? R.drawable.ic_visibility : R.drawable.ic_visibility_off);
            etPwCheck.setSelection(etPwCheck.length());
        });

        // 아이디 중복 확인
        btnCheckId.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            if (TextUtils.isEmpty(id)) {
                showToast("아이디를 입력해주세요.");
                return;
            }
            db.collection("users")
                    .whereEqualTo("userid", id)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (!snapshot.isEmpty()) {
                            showToast("이미 사용중인 아이디입니다.");
                        } else {
                            showToast("사용 가능한 아이디입니다.");
                        }
                    })
                    .addOnFailureListener(e -> showToast("서버 오류: " + e.getMessage()));
        });

        // 성별 버튼 색상 초기화
        btnMale.setBackgroundResource(R.drawable.rounded_btn_gray);
        btnMale.setTextColor(Color.parseColor("#59A983"));
        btnFemale.setBackgroundResource(R.drawable.rounded_btn_gray);
        btnFemale.setTextColor(Color.parseColor("#59A983"));

        // 성별 선택
        btnMale.setOnClickListener(v -> {
            selectedGender = "M";
            btnMale.setBackgroundResource(R.drawable.rounded_btn);
            btnMale.setTextColor(Color.WHITE);
            btnFemale.setBackgroundResource(R.drawable.rounded_btn_gray);
            btnFemale.setTextColor(Color.parseColor("#59A983"));
        });

        btnFemale.setOnClickListener(v -> {
            selectedGender = "F";
            btnFemale.setBackgroundResource(R.drawable.rounded_btn);
            btnFemale.setTextColor(Color.WHITE);
            btnMale.setBackgroundResource(R.drawable.rounded_btn_gray);
            btnMale.setTextColor(Color.parseColor("#59A983"));
        });

        // 회원가입 처리
        btnSignup.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            String pw = etPw.getText().toString();
            String pw2 = etPwCheck.getText().toString();
            String name = etName.getText().toString().trim();
            String birth = etBirth.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (id.isEmpty() || pw.isEmpty() || pw2.isEmpty() || name.isEmpty() || birth.isEmpty() || email.isEmpty()) {
                showToast("모든 항목을 입력해주세요.");
                return;
            }
            if (!pw.equals(pw2)) {
                showToast("비밀번호가 일치하지 않습니다.");
                return;
            }
            if (selectedGender.isEmpty()) {
                showToast("성별을 선택해주세요.");
                return;
            }

            db.collection("users")
                    .whereEqualTo("userid", id)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (!snapshot.isEmpty()) {
                            showToast("이미 사용중인 아이디입니다.");
                        } else {
                            mAuth.createUserWithEmailAndPassword(email, pw)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            String uid = mAuth.getCurrentUser().getUid();
                                            User user = new User(id, name, birth, email, selectedGender);
                                            db.collection("users").document(uid)
                                                    .set(user)
                                                    .addOnSuccessListener(unused -> {
                                                        showToast("회원가입이 완료되었습니다!");
                                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    })
                                                    .addOnFailureListener(e -> showToast("회원정보 저장 실패: " + e.getMessage()));
                                        } else {
                                            showToast("회원가입 실패: " + task.getException().getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(e -> showToast("서버 오류: " + e.getMessage()));
        });

        // 로그인 텍스트 컬러 강조
        String text = "이미 계정이 있나요? 로그인";
        SpannableString ss = new SpannableString(text);
        int start = text.indexOf("로그인");
        int end = start + "로그인".length();
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvGoLogin.setText(ss);

        tvGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showToast(String msg) {
        Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
