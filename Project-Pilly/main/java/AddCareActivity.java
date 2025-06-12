package com.example.pilly;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCareActivity extends AppCompatActivity {

    private EditText etTargetId;
    private Button btnSendRequest;
    private TextView tvNoRequests;
    private ImageButton btnBack;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private List<RequestItem> requestList = new ArrayList<>();
    private RequestAdapter adapter;
    private View recyclerRequests; // View 타입으로 선언 (RecyclerView가 아닌 뷰로 인식되었을 경우 대응)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_care);

        etTargetId = findViewById(R.id.etTargetId);
        btnSendRequest = findViewById(R.id.btnSendRequest);
        recyclerRequests = findViewById(R.id.recyclerRequests);
        tvNoRequests = findViewById(R.id.tvNoRequests);
        btnBack = findViewById(R.id.btnBack);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        if (recyclerRequests instanceof RecyclerView) {
            RecyclerView realRecycler = (RecyclerView) recyclerRequests;
            realRecycler.setLayoutManager(new LinearLayoutManager(this));
            adapter = new RequestAdapter(requestList);
            realRecycler.setAdapter(adapter);

            adapter.setOnDeleteClickListener((position, docId) -> {
                db.collection("requests").document(docId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "요청이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            requestList.remove(position);
                            adapter.notifyItemRemoved(position);
                            checkEmpty();
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "삭제 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            });
        }

        loadMyRequests();

        btnBack.setOnClickListener(v -> finish());

        btnSendRequest.setOnClickListener(v -> {
            String inputId = etTargetId.getText().toString().trim();
            if (inputId.isEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            }
            db.collection("users")
                    .whereEqualTo("userid", inputId)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (querySnapshot.isEmpty()) {
                            Toast.makeText(this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            String fromUID = auth.getCurrentUser().getUid();
                            String toUID = querySnapshot.getDocuments().get(0).getId();

                            if (fromUID.equals(toUID)) {
                                Toast.makeText(this, "본인에게는 요청할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            db.collection("requests")
                                    .whereEqualTo("fromUID", fromUID)
                                    .whereEqualTo("toUID", toUID)
                                    .get()
                                    .addOnSuccessListener(requestSnapshot -> {
                                        if (!requestSnapshot.isEmpty()) {
                                            Toast.makeText(this, "이미 요청을 보낸 사용자입니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("fromUID", fromUID);
                                            data.put("toUID", toUID);
                                            data.put("status", "pending");
                                            data.put("timestamp", new Date());

                                            db.collection("requests")
                                                    .add(data)
                                                    .addOnSuccessListener(doc -> {
                                                        Toast.makeText(this, "요청을 전송했습니다.", Toast.LENGTH_SHORT).show();
                                                        etTargetId.setText("");
                                                        loadMyRequests();
                                                    })
                                                    .addOnFailureListener(e -> Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "사용자 검색 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    private void checkEmpty() {
        if (requestList.isEmpty()) {
            recyclerRequests.setVisibility(View.GONE);
            tvNoRequests.setVisibility(View.VISIBLE);
        } else {
            recyclerRequests.setVisibility(View.VISIBLE);
            tvNoRequests.setVisibility(View.GONE);
        }
    }

    private void loadMyRequests() {
        String myUID = auth.getCurrentUser().getUid();
        db.collection("requests")
                .whereEqualTo("fromUID", myUID)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    requestList.clear();
                    if (querySnapshot.isEmpty()) {
                        adapter.notifyDataSetChanged();
                        checkEmpty();
                        return;
                    }

                    int total = querySnapshot.size();
                    final int[] processed = {0};

                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        String status = doc.getString("status");
                        Date timestamp = doc.getDate("timestamp");
                        String toUID = doc.getString("toUID");
                        String time = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(timestamp);
                        String docId = doc.getId();

                        db.collection("users").document(toUID).get()
                                .addOnSuccessListener(userDoc -> {
                                    String username = userDoc.getString("username");
                                    String showName = (username != null && !username.isEmpty()) ? username : toUID;
                                    requestList.add(new RequestItem(docId, showName, status, time));
                                    processed[0]++;
                                    if (processed[0] == total) {
                                        adapter.notifyDataSetChanged();
                                        checkEmpty();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    requestList.add(new RequestItem(docId, toUID, status, time));
                                    processed[0]++;
                                    if (processed[0] == total) {
                                        adapter.notifyDataSetChanged();
                                        checkEmpty();
                                    }
                                });
                    }
                });
    }
}
