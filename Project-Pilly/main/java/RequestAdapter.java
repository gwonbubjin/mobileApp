package com.example.pilly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RequestItem> items;
    private OnDeleteClickListener deleteClickListener;

    // 삭제 버튼 클릭 인터페이스
    public interface OnDeleteClickListener {
        void onDeleteClick(int position, String docId);
    }

    // 삭제 리스너 등록 함수
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public RequestAdapter(List<RequestItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestItem item = items.get(position);
        holder.tvRequestTarget.setText("요청 대상: " + item.targetName);
        holder.tvRequestStatus.setText("상태: " + getStatusText(item.status));
        holder.tvRequestTime.setText(item.time);

        // 삭제 버튼 클릭 시 콜백 호출
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(position, item.docId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String getStatusText(String status) {
        if ("pending".equals(status)) return "대기중";
        if ("accepted".equals(status)) return "수락됨";
        if ("rejected".equals(status)) return "거절됨";
        return status;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestTarget, tvRequestStatus, tvRequestTime;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestTarget = itemView.findViewById(R.id.tvRequestTarget);
            tvRequestStatus = itemView.findViewById(R.id.tvRequestStatus);
            tvRequestTime = itemView.findViewById(R.id.tvRequestTime);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
