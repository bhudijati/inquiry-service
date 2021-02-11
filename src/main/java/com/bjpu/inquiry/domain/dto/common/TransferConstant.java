package com.bjpu.inquiry.domain.dto.common;

public class TransferConstant {
    private TransferConstant() {
        //hide for implicit sonar
    }

    public static final String APPROVED_CODE = "00";
    public static final String MOCK_NUMBER_DEST = "123456789";
    public static final String MOCK_BANK_CODE_DEST = "001";

    public static class ErrorCode{
        private ErrorCode(){
            //hide for implicit sonar
        }
        public static final String PREFIX_INT_SOURCE_SYSTEM = "INT";
        public static final String AMOUNT_INVALID = "0001";
        public static final String DESTINATION_INVALID = "0002";
    }
}
