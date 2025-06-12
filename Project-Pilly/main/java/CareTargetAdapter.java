package com.example.pilly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CareTargetAdapter extends RecyclerView.Adapter<CareTargetAdapter.ViewHolder> {

    public interface OnCardListener {
        void onEdit(CareTarget target);
        void onDelete(CareTarget target);
        void onMedicineCheck(CareTarget target, MedicineRow medicine, int timeDocIndex, int medIdx);
    }

    private final Context context;
    private final List<CareTarget> list;
    private final OnCardListener listener;
    private final boolean enableCheck; // 🔹 추가된 필드

    // 🔹 4개짜리 생성자 추가
    public CareTargetAdapter(Context context, List<CareTarget> list, OnCardListener listener, boolean enableCheck) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.enableCheck = enableCheck;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_care_target, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        CareTarget t = list.get(pos);
        h.tvNameRelation.setText(t.name + " (" + t.relation + ")");
        h.tvRate.setText(t.rate + "%");

        // 🔹 약 목록(체크 동작 포함) 바인딩 - enableCheck 전달
        MedicineRowAdapter medAdapter = new MedicineRowAdapter(t.medicines, enableCheck, (medicine, timeDocIndex, medIdx) -> {
            if (listener != null) {
                listener.onMedicineCheck(t, medicine, timeDocIndex, medIdx);
            }
        });
        h.recyclerMedicines.setLayoutManager(new LinearLayoutManager(context));
        h.recyclerMedicines.setAdapter(medAdapter);

        h.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(t);
        });
        h.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(t);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameRelation, tvRate;
        ImageView btnEdit, btnDelete;
        RecyclerView recyclerMedicines;

        ViewHolder(View v) {
            super(v);
            tvNameRelation = v.findViewById(R.id.tvNameRelation);
            tvRate = v.findViewById(R.id.tvRate);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
            recyclerMedicines = v.findViewById(R.id.layoutMedicines);
        }
    }
}
