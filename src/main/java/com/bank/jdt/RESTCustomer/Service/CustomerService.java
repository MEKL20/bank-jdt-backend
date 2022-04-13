package com.bank.jdt.RESTCustomer.Service;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTCustomer.Repository.CustomerRepository;
import com.bank.jdt.Utils.JwtUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

@Service
public class CustomerService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    @Autowired
    CustomerRepository customerRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<String> loginCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), password));
                UserDetails userDetails = userDetailsService
                        .loadUserByUsername(customer.getEmail());

                final String jwt = jwtUtil.generateToken(userDetails);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Customer", customer.getName());
                jsonObject.put("Token", jwt);

                return new ResponseEntity(jsonObject, HttpStatus.OK);

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
}
