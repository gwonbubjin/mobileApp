package com.example.pilly;

import android.os.Bundle;
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
import java.util.List;

public class ReceivedRequestsActivity extends AppCompatActivity {
    private RecyclerView recyclerReceived;
    private TextView tvNoRequests;
    private ImageButton btnBack;

    private ReceivedRequestsAdapter adapter;
    private List<ReceivedRequestItem> receivedList = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_requests);

        recyclerReceived = findViewById(R.id.recyclerReceived);
        tvNoRequests = findViewById(R.id.tvNoRequests);
        btnBack = findViewById(R.id.btnBack);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerReceived.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceivedRequestsAdapter(receivedList);
        recyclerReceived.setAdapter(adapter);

        // 🔥 버튼 클릭 이벤트 연결!
        adapter.setOnActionClickListener(new ReceivedRequestsAdapter.OnActionClickListener() {
            @Override
            public void onAccept(int pos, String docId) {
                db.collection("requests").document(docId)
                        .update("status", "accepted")
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ReceivedRequestsActivity.this, "요청을 수락했습니다.", Toast.LENGTH_SHORT).show();
                            receivedList.remove(pos);
                            adapter.notifyItemRemoved(pos);
                            checkEmpty();
                        })
                        .addOnFailureListener(e -> Toast.makeText(ReceivedRequestsActivity.this, "실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onReject(int pos, String docId) {
                db.collection("requests").document(docId)
                        .update("status", "rejected")
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ReceivedRequestsActivity.this, "요청을 거절했습니다.", Toast.LENGTH_SHORT).show();
                            receivedList.remove(pos);
                            adapter.notifyItemRemoved(pos);
                            checkEmpty();
                        })
                        .addOnFailureListener(e -> Toast.makeText(ReceivedRequestsActivity.this, "실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        btnBack.setOnClickListener(v -> finish());

        loadReceivedRequests();
    }

    private void checkEmpty() {
        if (receivedList.isEmpty()) {
            recyclerReceived.setVisibility(RecyclerView.GONE);
            tvNoRequests.setVisibility(TextView.VISIBLE);
        } else {
            recyclerReceived.setVisibility(RecyclerView.VISIBLE);
            tvNoRequests.setVisibility(TextView.GONE);
        }
    }

    private void loadReceivedRequests() {
        String myUID = auth.getCurrentUser().getUid();
        db.collection("requests")
                .whereEqualTo("toUID", myUID)
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    receivedList.clear();
                    if (querySnapshot.isEmpty()) {
                        adapter.notifyDataSetChanged();
                        checkEmpty();
                        return;
                    }
                    int total = querySnapshot.size();
                    final int[] processed = {0};

                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        String docId = doc.getId();
                        String fromUID = doc.getString("fromUID");
                        Date timestamp = doc.getDate("timestamp");
                        String time = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(timestamp);
                        String status = doc.getString("status");

                        db.collection("users").document(fromUID).get()
                                .addOnSuccessListener(userDoc -> {
                                    String fromName = userDoc.getString("username");
                                    if (fromName == null || fromName.isEmpty()) fromName = fromUID;

                                    receivedList.add(new ReceivedRequestItem(docId, fromName, time, status));
                                    processed[0]++;
                                    if (processed[0] == total) {
                                        adapter.notifyDataSetChanged();
                                        checkEmpty();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    receivedList.add(new ReceivedRequestItem(docId, fromUID, time, status));
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
