package com.rd.loancalculator.entity;

import com.rd.loancalculator.request.CustomerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_customer")
    private Long idCustomer;

    @Column(name = "ssn_number")
    private String ssnNumber;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "applicationDate")
    private Date applicationDate;


    public Customer(CustomerRequest customerRequest) {
        this.ssnNumber = customerRequest.getSsnNumber().trim();
        this.loanAmount = customerRequest.getLoanAmount();
        this.annualIncome = customerRequest.getAnnualIncome();
        this.applicationDate = new Date();
    }

    public Customer(String ssnNumber, Double loanAmount, Double annualIncome, Date applicationDate) {
        this.ssnNumber = ssnNumber;
        this.loanAmount = loanAmount;
        this.annualIncome = annualIncome;
        this.applicationDate = applicationDate;
    }
}
