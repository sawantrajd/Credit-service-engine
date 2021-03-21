package com.rd.loancalculator.serviceimpl;

import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rd.loancalculator.constants.Constants;
import com.rd.loancalculator.entity.Customer;
import com.rd.loancalculator.exception.DaysLimitConstraintCrossedException;
import com.rd.loancalculator.exception.InvalidInputException;
import com.rd.loancalculator.repository.CustomerRepository;
import com.rd.loancalculator.request.CustomerRequest;
import com.rd.loancalculator.response.CustomerResponse;
import com.rd.loancalculator.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static Double MIN_LOAN_AMOUNT = (double)1000;
    
    private static Double MIN_ANNUAL_INCOME = (double)1000;

    @Override
    public CustomerResponse checkCustomerEligibility(CustomerRequest customerRequest) throws InvalidInputException, DaysLimitConstraintCrossedException {
        validateCustomerRequest(customerRequest);
        Customer customer = new Customer(customerRequest);
        Integer score = 0;
        Double loan = 0.0;
        CustomerResponse customerResponse = new CustomerResponse();
        System.out.println("########### before calling credit score = ");

        Customer customerObj = customerRepository.findBySsnNumber(customerRequest.getSsnNumber().trim());

        //case when user applied at least once
        if(customerObj!=null) {
            if(validateConstraint(customerObj)){
                throw new DaysLimitConstraintCrossedException(Constants.CONSTRAINT_CROSSED);

            }

			score = getCustomerScore(customerObj.getSsnNumber());
			loan = getLoanAmount(score, customer);
			customerResponse = updateCustomerRecords(customerObj, customer);
			customerResponse.setSsnNumber(customer.getSsnNumber());
			customerResponse.setEligibleLoanAmount(loan);
			return customerResponse;
        }
        //When this user records are not in DB means never applied for loan
        else {
            score = getCustomerScore(customer.getSsnNumber());
            loan =  getLoanAmount(score,customer);
            customerResponse = saveCustomerRecords(customer);
            customerResponse.setSsnNumber(customer.getSsnNumber());
            customerResponse.setEligibleLoanAmount(loan);
            return customerResponse;
        }
    }

    @Override
    public CustomerResponse saveCustomerRecords(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customer.setApplicationDate(new Date());
        customerRepository.save(customer);
        customerResponse.setMessage(Constants.CUSTOMER_SAVED);
        customerResponse.setStatus(HttpStatus.OK);
        return customerResponse;
    }

    public boolean validateConstraint(Customer customerObj) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime thirtyDaysAgo = now.plusDays(-30);
        return !customerObj.getApplicationDate().toInstant().isBefore(thirtyDaysAgo.toInstant());
    }

    @Override
    public CustomerResponse updateCustomerRecords(Customer customerObj,Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerObj.setLoanAmount(customer.getLoanAmount());
        customerObj.setApplicationDate(new Date());
        customerObj.setAnnualIncome(customer.getAnnualIncome());
        customerRepository.save(customerObj);
        customerResponse.setMessage(Constants.CUSTOMER_UPDATED);
        customerResponse.setStatus(HttpStatus.OK);
        return customerResponse;
    }

    public void validateCustomerRequest(CustomerRequest customerRequest) throws InvalidInputException {
        if(null == customerRequest)
        {
            throw new InvalidInputException("Please provide customer details");

        }
        if(StringUtils.isBlank(customerRequest.getSsnNumber()))
        {
            throw new InvalidInputException("Please provide valid SSN number");
        }
        
        if(null == customerRequest.getLoanAmount() )
        {
            throw new InvalidInputException("Please provide Loan amount");

        }
        
        if(customerRequest.getLoanAmount()<MIN_LOAN_AMOUNT)
        {
            throw new InvalidInputException("Please provide valid Loan amount > "+MIN_LOAN_AMOUNT);
        }
        
        if(null == customerRequest.getAnnualIncome())
        {
            throw new InvalidInputException("Please provide Annual income");
        }
        
        if(customerRequest.getAnnualIncome()<MIN_ANNUAL_INCOME)
        {
            throw new InvalidInputException("Annual income is not eligible");
        }
    }

    @Override
    public Integer getCustomerScore(String ssnNumber) {
        // request url
        String url = "http://credit-service/credit?ssnNumber={ssnNumber}";
        // make an HTTP GET request
        Integer score = restTemplate.getForObject(url, Integer.class, ssnNumber);
        return score;
    }

    @Override
    public Double getLoanAmount(Integer score,Customer customer) {
        if(score!=null && score>=750 && customer.getLoanAmount()!=null && customer.getLoanAmount()>0.0)
        {
            return customer.getAnnualIncome()/2;
        }
        return 0.0;
    }


}