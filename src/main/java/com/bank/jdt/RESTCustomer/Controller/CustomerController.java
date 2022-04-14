package com.bank.jdt.RESTCustomer.Controller;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTCustomer.Service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Customer")
public class CustomerController {

    private final
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(String username, String password){
        return customerService.loginCustomer(username, password);
    }

    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer) throws Exception {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @GetMapping("/get")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/get/{id}")
    public Optional<Customer> getCustomer(@PathVariable("id") long id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id) {
        customerService.deleteCustomerById(id);
        return "Customer with id :"+id+" has been deleted";
    }


}
