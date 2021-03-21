package com.rd.loancalculator.servive;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rd.loancalculator.entity.Customer;
import com.rd.loancalculator.exception.InvalidInputException;
import com.rd.loancalculator.repository.CustomerRepository;
import com.rd.loancalculator.request.CustomerRequest;
import com.rd.loancalculator.response.CustomerResponse;
import com.rd.loancalculator.service.CustomerService;
import com.rd.loancalculator.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerServiceImpl customerServiceimpl;

    @Mock
    CustomerRepository customerRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    private TestRestTemplate restTemplate;

    /*@Test
    public void saveCustomerRecords() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("RTREWQ123456", 900000.0,2500000.0);
        this.mockMvc.perform(post("/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isOk());
    }*/

    @Test
    @DisplayName("Subscription message service test ")
    void testSubscriptionMessage() {
        CustomerRequest customerRequest = new CustomerRequest("RTREWQ123456", 900000.0,2500000.0);
        Customer customer = new Customer(customerRequest);

        CustomerResponse customerResponse = customerServiceimpl.saveCustomerRecords(customer);
        //assertEquals("Hello "+user+", Thanks for the subscription!", message);
        assertEquals(customerResponse.getStatus(), HttpStatus.OK);
    }

    @Test
    public void createCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("RTREWQ123456", 900000.0,2500000.0);
        Customer customer = new Customer(customerRequest);

        CustomerResponse customerResponseObj = new CustomerResponse();
        customerResponseObj.setMessage("Try after days limit constraint");
        customerResponseObj.setStatus(HttpStatus.BAD_REQUEST);


        customerService.saveCustomerRecords(
                Mockito.any(Customer.class));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());


    }

   /* @Test
    public void getCustomerScoreTest() {
        // request url
        String url = "http://credit-service/credit?ssnNumber={ssnNumber}";
        String ssnNumber = "RTREWQ123456";

        // make an HTTP GET request
        //Integer score = restTemplate.getForObject(url, Integer.class, ssnNumber);
        //assertEquals(750, score);

        Mockito.when(customerServiceimpl.getCustomerScore(ssnNumber)).thenReturn(750);
        Integer score = customerServiceimpl.getCustomerScore(ssnNumber);
        assertEquals(750, score);
    }*/

    @Test
    public void updateCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("RTREWQ123456", 900000.0,2500000.0);
        Customer customer = new Customer(customerRequest);

        CustomerResponse customerResponseObj = new CustomerResponse();
        customerResponseObj.setMessage("Try after days limit constraint");
        customerResponseObj.setStatus(HttpStatus.BAD_REQUEST);


        customerService.saveCustomerRecords(
                Mockito.any(Customer.class));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());


    }

    @Test
    public void getLoanAmountTest()
    {
        CustomerRequest customerRequest = new CustomerRequest("RTREWQ123456", 900000.0,2500000.0);
        Customer customer = new Customer(customerRequest);
        Integer score = 750;

        Double loan = customerServiceimpl.getLoanAmount(score,customer);
        //assertEquals("Hello "+user+", Thanks for the subscription!", message);
        assertEquals(1250000.0,loan);
    }

    @Test
    public void getLoanAmountLessTest()
    {
        CustomerRequest customerRequest = new CustomerRequest("RTREWQ123456", 900000.0,2500000.0);
        Customer customer = new Customer(customerRequest);
        Integer score = 500;

        Double loan = customerServiceimpl.getLoanAmount(score,customer);
        //assertEquals("Hello "+user+", Thanks for the subscription!", message);
        assertEquals(0.0,loan);
    }

    /*@Test
    public void validateConstraintTest() {

        Customer customer = new Customer("RTREWQ123456", 900000.0,2500000.0, new Date(2020-01-01));


        boolean result = customerServiceimpl.validateConstraint(customer);

        assertEquals(true,result);
    }*/

    @Test
    public void validateConstraintNewTest() {

        Customer customer = new Customer("RTREWQ123456", 900000.0,2500000.0, new Date());


        boolean result = customerService.validateConstraint(customer);

        assertEquals(false,result);
    }

    @Test
    public void validateCustomerRequestEmptyTest() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest();

        // customerServiceimpl.validateCustomerRequest(customerRequest);


        CustomerResponse customerResponseObj = new CustomerResponse();
        customerResponseObj.setMessage("Try after days limit constraint");
        customerResponseObj.setStatus(HttpStatus.BAD_REQUEST);


        customerService.validateCustomerRequest(
                Mockito.any(CustomerRequest.class));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void validateCustomerRequestSSNNumberTest() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("", 900000.0,2500000.0);



        CustomerResponse customerResponseObj = new CustomerResponse();
        customerResponseObj.setMessage("Try after days limit constraint");
        customerResponseObj.setStatus(HttpStatus.BAD_REQUEST);


        customerService.validateCustomerRequest(
                Mockito.any(CustomerRequest.class));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void validateCustomerRequestLoanAmountTest() throws InvalidInputException,Exception {
        CustomerRequest customerRequest = new CustomerRequest("", null,2500000.0);



        CustomerResponse customerResponseObj = new CustomerResponse();
        customerResponseObj.setMessage("Try after days limit constraint");
        customerResponseObj.setStatus(HttpStatus.BAD_REQUEST);


        customerService.validateCustomerRequest(
                Mockito.any(CustomerRequest.class));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }


}
