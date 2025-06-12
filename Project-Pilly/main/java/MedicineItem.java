package com.example.pilly;

import java.io.Serializable;

public class MedicineItem implements Serializable {
    private String name;      // 약 이름
    private String amount;    // 복용량(예: "50", "500mg" 등)
    private boolean taken;

    // 기본 생성자
    public MedicineItem() {}

    // 전체 생성자 (time 없이)
    public MedicineItem(String name, String amount, boolean taken) {
        this.name = name;
        this.amount = amount;
        this.taken = taken;
    }

    // 이름
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // 복용량
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    // 복용 완료 체크
    public boolean isTaken() { return taken; }
    public void setTaken(boolean taken) { this.taken = taken; }
}
