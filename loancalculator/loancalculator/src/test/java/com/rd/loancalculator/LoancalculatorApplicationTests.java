package com.rd.loancalculator;

import com.rd.loancalculator.constants.Constants;
import com.rd.loancalculator.entity.Customer;
import com.rd.loancalculator.request.CustomerRequest;
import com.rd.loancalculator.response.CustomerResponse;
import com.rd.loancalculator.response.ExceptionResponse;
import com.rd.loancalculator.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoancalculatorApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CustomerService customerService;

	@Test
	@DisplayName("test Message REST API ")
	void testMessage() {
		String url = "/customer";
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setSsnNumber("RTREWQ123456");
		customerRequest.setLoanAmount(900000.0);
		customerRequest.setAnnualIncome(2500000.0);
		CustomerResponse customerResponseObj = new CustomerResponse();
		customerResponseObj.setMessage("Try after days limit constraint");
		customerResponseObj.setStatus(HttpStatus.BAD_REQUEST);
		ExceptionResponse exceptionResponseObj = new ExceptionResponse(Constants.CONSTRAINT_CROSSED,"400");
		ExceptionResponse exceptionResponse = this.restTemplate.postForObject(url,customerRequest,ExceptionResponse.class
				);
		assertEquals(exceptionResponse, exceptionResponseObj);
	}
}
