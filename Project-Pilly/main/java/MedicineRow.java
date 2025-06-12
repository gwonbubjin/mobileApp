package com.example.pilly;

public class MedicineRow {
    public String time;
    public String name;
    public boolean taken;

    public int timeDocIndex; // 문서 인덱스
    public int medIndex;     // 해당 문서 내 약물 리스트 인덱스
    public String timeId;    // 🔥 Firestore 문서 ID (복용 기록 저장용)

    public MedicineRow(String time, String name, boolean taken, int timeDocIndex, int medIndex) {
        this.time = time;
        this.name = name;
        this.taken = taken;
        this.timeDocIndex = timeDocIndex;
        this.medIndex = medIndex;
        this.timeId = ""; // 기본값, CareActivity에서 set해줘야 함
    }
}
