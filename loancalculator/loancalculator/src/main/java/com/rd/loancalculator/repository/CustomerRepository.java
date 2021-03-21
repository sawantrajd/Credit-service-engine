package com.rd.loancalculator.repository;

import com.rd.loancalculator.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findBySsnNumber(String ssnNumber);
}
