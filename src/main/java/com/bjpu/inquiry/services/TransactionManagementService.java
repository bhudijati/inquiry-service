package com.bjpu.inquiry.services;

import com.bjpu.inquiry.domain.dto.trxhistory.TransactionManagementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.history.management.service}")
    private String prefixAHistoryServiceUrl;

    public void sendTransactionalData(TransactionManagementRequest transactionManagementRequest) {
        restTemplate.postForEntity(prefixAHistoryServiceUrl.concat("/history/api/v1/store"),
                transactionManagementRequest, HttpStatus.class);
    }
}
