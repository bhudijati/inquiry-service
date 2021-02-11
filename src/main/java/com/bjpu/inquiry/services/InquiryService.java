package com.bjpu.inquiry.services;

import com.bjpu.inquiry.domain.dto.InquiryRequest;
import com.bjpu.inquiry.domain.dto.InquiryResponse;
import com.bjpu.inquiry.domain.dto.common.TransferConstant;
import com.bjpu.inquiry.domain.dto.trxhistory.TransactionManagementRequest;
import com.bjpu.inquiry.domain.dto.trxhistory.TransactionStage;
import com.bjpu.inquiry.exceptions.GenericException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InquiryService {


    public InquiryResponse getInquiryData(InquiryRequest inquiryRequest, TransactionManagementRequest transactionManagementRequest,
                                          String referenceNumber) {
        transactionManagementRequest.setTransactionStage(TransactionStage.INQUIRY.getStage());
        transactionManagementRequest.setSourceAccount(inquiryRequest.getSourceAccount());
        transactionManagementRequest.setBankCode(inquiryRequest.getBankCode());
        transactionManagementRequest.setDestAccount(inquiryRequest.getDestAccount());
        transactionManagementRequest.setAmount(inquiryRequest.getAmount());
        transactionManagementRequest.setReferenceNumber(referenceNumber);

        //do some mock validation
        if (inquiryRequest.getAmount().compareTo(BigDecimal.valueOf(10000)) < 0) {
            throw new GenericException(TransferConstant.ErrorCode.PREFIX_INT_SOURCE_SYSTEM, TransferConstant.ErrorCode.AMOUNT_INVALID, "Invalid amount", referenceNumber);
        }

        if (!TransferConstant.MOCK_NUMBER_DEST.equalsIgnoreCase(inquiryRequest.getDestAccount()) &&
                !TransferConstant.MOCK_BANK_CODE_DEST.equalsIgnoreCase(inquiryRequest.getBankCode())) {
            throw new GenericException(TransferConstant.ErrorCode.PREFIX_INT_SOURCE_SYSTEM, TransferConstant.ErrorCode.DESTINATION_INVALID, "Invalid Destination", referenceNumber);
        }

        InquiryResponse inquiryResponse = InquiryResponse.builder()
                .adminFee(BigDecimal.valueOf(6500))
                .amount(inquiryRequest.getAmount())
                .destinationAccountName("Mocking Desti")
                .destinationAccountNumber(inquiryRequest.getDestAccount())
                .referenceNumber(referenceNumber)
                .build();

        transactionManagementRequest.setDestinationAccountName(inquiryResponse.getDestinationAccountName());
        transactionManagementRequest.setAdminFee(inquiryResponse.getAdminFee());

        //doMock Inquiry
        return inquiryResponse;
    }
}
