package com.example.pilly;

public class RequestItem {
    public String docId;      // Firestore 문서 ID (삭제 시 필요)
    public String targetName; // 상대 이름
    public String status;
    public String time;

    public RequestItem(String docId, String targetName, String status, String time) {
        this.docId = docId;
        this.targetName = targetName;
        this.status = status;
        this.time = time;
    }
}
