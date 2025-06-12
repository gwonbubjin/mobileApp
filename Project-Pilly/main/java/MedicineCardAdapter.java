package com.example.pilly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MedicineCardAdapter extends RecyclerView.Adapter<MedicineCardAdapter.CardViewHolder> {

    private final ArrayList<MedicineTime> medicineTimes;
    private final Context context;
    private final FirebaseFirestore db;
    private final String uid;

    public interface OnEditClickListener {
        void onEditClick(MedicineTime medicineTime, int position);
    }

    public interface OnMedicineCheckListener {
        void onMedicineCheck(MedicineTime medicineTime, MedicineItem item, int timeDocIndex, int medIndex);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(MedicineTime medicineTime, int position);
    }

    private final OnEditClickListener editClickListener;
    private final OnMedicineCheckListener checkListener;
    private final OnDeleteClickListener deleteClickListener;

    public MedicineCardAdapter(ArrayList<MedicineTime> medicineTimes, Context context, FirebaseFirestore db, String uid,
                               OnEditClickListener editListener, OnMedicineCheckListener checkListener, OnDeleteClickListener deleteListener) {
        this.medicineTimes = medicineTimes;
        this.context = context;
        this.db = db;
        this.uid = uid;
        this.editClickListener = editListener;
        this.checkListener = checkListener;
        this.deleteClickListener = deleteListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        MedicineTime time = medicineTimes.get(position);

        String timeStr = time.getTime();
        if (timeStr != null && !timeStr.isEmpty()) {
            try {
                String[] parts = timeStr.split(":");
                if (parts.length == 2) {
                    int hour = Integer.parseInt(parts[0]);
                    String amPm = "오전";
                    if (hour >= 12) {
                        amPm = "오후";
                        if (hour > 12) hour -= 12;
                    } else if (hour == 0) {
                        hour = 12;
                    }
                    timeStr = String.format("%s %d:%s", amPm, hour, parts[1]);
                }
            } catch (Exception e) {
                timeStr = time.getTime();
            }
        }
        holder.tvTime.setText(timeStr);

        holder.medicineContainer.removeAllViews();
        ArrayList<MedicineItem> items = time.getItems();

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                MedicineItem item = items.get(i);
                int medIndex = i;

                View pillView = LayoutInflater.from(context).inflate(R.layout.item_medicine_item, holder.medicineContainer, false);
                TextView tvPill = pillView.findViewById(R.id.tv_medicine_item);
                tvPill.setText(Objects.toString(item.getName(), "이름없음"));

                updatePillStyle(pillView, tvPill, item.isTaken());

                pillView.setOnClickListener(v -> {
                    boolean newTaken = !item.isTaken();
                    item.setTaken(newTaken);
                    updatePillStyle(pillView, tvPill, newTaken);

                    db.collection("users")
                            .document(uid)
                            .collection("medicine_times")
                            .document(time.getDocId())
                            .get()
                            .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    ArrayList<Map<String, Object>> dbItems = (ArrayList<Map<String, Object>>) documentSnapshot.get("items");
                                    if (dbItems != null && medIndex < dbItems.size()) {
                                        dbItems.get(medIndex).put("taken", newTaken);

                                        db.collection("users")
                                                .document(uid)
                                                .collection("medicine_times")
                                                .document(time.getDocId())
                                                .update("items", dbItems)
                                                .addOnSuccessListener(unused1 -> {
                                                    String today = java.time.LocalDate.now().toString();
                                                    String recordId = time.getDocId() + "_" + item.getName();
                                                    Map<String, Object> record = new HashMap<>();
                                                    record.put("status", newTaken);
                                                    record.put("time", time.getTime());
                                                    record.put("medicineName", item.getName());
                                                    record.put("timestamp", com.google.firebase.Timestamp.now());

                                                    db.collection("users")
                                                            .document(uid)
                                                            .collection("medicineRecords")
                                                            .document(today)
                                                            .collection("records")
                                                            .document(recordId)
                                                            .set(record)
                                                            .addOnSuccessListener(unused2 -> {
                                                                notifyItemChanged(holder.getBindingAdapterPosition());
                                                                checkChallengeCompletion(); // ✅ 챌린지 검사 실행
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                item.setTaken(!newTaken);
                                                                updatePillStyle(pillView, tvPill, !newTaken);
                                                                Toast.makeText(context, "기록 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            });
                                                })
                                                .addOnFailureListener(e -> {
                                                    item.setTaken(!newTaken);
                                                    updatePillStyle(pillView, tvPill, !newTaken);
                                                    Toast.makeText(context, "업데이트 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(e -> {
                                item.setTaken(!newTaken);
                                updatePillStyle(pillView, tvPill, !newTaken);
                                Toast.makeText(context, "데이터 조회 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                    int pos = holder.getBindingAdapterPosition();
                    if (checkListener != null && pos != RecyclerView.NO_POSITION) {
                        checkListener.onMedicineCheck(time, item, pos, medIndex);
                    }
                });

                holder.medicineContainer.addView(pillView);
            }
        }

        holder.btnManage.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.btnManage);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_card_manage, popup.getMenu());

            popup.setOnMenuItemClickListener(menuItem -> {
                int pos = holder.getBindingAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return false;

                MedicineTime selectedTime = medicineTimes.get(pos);

                if (menuItem.getItemId() == R.id.menu_edit) {
                    if (editClickListener != null) {
                        editClickListener.onEditClick(selectedTime, pos);
                    }
                    return true;
                } else if (menuItem.getItemId() == R.id.menu_delete) {
                    if (deleteClickListener != null) {
                        deleteClickListener.onDeleteClick(selectedTime, pos);
                    }
                    return true;
                }
                return false;
            });

            popup.show();
        });
    }

    private void checkChallengeCompletion() {
        String today = java.time.LocalDate.now().toString();

        db.collection("users")
                .document(uid)
                .collection("medicine_times")
                .get()
                .addOnSuccessListener(snapshot -> {
                    boolean anyData = false;
                    boolean allTaken = true;

                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        anyData = true;
                        ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) doc.get("items");
                        if (items != null) {
                            for (Map<String, Object> item : items) {
                                Boolean taken = (Boolean) item.get("taken");
                                if (taken == null || !taken) {
                                    allTaken = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (anyData && allTaken) {
                        db.collection("users")
                                .document(uid)
                                .collection("challenges")
                                .document("med_3")
                                .get()
                                .addOnSuccessListener(doc -> {
                                    if (!doc.exists()) return;

                                    Challenge challenge = doc.toObject(Challenge.class);
                                    if (challenge == null || challenge.isCompleted()) return;

                                    String last = challenge.getLastSuccessDate();
                                    if (today.equals(last)) return;

                                    int newStreak = challenge.getCurrentStreak() + 1;
                                    boolean isComplete = newStreak >= challenge.getTargetDays();

                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("currentStreak", newStreak);
                                    updates.put("lastSuccessDate", today);
                                    if (isComplete) updates.put("completed", true);

                                    db.collection("users")
                                            .document(uid)
                                            .collection("challenges")
                                            .document("med_3")
                                            .update(updates)
                                            .addOnSuccessListener(v -> {
                                                Toast.makeText(context, "🎉 챌린지 1일 성공!", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(context, "챌린지 갱신 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                });
                    }
                });
    }

    private void updatePillStyle(View pillView, TextView tvPill, boolean isTaken) {
        pillView.setBackgroundResource(isTaken ? R.drawable.pill_background_selected : R.drawable.pill_background_default);
        tvPill.setTextColor(isTaken ? Color.WHITE : Color.parseColor("#78C15E"));
    }

    @Override
    public int getItemCount() {
        return medicineTimes.size();
    }

    public void updateData(ArrayList<MedicineTime> newData) {
        this.medicineTimes.clear();
        this.medicineTimes.addAll(newData);
        notifyDataSetChanged();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        LinearLayout medicineContainer;
        ImageButton btnManage;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            medicineContainer = itemView.findViewById(R.id.container_medicine_items);
            btnManage = itemView.findViewById(R.id.btn_manage);
        }
    }
}
