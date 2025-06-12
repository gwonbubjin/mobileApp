package com.example.pilly;

public class ReceivedRequestItem {
    public String docId;
    public String fromName;
    public String time;
    public String status;

    public ReceivedRequestItem(String docId, String fromName, String time, String status) {
        this.docId = docId;
        this.fromName = fromName;
        this.time = time;
        this.status = status;
    }
}
