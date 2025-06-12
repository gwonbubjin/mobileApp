package com.example.pilly;

import java.io.Serializable;
import java.util.ArrayList;

public class MedicineTime implements Serializable {
    private String time;
    private String time24; // 24시간 형식 시간 저장
    private ArrayList<MedicineItem> items;
    private String docId;
    private ArrayList<String> days;

    public MedicineTime() {
        this.items = new ArrayList<>();
        this.days = new ArrayList<>();
    }

    public MedicineTime(String time, ArrayList<MedicineItem> items) {
        this.time = time;
        this.items = items;
        this.days = new ArrayList<>();
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getTime24() { return time24; }
    public void setTime24(String time24) { this.time24 = time24; }

    public ArrayList<MedicineItem> getItems() {
        if (items == null) items = new ArrayList<>();
        return items;
    }

    public void setItems(ArrayList<MedicineItem> items) {
        this.items = items;
    }

    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

    public ArrayList<String> getDays() {
        if (days == null) days = new ArrayList<>();
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }}
