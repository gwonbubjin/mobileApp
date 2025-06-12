package com.example.pilly;

import java.util.List;

// CareTarget.java (돌봄 대상자)
public class CareTarget {
    public String id;        // Firestore 문서 ID 등
    public String name;
    public String relation;
    public int rate;         // 복용률 %
    public List<MedicineRow> medicines;

    public CareTarget(String id, String name, String relation, int rate, List<MedicineRow> medicines) {
        this.id = id;
        this.name = name;
        this.relation = relation;
        this.rate = rate;
        this.medicines = medicines;
    }
}




