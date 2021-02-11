package com.bjpu.inquiry.controller;

import com.bjpu.inquiry.domain.dto.InquiryRequest;
import com.bjpu.inquiry.exceptions.GenericException;
import com.bjpu.inquiry.services.AuditManagementService;
import com.bjpu.inquiry.services.InquiryService;
import com.bjpu.inquiry.services.TransactionManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditManagementService auditManagementService;

    @MockBean
    private InquiryService inquiryService;

    @MockBean
    private TransactionManagementService transactionManagementService;

    @SneakyThrows
    @Test
    public void inquiryOtherBank_expectSuccess() {
        mockMvc.perform(post("/v1/inquiry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InquiryRequest.builder()
                        .amount(BigDecimal.valueOf(10000))
                        .bankCode("001")
                        .sourceAccount("7125325346")
                        .destAccount("123456789")
                        .build())))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test(expected = Exception.class)
    public void inquiryOtherBank_expectException() {
        doThrow(GenericException.class).when(inquiryService).getInquiryData(any(), any(), any());
        mockMvc.perform(post("/v1/inquiry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InquiryRequest.builder()
                        .amount(BigDecimal.valueOf(10000))
                        .bankCode("001")
                        .sourceAccount("7125325346")
                        .destAccount("123456789")
                        .build())))
                .andExpect(status().isBadRequest());
    }
}