package com.bjpu.inquiry.services;

import com.bjpu.inquiry.domain.dto.InquiryRequest;
import com.bjpu.inquiry.domain.dto.InquiryResponse;
import com.bjpu.inquiry.domain.dto.trxhistory.TransactionManagementRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InquiryServiceTest {

    @Autowired
    private InquiryService inquiryService;

    @Test
    public void testGetInquiryData_expectSuccessInquiry() {
        InquiryResponse inquiryResponse = inquiryService.getInquiryData(InquiryRequest.builder()
                .amount(BigDecimal.valueOf(10000))
                .bankCode("001")
                .sourceAccount("7125325346")
                .destAccount("123456789")
                .build(), new TransactionManagementRequest(), "UID1231231");
        assertNotNull(inquiryResponse.getDestinationAccountName());
    }

    @Test(expected = Exception.class)
    public void testGetInquiryData_expectException_invalidAmount() {
        InquiryResponse inquiryResponse = inquiryService.getInquiryData(InquiryRequest.builder()
                .amount(BigDecimal.valueOf(10000))
                .bankCode("0201")
                .sourceAccount("7125325346")
                .destAccount("12345678229")
                .build(), new TransactionManagementRequest(), "UID1231231");
    }

    @Test(expected = Exception.class)
    public void testGetInquiryData_expectException_invalidDest() {
        InquiryResponse inquiryResponse = inquiryService.getInquiryData(InquiryRequest.builder()
                .amount(BigDecimal.valueOf(123))
                .bankCode("001")
                .sourceAccount("7125325346")
                .destAccount("123456789")
                .build(), new TransactionManagementRequest(), "UID1231231");
    }
}