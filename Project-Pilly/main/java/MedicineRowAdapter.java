package com.example.pilly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineRowAdapter extends RecyclerView.Adapter<MedicineRowAdapter.ViewHolder> {
    public interface OnMedCheckListener {
        void onCheck(MedicineRow item, int timeDocIndex, int medIdx);
    }

    private final List<MedicineRow> medicineList;
    private final boolean enableCheck; // true: 체크 가능, false: 모니터링만
    private final OnMedCheckListener checkListener;

    // MedicineRow에 timeDocIndex, medIdx 필드가 있어야 함(리스트에서 위치 추적)
    public MedicineRowAdapter(List<MedicineRow> medicineList, boolean enableCheck, OnMedCheckListener checkListener) {
        this.medicineList = medicineList;
        this.enableCheck = enableCheck;
        this.checkListener = checkListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicineRow item = medicineList.get(position);
        holder.tvMedTime.setText(item.time);
        holder.tvMedName.setText(item.name);

        // 복용 상태에 따라 아이콘/색상 변경
        if (item.taken) {
            holder.ivMedStatus.setImageResource(R.drawable.ic_check_circle);
            holder.ivMedStatus.setColorFilter(0xFF69C088); // 초록
        } else {
            holder.ivMedStatus.setImageResource(R.drawable.ic_close_circle);
            holder.ivMedStatus.setColorFilter(0xFFF45858); // 빨강
        }

        // 피돌봄이(본인)는 클릭 가능, 돌봄이는 클릭 불가
        holder.ivMedStatus.setAlpha(enableCheck ? 1.0f : 0.5f); // 돌봄이면 흐리게
        if (enableCheck) {
            holder.ivMedStatus.setOnClickListener(v -> {
                if (checkListener != null) {
                    // item에 timeDocIndex, medIdx가 들어있어야 함
                    checkListener.onCheck(item, item.timeDocIndex, item.medIndex);
                }
            });
        } else {
            holder.ivMedStatus.setOnClickListener(null); // 클릭 이벤트 제거
        }
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMedTime, tvMedName;
        ImageView ivMedStatus;
        ViewHolder(View v) {
            super(v);
            tvMedTime = v.findViewById(R.id.tvMedTime);
            tvMedName = v.findViewById(R.id.tvMedName);
            ivMedStatus = v.findViewById(R.id.ivMedStatus);
        }
    }
}
