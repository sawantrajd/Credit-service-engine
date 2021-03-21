package com.rd.loancalculator.controller;

import com.rd.loancalculator.exception.DaysLimitConstraintCrossedException;
import com.rd.loancalculator.exception.InvalidInputException;
import com.rd.loancalculator.request.CustomerRequest;
import com.rd.loancalculator.response.CustomerResponse;
import com.rd.loancalculator.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public CustomerResponse checkCustomerEligibility(@RequestBody  CustomerRequest customerRequest) throws InvalidInputException, DaysLimitConstraintCrossedException {
        return customerService.checkCustomerEligibility(customerRequest);
    }
}
