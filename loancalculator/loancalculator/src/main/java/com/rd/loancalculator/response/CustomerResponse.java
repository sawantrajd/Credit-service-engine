package com.rd.loancalculator.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomerResponse {

    private String ssnNumber;
    private Double eligibleLoanAmount;
    private String message;
    private HttpStatus status;
}
