package com.example.todoapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskChangeListener listener;

    public interface OnTaskChangeListener {
        void onTaskChecked(Task task, boolean isChecked);
        void onTaskDeleted(Task task);
    }

    public TaskAdapter(List<Task> taskList, OnTaskChangeListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textTitle.setText(task.getTitle());

        // Null 방지 처리: 마감일
        String dueDate = task.getDueDate();
        if (dueDate == null || dueDate.trim().isEmpty()) {
            holder.textDueDate.setText("마감일 없음");
        } else {
            holder.textDueDate.setText("마감일: " + dueDate);
        }

        // Null 방지 처리: 우선순위
        String priority = task.getPriority();
        if (priority == null) priority = "하";  // 기본값

        holder.textPriority.setText(priority);

        switch (priority) {
            case "상":
                holder.textPriority.setBackgroundColor(Color.parseColor("#E53935"));
                break;
            case "중":
                holder.textPriority.setBackgroundColor(Color.parseColor("#FB8C00"));
                break;
            case "하":
            default:
                holder.textPriority.setBackgroundColor(Color.parseColor("#43A047"));
                break;
        }

        holder.checkBox.setChecked(task.isDone());

        holder.checkBox.setOnCheckedChangeListener((btn, isChecked) -> {
            task.setDone(isChecked);
            if (listener != null) listener.onTaskChecked(task, isChecked);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onTaskDeleted(task);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateList(List<Task> updatedList) {
        this.taskList = updatedList;
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textTitle, textDueDate, textPriority;
        ImageButton btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            textTitle = itemView.findViewById(R.id.textViewTitle);
            textDueDate = itemView.findViewById(R.id.textViewDueDate);
            textPriority = itemView.findViewById(R.id.textViewPriority);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}