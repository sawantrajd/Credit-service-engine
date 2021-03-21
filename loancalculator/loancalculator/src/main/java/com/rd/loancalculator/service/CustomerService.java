package com.rd.loancalculator.service;

import com.rd.loancalculator.exception.DaysLimitConstraintCrossedException;
import com.rd.loancalculator.exception.InvalidInputException;
import com.rd.loancalculator.request.CustomerRequest;
import com.rd.loancalculator.response.CustomerResponse;
import com.rd.loancalculator.entity.Customer;

public interface CustomerService {


    public CustomerResponse checkCustomerEligibility(CustomerRequest customerRequest) throws InvalidInputException, DaysLimitConstraintCrossedException;
    public CustomerResponse saveCustomerRecords(Customer customer);
    public CustomerResponse updateCustomerRecords(Customer customerObj, Customer customer);
    public Integer getCustomerScore(String ssnNumber);
    public Double getLoanAmount(Integer score,Customer customer);
    public void validateCustomerRequest(CustomerRequest customerRequest) throws InvalidInputException;
    public boolean validateConstraint(Customer customerObj);
}
