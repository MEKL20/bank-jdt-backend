package com.bank.jdt.RESTCustomer.Controller;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTCustomer.Service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<String> updateCustomer(@PathVariable("username") String username, @RequestBody Customer customer) {
        return customerService.updateCustomer(username, customer);
    }

    @GetMapping("/get")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/get/{username}")
    public Optional<Customer> getCustomer(@PathVariable("username") String username) {
        return customerService.getCustomerByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id) {
        customerService.deleteCustomerById(id);
        return "Customer with id :"+id+" has been deleted";
    }


}
