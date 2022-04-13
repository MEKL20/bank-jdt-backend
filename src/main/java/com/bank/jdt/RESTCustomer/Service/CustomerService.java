package com.bank.jdt.RESTCustomer.Service;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTCustomer.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
