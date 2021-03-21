package com.rd.loancalculator.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    @NotNull
    @Size(min=2, max=30)
    @NotBlank(message = "Provide Valid SSNNumber")
    private String ssnNumber;
    @NotNull
    @Size(min=2, max=30)
    @NotBlank(message = "Provide Valid Loan Amount")
    private Double loanAmount;
    @NotNull
    @Size(min=2, max=30)
    @NotBlank(message = "Provide Valid Annual Income")
    private Double annualIncome;
}
