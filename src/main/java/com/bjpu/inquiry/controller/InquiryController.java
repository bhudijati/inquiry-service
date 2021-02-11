package com.bjpu.inquiry.controller;

import com.bjpu.inquiry.domain.dto.InquiryRequest;
import com.bjpu.inquiry.domain.dto.ResultCode;
import com.bjpu.inquiry.domain.dto.audit.AuditActivity;
import com.bjpu.inquiry.domain.dto.audit.AuditRequest;
import com.bjpu.inquiry.domain.dto.trxhistory.TransactionManagementRequest;
import com.bjpu.inquiry.exceptions.GenericException;
import com.bjpu.inquiry.services.AuditManagementService;
import com.bjpu.inquiry.services.InquiryService;
import com.bjpu.inquiry.domain.dto.InquiryResponse;
import com.bjpu.inquiry.services.TransactionManagementService;
import com.bjpu.inquiry.domain.dto.common.TransferConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/v1")
@Slf4j
public class InquiryController {

    @Autowired
    private AuditManagementService auditManagementService;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private TransactionManagementService transactionManagementService;

    @PostMapping(value = "/inquiry")
    @ResponseBody
    public InquiryResponse inquiryOtherBank(@RequestBody InquiryRequest inquiryRequest) {
        InquiryResponse inquiryResponse;
        ResultCode resultCode = ResultCode.builder()
                .resultAndCode(TransferConstant.APPROVED_CODE)
                .build();
        String referenceNumber = String.valueOf(UUID.randomUUID());
        TransactionManagementRequest transactionManagementRequest = new TransactionManagementRequest();
        try {
            inquiryResponse = inquiryService.getInquiryData(inquiryRequest, transactionManagementRequest, referenceNumber);
        } catch (Exception ex) {
            log.error("Exception happened when doing inquiry message [{}]", ex.getMessage());
            throw throwHandler(ex, resultCode);
        } finally {
            CompletableFuture<Void> audit = CompletableFuture.runAsync(() -> auditManagementService.sendAudit(AuditRequest.builder()
                    .activityName(AuditActivity.INQUIRY)
                    .sourceAccount(inquiryRequest.getSourceAccount())
                    .destinationAccount(inquiryRequest.getDestAccount())
                    .resultCode(resultCode.getResultAndCode())
                    .referenceNumber(referenceNumber)
                    .build()));
            CompletableFuture<Void> history = CompletableFuture.runAsync(() ->
                    transactionManagementService.sendTransactionalData(transactionManagementRequest));
            CompletableFuture.allOf(audit, history).join();
        }
        return inquiryResponse;
    }

    private GenericException throwHandler(Exception ex, ResultCode resultCode) {
        String sourceSystem = "";
        String errorCode = "";
        String errorDesc = "";
        String referenceNumber = "";
        if (ex instanceof GenericException) {
            sourceSystem = ((GenericException) ex).getSourceSystem();
            errorCode = ((GenericException) ex).getErrorCode();
            errorDesc = ((GenericException) ex).getErrorDesc();
            referenceNumber = ((GenericException) ex).getReferenceNumber();

        }
        resultCode.setResultAndCode(sourceSystem.concat(":").concat(errorCode));
        return new GenericException(sourceSystem, errorCode, errorDesc, referenceNumber);
    }
}
