package com.example.todoapp;

public class Task {
    private int id;
    private String title;
    private boolean isDone;
    private String dueDate; // 날짜/시간 (예: "2024-05-10 16:30")
    private String priority; // 상, 중, 하

    public Task(int id, String title, boolean isDone, String dueDate, String priority) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isDone() { return isDone; }
    public String getDueDate() { return dueDate; }
    public String getPriority() { return priority; }

    public void setTitle(String title) { this.title = title; }
    public void setDone(boolean done) { isDone = done; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setPriority(String priority) { this.priority = priority; }
}
