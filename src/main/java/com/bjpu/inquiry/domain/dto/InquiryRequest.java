package com.bjpu.inquiry.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryRequest implements Serializable {
    private String sourceAccount;
    private String bankCode;
    private String destAccount;
    private BigDecimal amount;
}
