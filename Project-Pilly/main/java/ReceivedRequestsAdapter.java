package com.example.pilly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReceivedRequestsAdapter extends RecyclerView.Adapter<ReceivedRequestsAdapter.ViewHolder> {

    private List<ReceivedRequestItem> items;
    private OnActionClickListener actionClickListener;

    // 콜백 인터페이스 (액티비티에서 구현)
    public interface OnActionClickListener {
        void onAccept(int pos, String docId);
        void onReject(int pos, String docId);
    }

    public void setOnActionClickListener(OnActionClickListener listener) {
        this.actionClickListener = listener;
    }

    public ReceivedRequestsAdapter(List<ReceivedRequestItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_request, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReceivedRequestItem item = items.get(position);
        holder.tvRequestSender.setText("보낸 사람: " + item.fromName);
        holder.tvRequestTime.setText(item.time);

        holder.btnAccept.setOnClickListener(v -> {
            if (actionClickListener != null) {
                actionClickListener.onAccept(holder.getAdapterPosition(), item.docId);
            }
        });
        holder.btnReject.setOnClickListener(v -> {
            if (actionClickListener != null) {
                actionClickListener.onReject(holder.getAdapterPosition(), item.docId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestSender, tvRequestTime;
        Button btnAccept, btnReject;

        ViewHolder(@NonNull View v) {
            super(v);
            tvRequestSender = v.findViewById(R.id.tvRequestSender);
            tvRequestTime = v.findViewById(R.id.tvRequestTime);
            btnAccept = v.findViewById(R.id.btnAccept);
            btnReject = v.findViewById(R.id.btnReject);
        }
    }
}
