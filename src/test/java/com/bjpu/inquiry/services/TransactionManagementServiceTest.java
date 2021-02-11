package com.bjpu.inquiry.services;

import com.bjpu.inquiry.domain.dto.trxhistory.TransactionManagementRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionManagementServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TransactionManagementService transactionManagementService;

    @Test
    public void testSendTransactionalData_expectRestemplateCalled() {
        transactionManagementService.sendTransactionalData(TransactionManagementRequest.builder().build());
        verify(restTemplate, times(1)).postForEntity(anyString(), any(TransactionManagementRequest.class), eq(HttpStatus.class));
    }
}