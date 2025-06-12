package com.example.pilly;

public class Challenge {
    private String title;
    private int targetDays;
    private int currentStreak;
    private boolean completed; // ✅ 필드명을 Firestore에 맞춤
    private String lastSuccessDate;

    public Challenge() {
        // Firestore 역직렬화용 기본 생성자
    }

    public Challenge(String title, int targetDays, int currentStreak, boolean completed, String lastSuccessDate) {
        this.title = title;
        this.targetDays = targetDays;
        this.currentStreak = currentStreak;
        this.completed = completed;
        this.lastSuccessDate = lastSuccessDate;
    }

    public String getTitle() {
        return title;
    }

    public int getTargetDays() {
        return targetDays;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getLastSuccessDate() {
        return lastSuccessDate;
    }
}
