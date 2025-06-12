package com.example.pilly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    private final List<Challenge> challengeList;
    private final Context context;
    private final boolean isCompleted; // true면 완료된 챌린지용

    public ChallengeAdapter(Context context, List<Challenge> challengeList, boolean isCompleted) {
        this.context = context;
        this.challengeList = challengeList;
        this.isCompleted = isCompleted;
    }

    @NonNull
    @Override
    public ChallengeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_challenge_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeAdapter.ViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);

        holder.tvTitle.setText(challenge.getTitle());

        int current = challenge.getCurrentStreak();
        int target = challenge.getTargetDays();
        int percent = (int) ((current / (float) target) * 100);
        holder.progressBar.setProgress(percent);

        holder.tvProgress.setText(target + "일 중 " + current + "일 성공");

        // 상태 텍스트
        if (isCompleted) {
            holder.tvStatus.setText("완료됨");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.gray)); // 완료 시 회색
        } else {
            holder.tvStatus.setText("진행 중");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green)); // 진행 중이면 초록
        }
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvProgress, tvStatus;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvChallengeTitle);
            tvProgress = itemView.findViewById(R.id.tvChallengeProgressText);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvStatus = itemView.findViewById(R.id.tvChallengeStatus);
        }
    }
}
