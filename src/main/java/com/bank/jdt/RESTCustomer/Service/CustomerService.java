package com.bank.jdt.RESTCustomer.Service;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTCustomer.Repository.CustomerRepository;
import com.bank.jdt.Utils.JwtUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<String> loginCustomer(String username, String password) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                UserDetails userDetails = userDetailsService
                        .loadUserByUsername(username);

                final String jwt = jwtUtil.generateToken(userDetails);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Customer", customer);
                jsonObject.put("Token", jwt);

                return new ResponseEntity<>(jwt, HttpStatus.OK);

            } catch (BadCredentialsException e) {
                return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
            } catch (JSONException e) {
                return new ResponseEntity<>("JSON nya salah", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    public Customer addCustomer(Customer customer) {
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(long id, Customer customer) {
        Customer existingCustomer = customerRepository.getById(id);
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        existingCustomer.setName(customer.getName());
        existingCustomer.setPassword(encodedPassword);
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setDatebirth(customer.getDatebirth());
        return customerRepository.save(existingCustomer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(long id){
        return customerRepository.findById(id);
    }

    public void deleteCustomerById(long id) {
        customerRepository.deleteById(id);
    }
}
