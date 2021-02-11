package com.bjpu.inquiry.domain.dto.trxhistory;

public enum TransactionStage {
    INQUIRY("INQUIRY"),
    EXECUTED("EXECUTED");

    String stage;

    TransactionStage(String stage){
        this.stage = stage;
    }
    public String getStage() {
        return stage;
    }
}
