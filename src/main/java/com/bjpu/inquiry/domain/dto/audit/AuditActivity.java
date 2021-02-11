package com.bjpu.inquiry.domain.dto.audit;

public enum AuditActivity {
    INQUIRY("Send inquiry for Transfer to Other Bank");

    String activity;

    AuditActivity(String activity){
        this.activity = activity;
    }
    public String getActivity() {
        return activity;
    }
}
